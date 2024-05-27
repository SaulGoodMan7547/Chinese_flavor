package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.SocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.file.LinkOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private AddressBookMapper addressBookMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private WeChatPayUtil weChatPayUtil;
    private Orders order;

    @Resource
    private SocketServer socketServer;

    /**
     * 提交订单
     * @param orderSubmitDTO
     * @return
     */
    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO orderSubmitDTO) {
        //判断是否提交地址信息
        Long addressBookId = orderSubmitDTO.getAddressBookId();
        AddressBook address = addressBookMapper.getById(addressBookId);

        if(address == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //判断购物车是否为空
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

        if(shoppingCartList == null || shoppingCartList.isEmpty()){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //插入order数据
        Orders order = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO,order);
        User user = userMapper.getById(userId);

        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setStatus(1);
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setPayStatus(0);
        order.setUserName(user.getName());
        order.setPhone(address.getPhone());
        order.setAddress(address.getProvinceName() + address.getCityName() + address.getDistrictName() + address.getDetail());
        order.setConsignee(address.getConsignee());

        this.order =order;

        orderMapper.insert(order);

        //插入orderDetail数据
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);

            orderDetail.setOrderId(order.getId());

            orderDetails.add(orderDetail);
        }

        orderDetailMapper.insertBatch(orderDetails);

        //清楚购物车数据
        shoppingCartMapper.deleteByUserId(userId);

        //封装orderVO数据返回
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime()).build();

        return orderSubmitVO;
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);
/*
        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //商户订单号
                new BigDecimal(0.01), //支付金额，单位 元
                "苍穹外卖订单", //商品描述
                user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;*/

        //以下四行代码可替换为其下面注释
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","ORDERPAID");

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        //OrderPaymentVO vo = new OrderPaymentVO("123", "213", "123", "123","123");

        Integer OrderPaidStatus = Orders.PAID;//支付状态，已支付
        Integer OrderStatus = Orders.TO_BE_CONFIRMED;  //订单状态，待接单
        LocalDateTime check_out_time = LocalDateTime.now();//更新支付时间
        orderMapper.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, this.order.getId());

        //使用webSocket连接，支付成功后，向管理端页面发送信息
        //信息格式：type:1 接单 2 催单  orderId:订单id,context:消息
        Map jsonObject1 = new HashMap<>();
        jsonObject1.put("type",1);
        jsonObject1.put("orderId",this.order.getId());
        jsonObject1.put("content","订单号" + this.order.getNumber());

        String s = JSON.toJSONString(jsonObject1);

        socketServer.sendToAllClient(s);

        return vo;

    }

    /**
     * 订单详情
     */
    @Override
    public OrderVO detail(Long orderId) {

        List<OrderDetail> list = orderDetailMapper.getByOrderId(orderId);

        OrderVO orderVO = new OrderVO();
        Orders order1 = orderMapper.getById(orderId);
        BeanUtils.copyProperties(order1,orderVO);

        orderVO.setOrderDetailList(list);

        return orderVO;
    }

    /**
     * 取消订单
     */
    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) {

        LocalDateTime now = LocalDateTime.now();

        orderMapper.cancel(ordersCancelDTO,now);
    }

    /**
     * 历史订单查询
     */
    @Override
    public PageResult history(Integer page, Integer pageSize, Integer status) {
        Long userId = BaseContext.getCurrentId();

        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setUserId(userId);
        ordersPageQueryDTO.setStatus(status);



        PageHelper.startPage(page, pageSize);

        Page<Orders> ordersPage = orderMapper.pageQuery(ordersPageQueryDTO);
        List<OrderVO> list = new ArrayList<>();

        if(ordersPage != null && !ordersPage.isEmpty()){
            for (Orders orders : ordersPage) {
                String orderDishes= "";
                Long id = orders.getId();

                List<OrderDetail> details = orderDetailMapper.getByOrderId(id);
                for (OrderDetail detail : details) {
                    orderDishes += detail.getName() + " ";
                }

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders,orderVO);

                orderVO.setOrderDetailList(details);
                orderVO.setOrderDishes(orderDishes);

                list.add(orderVO);
            }
        }

        return new PageResult(ordersPage.getTotal(),list);
    }

    /**
     * 再来一单
     */
    @Override
    @Transactional
    public void repetition(Long id) {
        Orders order = orderMapper.getById(id);
        orderMapper.insert(order);

        List<OrderDetail> list = orderDetailMapper.getByOrderId(id);
        Long orderId = order.getId();
        Long userId = BaseContext.getCurrentId();

        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        if(list != null && !list.isEmpty()){

            for (OrderDetail detail : list) {
                detail.setOrderId(orderId);

                ShoppingCart shoppingCart = new ShoppingCart();
                BeanUtils.copyProperties(detail,shoppingCart);
                shoppingCart.setUserId(userId);
                shoppingCart.setCreateTime(LocalDateTime.now());

                shoppingCarts.add(shoppingCart);
            }
        }

        shoppingCartMapper.insertBatch(shoppingCarts);
    }

    /**
     * 订单分页查询
     */
    @Override
    public PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {

        int page = ordersPageQueryDTO.getPage();
        int pageSize = ordersPageQueryDTO.getPageSize();
        PageHelper.startPage(page,pageSize);

        Page<Orders> orders = orderMapper.pageQuery(ordersPageQueryDTO);
        ArrayList<OrderVO> list = new ArrayList<>();

        for (Orders order1 : orders) {
            OrderVO ordersVO = new OrderVO();
            BeanUtils.copyProperties(order1,ordersVO);

            List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(order1.getId());
            StringBuffer orderDishes = new StringBuffer();

            for (OrderDetail orderDetail : orderDetails) {
                orderDishes.append(orderDetail.getName() + " ");
            }

            ordersVO.setOrderDishes(String.valueOf(orderDishes));

            list.add(ordersVO);
        }

        PageResult pageResult = new PageResult(orders.getTotal(), list);
        return pageResult;
    }

    /**
     * 订单状态统计
     */
    @Override
    public OrderStatisticsVO statistics() {
        List<Orders> orders = orderMapper.getAll();

        Integer toBeConfirmed = 0;//待接单数量
        Integer confirmed = 0;//待派送数量
        Integer deliveryInProgress = 0;//派送中数量

        for (Orders order1 : orders) {
            Integer status = order1.getStatus();

            if (status == 2) {
                toBeConfirmed++;
            } else if (status == 3) {
                confirmed++;
            } else if (status == 4) {
                deliveryInProgress++;
            }
        }

        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);

        return orderStatisticsVO;
    }

    /**
     * 接单
     */
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {

        orderMapper.updateStatusById(ordersConfirmDTO);

    }

    /**
     * 派送订单
     */
    @Override
    public void delivery(Long id) {

        OrdersConfirmDTO ordersConfirmDTO = new OrdersConfirmDTO();
        ordersConfirmDTO.setStatus(4);
        ordersConfirmDTO.setId(id);

        orderMapper.updateStatusById(ordersConfirmDTO);
    }

    /**
     * 完成订单
     */
    @Override
    public void complete(Long id) {

        OrdersConfirmDTO ordersConfirmDTO = new OrdersConfirmDTO();
        ordersConfirmDTO.setStatus(5);
        ordersConfirmDTO.setId(id);

        LocalDateTime now = LocalDateTime.now();

        //orderMapper.updateStatusById(ordersConfirmDTO);

        orderMapper.complete(id,now);
    }

    /**
     * 拒绝订单
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        LocalDateTime now = LocalDateTime.now();
        ordersRejectionDTO.setRejectionReason("拒单：" + ordersRejectionDTO.getRejectionReason());

        orderMapper.rejection(ordersRejectionDTO,now);
    }

    /**
     * 催单
     */
    @Override
    public void reminder(Long id) {
        Orders order = orderMapper.getById(id);

        Map map = new HashMap<>();
        map.put("type",2);
        map.put("content","订单号" + order.getNumber());
        map.put("orderId",id);

        String s = JSON.toJSONString(map);

        socketServer.sendToAllClient(s);
    }

    /**
     * 支付成功，修改订单状态，当前项目没有执行该方法
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }
}

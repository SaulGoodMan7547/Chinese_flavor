package com.sky.service.impl;

import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private SetMealMapper setMealMapper;

    @Resource
    private DishMapper dishMapper;

    /**
     * 查询今日运营数据
     */
    @Override
    public BusinessDataVO businessDate() {

        LocalDate today = LocalDate.now();

        //获取当日营业额
        Double todayTurnover = orderMapper.getTodayTurnover(today, today.plusDays(1));
        //获取当日有效订单数
        Integer todayOrders = orderMapper.getTodayOrders(today, today.plusDays(1));
        //获取当日所有订单数
        Integer todayAllOrders = orderMapper.getTodayAllOrders(today, today.plusDays(1));
        //获取当日新增用户数
        Integer todayNewUser = userMapper.getTodayNewUser(today, today.plusDays(1));

        Double orderCompletionRate = 0.0;
        Double unitPrice = 0.0;
        if(todayAllOrders != 0){
            orderCompletionRate = (double) todayOrders / todayAllOrders;
        }
        if(todayOrders != 0){
            unitPrice = (double) todayTurnover / todayOrders;
        }

        BusinessDataVO businessDataVO = BusinessDataVO.builder()
                .newUsers(todayNewUser)
                .turnover(todayTurnover)
                .validOrderCount(todayOrders)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice).build();


        return businessDataVO;
    }

    /**
     * 查询今日订单
     */
    @Override
    public OrderOverViewVO orderView() {

        LocalDate today = LocalDate.now();

        //获取待接单数量
        Integer waitingOrders = orderMapper.getOrderOverView(today, today.plusDays(1), 2);
        Integer deliveredOrders = orderMapper.getOrderOverView(today, today.plusDays(1), 3);
        Integer completedOrders = orderMapper.getOrderOverView(today, today.plusDays(1), 5);
        Integer cancelledOrders = orderMapper.getOrderOverView(today, today.plusDays(1), 6);
        Integer allOrders = orderMapper.getOrderOverView(today, today.plusDays(1),null);

        OrderOverViewVO build = OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders).build();

        return build;
    }

    /**
     * 套餐总览
     */
    @Override
    public SetmealOverViewVO overViewSetmeal() {

        LocalDate today = LocalDate.now();

        Integer discontinued = setMealMapper.overViewSetmeal(today, today.plusDays(1), 0);
        Integer sold = setMealMapper.overViewSetmeal(today, today.plusDays(1), 1);

        SetmealOverViewVO build = SetmealOverViewVO.builder().discontinued(discontinued).sold(sold).build();

        return build;
    }

    /**
     * 菜品总览
     */
    @Override
    public DishOverViewVO overViewDishes() {

        LocalDate today = LocalDate.now();

        Integer discontinued = dishMapper.overViewDishes(today, today.plusDays(1), 0);
        Integer sold = dishMapper.overViewDishes(today, today.plusDays(1), 1);

        DishOverViewVO build = DishOverViewVO.builder().discontinued(discontinued).sold(sold).build();

        return build;
    }
}

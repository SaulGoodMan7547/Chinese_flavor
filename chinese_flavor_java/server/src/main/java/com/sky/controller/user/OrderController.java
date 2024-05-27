package com.sky.controller.user;

import com.alibaba.fastjson.JSON;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 提交订单
     * @param orderSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO orderSubmitDTO){

        if(orderSubmitDTO.getTablewareNumber() <= 1){
            orderSubmitDTO.setTablewareNumber(1);
        }

        OrderSubmitVO submit = orderService.submit(orderSubmitDTO);

        return Result.success(submit);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);


        return Result.success(orderPaymentVO);
    }

    /**
     * 订单详情
     */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> detail(@PathVariable("id") Long id){

        OrderVO detail = orderService.detail(id);

        return Result.success(detail);
    }

    /**
     * 用户取消订单
     */
    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable("id") Long id){

        OrdersCancelDTO ordersCancelDTO = new OrdersCancelDTO();
        ordersCancelDTO.setCancelReason("用户取消订单");
        ordersCancelDTO.setId(id);

        orderService.cancel(ordersCancelDTO);

        return Result.success();
    }

    /**
     * 历史订单查询
     */
    @GetMapping("/historyOrders")
    public Result<PageResult> history(Integer page, Integer pageSize, Integer status){

        PageResult history = orderService.history(page, pageSize, status);

        return Result.success(history);
    }

    /**
     * 再来一单
     */
    @PostMapping("/repetition/{id}")
    public Result repetition(@PathVariable("id") Long id){

        orderService.repetition(id);

        return Result.success();
    }

    /**
     * 催单
     */
    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable("id") Long id){

        orderService.reminder(id);

        return Result.success();
    }
}

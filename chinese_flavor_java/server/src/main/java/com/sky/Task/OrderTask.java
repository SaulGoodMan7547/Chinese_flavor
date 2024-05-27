package com.sky.Task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 处理超时为支付（15分钟为支付为超时）
     * 每一分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ? ")
    public void processTimeOutOrders(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minutes = now.plusMinutes(-15);

        List<Orders> list = orderMapper.getByStatusAndTime(1, minutes);

        if(list != null && !list.isEmpty()){
            for (Orders orders : list) {
                orders.setStatus(6);
                orders.setCancelReason("订单超时:自动取消");
                orders.setCancelTime(LocalDateTime.now());

                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理一直处于派送中的订单（每天零晨一点统一设为已完成）
     */
    @Scheduled(cron = "0 5 21 * * ? ")
    public void processDeliveringOrders(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minutes = now.plusMinutes(-60);

        List<Orders> list= orderMapper.getByStatusAndTime(4, minutes);

        if(list != null && !list.isEmpty()){
            for (Orders orders : list) {
                orders.setStatus(5);

                orderMapper.update(orders);
            }
        }
    }
}

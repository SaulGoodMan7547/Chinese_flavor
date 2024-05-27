package com.sky.controller.admin;

import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/admin/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 订单分页查询
     */
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(Integer page,Integer pageSize, Integer status,
                                              String phone,String number,String beginTime,String endTime){

        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(beginTime != null && endTime != null){
            //格式化只能处理年月日，年月日占字符串前10为
            beginTime = beginTime.substring(0,10);
            endTime = endTime.substring(0,10);

            LocalDate bTime = LocalDate.parse(beginTime, formatter);
            LocalDate eTime = LocalDate.parse(endTime, formatter);

            ordersPageQueryDTO.setBeginTime(bTime.atStartOfDay());
            ordersPageQueryDTO.setEndTime(eTime.atStartOfDay());
        }



        ordersPageQueryDTO.setPage(page);
        ordersPageQueryDTO.setPageSize(pageSize);
        ordersPageQueryDTO.setStatus(status);
        ordersPageQueryDTO.setPhone(phone);
        ordersPageQueryDTO.setNumber(number);

        //ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());

        log.info("DTO: {}",ordersPageQueryDTO);

        PageResult pageResult = orderService.pageQuery(ordersPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 订单状态统计
     */
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> statistics(){

        OrderStatisticsVO statistics = orderService.statistics();

        return Result.success(statistics);
    }

    /**
     * 接单
     */
    @PutMapping("/confirm")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){

        log.info("id: {}",ordersConfirmDTO);

        ordersConfirmDTO.setStatus(3);

        orderService.confirm(ordersConfirmDTO);

        return Result.success();
    }

    /**
     * 派送订单
     */
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable("id") Long id){

        orderService.delivery(id);

        return Result.success();
    }

    /**
     * 完成订单
     */
    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable("id") Long id){

        orderService.complete(id);

        return Result.success();
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO){

        orderService.cancel(ordersCancelDTO);

        return Result.success();
    }

    /**
     * 拒绝订单
     */
    @PutMapping("/rejection")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO){

        orderService.rejection(ordersRejectionDTO);

        return Result.success();
    }

    /**
     * 查看订单详情
     */
    @GetMapping("/details/{id}")
    public Result<OrderVO> detail(@PathVariable("id") Long id){

        OrderVO detail = orderService.detail(id);

        return Result.success(detail);
    }
}

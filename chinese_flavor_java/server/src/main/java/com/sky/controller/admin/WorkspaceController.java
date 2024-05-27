package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/admin/workspace")
public class WorkspaceController {

    @Resource
    private WorkspaceService workspaceService;

    /**
     * 查询今日运营数据
     */
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessDate(){

        BusinessDataVO businessDataVO = workspaceService.businessDate();

        return Result.success(businessDataVO);
    }

    /**
     * 查询今日订单
     */
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> orderView(){

        OrderOverViewVO orderOverViewVO = workspaceService.orderView();

        return Result.success(orderOverViewVO);
    }

    /**
     * 套餐总览
     */
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> overViewSetmeal(){

        SetmealOverViewVO setmealOverViewVO = workspaceService.overViewSetmeal();

        return Result.success(setmealOverViewVO);
    }

    /**
     * 菜品总览
     */
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overViewDishes(){

        DishOverViewVO dishOverViewVO = workspaceService.overViewDishes();

        return Result.success(dishOverViewVO);
    }
}

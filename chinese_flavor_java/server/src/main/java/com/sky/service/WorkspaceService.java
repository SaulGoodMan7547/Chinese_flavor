package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

public interface WorkspaceService {

    /**
     * 查询今日运营数据
     */
    BusinessDataVO businessDate();

    /**
     * 查询今日订单
     */
    OrderOverViewVO orderView();

    /**
     * 套餐总览
     */
    SetmealOverViewVO overViewSetmeal();

    /**
     * 菜品总览
     */
    DishOverViewVO overViewDishes();
}

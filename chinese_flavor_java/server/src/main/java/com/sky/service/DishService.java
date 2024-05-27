package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增菜品
     */
    void save(DishDTO dishDTO);

    /**
     * 菜品分页查询
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品删除
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 菜品修改
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 菜品停售起售
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据分类id查询菜品
     */
    List<Dish> list(Long categoryId);

    /**
     * 根据分类id查询菜品（名称搜索）
     */
    List<Dish> getByName(String name);

    /**
     * 通过分类id查询菜品
     * @param setmealId
     * @return
     */
    List<DishItemVO> getBySetmealId(Long setmealId);

    /**
     * 通过分类id查询菜品
     */
    List<DishVO> listVO(Long categoryId);
}

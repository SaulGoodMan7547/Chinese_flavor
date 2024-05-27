package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 菜品分类修改
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 菜品分类删除
     */
    void delete(Integer id);

    /**
     * 菜品分类启用禁用
     */
    void startOrStop(Integer id,Integer status);

    /**
     * 菜品分类新增
     */
    void insert(CategoryDTO categoryDTO);

    /**
     * 根据类型查询分类
     */
    List<Category> list(Integer type);

    /**
     * 查询所有分类
     */
    List<Category> selectByType(Integer type);
}

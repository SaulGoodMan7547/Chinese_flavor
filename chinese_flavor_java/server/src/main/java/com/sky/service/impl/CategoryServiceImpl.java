package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        return page;
    }

    /**
     * 菜品分类修改
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO,category);
/*
        改为公共字段自动填充
        category.setUpdateTime(LocalDateTime.now());

        category.setUpdateUser(BaseContext.getCurrentId());
*/

        categoryMapper.update(category);
    }

    /**
     * 菜品分类删除
     */
    @Override
    public void delete(Integer id) {

        categoryMapper.delete(id);
    }

    /**
     * 菜品分类启用禁用
     */
    @Override
    public void startOrStop(Integer id,Integer status) {
        categoryMapper.startOrStop(id,status);
    }

    /**
     * 菜品分类新增
     */
    @Override
    public void insert(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO,category);

        /*
        改为公共字段自动填充
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
         */

        category.setStatus(StatusConstant.DISABLE);

        categoryMapper.insert(category);
    }

    /**
     * 根据类型查询分类
     */
    @Override
    public List<Category> list(Integer type) {
        List<Category> list = categoryMapper.list(type);

        return list;
    }

    //TODO 删除分类时添加判断逻辑：若该分类下有菜品，则不允许删除

    /**
     * 查询所有分类
     */
    @Override
    public List<Category> selectByType(Integer type) {

        List<Category> categories = categoryMapper.selectByType(type);

        return categories;
    }
}

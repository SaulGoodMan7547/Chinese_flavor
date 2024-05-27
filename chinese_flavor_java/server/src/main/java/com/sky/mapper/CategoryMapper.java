package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 菜品分类修改
     */
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

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
     @AutoFill(OperationType.INSERT)
    void insert(Category category);

    /**
     * 根据类型查询分类
     */
    List<Category> list(Integer type);

    /**
     * 根据类型分类（type = null则查询所有）
     * @return
     */
    List<Category> selectByType(Integer type);
}

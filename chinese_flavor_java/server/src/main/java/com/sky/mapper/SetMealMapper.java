package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SetMealMapper {

    /**
     * 通过category_id（分类id）查询数量
     */
    Integer count(Integer categoryId);

    @Select("SELECT * FROM setmeal WHERE id = #{id}")
    Setmeal getById(Long id);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 添加套餐
     * @param setmeal
     */

    @AutoFill(OperationType.INSERT)
    void add(Setmeal setmeal);

    /**
     * 套餐起售停售
     */
    @Update("UPDATE setmeal SET status = #{status} WHERE id = #{id}")
    void startOrStop(Integer status, Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 通过id查询在售的个数
     */
    Integer countSelling(List<Long> ids);

    /**
     * 修改菜品
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    @Select("SELECT * FROM setmeal WHERE category_id = #{categoryId} AND status = #{status}")
    List<Setmeal> getByCategoryId(Setmeal setMeal);

    Integer overViewSetmeal(LocalDate today, LocalDate plusDays, int status);
}

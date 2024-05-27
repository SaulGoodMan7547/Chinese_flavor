package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    /**
     * 通过许多菜品的id查询出其绑定的套餐的id
     */
    List<Long> getSetMealByDishIds(List<Long> dishIds);

    @Select("SELECT * FROM setmeal_dish WHERE setmeal_id = #{setMealId}")
    List<SetmealDish> getBySetMealId(Long setMealId);

    /**
     * 批量插入setmealDish
     * @param setmealDishes
     */
    void addBatch(List<SetmealDish> setmealDishes);

    void deleteBySetmealId(Long setmealId);

    void deleteBySetmealIds(List<Long> ids);
}

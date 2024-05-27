package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    //void select(Integer id);

    /**
     * 通过list保存多个flavor
     * @param flavors
     */
    void saveBatch(List<DishFlavor> flavors);

    /**
     * 通过dishId删除flavor
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 通过dishId集合删除批量flavor
     */
    void deleteBatchByDishId(List<Long> dishIds);

    /**
     * 通过dishId查询DishFlavor
     * @param dishId
     * @return
     */
    @Select("SELECT * FROM dish_flavor WHERE dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}

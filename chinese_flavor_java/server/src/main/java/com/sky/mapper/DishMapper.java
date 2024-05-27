package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * 通过category_id（分类）查询数量
     */
    Integer count(Integer categoryId);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void save(Dish dish);

    /**
     * 菜品分页查询
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 通过id查询菜品
     */
    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish getById(Long id);

    /**
     * 通过多个id查询其中在售数量
     * @param ids
     */
    Integer getStatusCountById(List<Long> ids);

    /**
     * 通过id集合删除数据
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 通过id删除数据
     */
    @Delete("DELETE FROM dish WHERE id = #{id}")
    void deleteById(Integer id);

    /**
     * 菜品修改
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 菜品停售起售
     */
    @Update("UPDATE dish SET status = #{status} WHERE id = #{id}")
    void startOrStop(Integer status, Long id);

    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 通过名字查询
     * @param name
     * @return
     */
    List<Dish> getByName(String name);

    /**
     * 通过分类id查询菜品
     * @param dish
     * @return
     */
    List<Dish> getByCategoryId(Dish dish);

    Integer overViewDishes(LocalDate today, LocalDate plusDays, int status);
}

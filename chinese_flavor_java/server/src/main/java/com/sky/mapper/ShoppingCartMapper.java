package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 通过userId,dishId,setmealId，dishFlavor查询购物车中的内容
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车中的菜品数量
     * @param shoppingCart1
     */
    @Update("UPDATE shopping_cart SET number = #{number} WHERE id = #{id}")
    void updateNumber(ShoppingCart shoppingCart1);

    /**
     * 向shopping_cart插入数据
     */
    @Insert("INSERT INTO shopping_cart(name,image,user_id,dish_id,setmeal_id,dish_flavor,number,amount,create_time) " +
            "VALUES (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("DELETE FROM shopping_cart  WHERE user_id = #{userId} ")
    void deleteByUserId(Long userId);

    /**
     * 通过id删除购物车中的物品
     * @param id
     */
    @Delete("DELETE FROM shopping_cart WHERE id = #{id}")
    void deleteByid(Long id);

    void insertBatch(List<ShoppingCart> shoppingCarts);
}

package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private DishMapper dishMapper;

    @Resource
    private SetMealMapper setMealMapper;
    /**
     * 添加到购物车
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //查询购物车中是否含有
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //若含有则直接数量加一
        if(list != null && list.size() > 0){
            ShoppingCart shoppingCart1 = list.get(0);
            shoppingCart1.setNumber(shoppingCart1.getNumber() + 1);

            shoppingCartMapper.updateNumber(shoppingCart1);
            return;
        }

        //若没有则插入
        Long dishId = shoppingCartDTO.getDishId();
        if(dishId != null){
            Dish dish = dishMapper.getById(dishId);

            shoppingCart.setAmount(dish.getPrice());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setName(dish.getName());
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);

            shoppingCartMapper.insert(shoppingCart);
            return;
        }

        Long setmealId = shoppingCartDTO.getSetmealId();
        Setmeal setmeal = setMealMapper.getById(setmealId);

        shoppingCart.setAmount(setmeal.getPrice());
        shoppingCart.setImage(setmeal.getImage());
        shoppingCart.setName(setmeal.getName());
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setNumber(1);

        shoppingCartMapper.insert(shoppingCart);
    }

    /**
     * 显示购物车
     */
    @Override
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId()).build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        return list;
    }

    /**
     * 清空购物车
     */
    @Override
    public void deleteByUserId() {
        Long userId = BaseContext.getCurrentId();

        shoppingCartMapper.deleteByUserId(userId);
    }

    /**
     * 删除购物车中的一个商品
     */
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> shoppingCarts = shoppingCartMapper.list(shoppingCart);

        ShoppingCart food = shoppingCarts.get(0);

        if(food.getNumber() >= 2){

            food.setNumber(food.getNumber() - 1);
            shoppingCartMapper.updateNumber(food);
        }else {
            shoppingCartMapper.deleteByid(food.getId());
        }

    }
}

package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Resource
    private DishMapper dishMapper;

    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Resource
    private SetMealDishMapper setMealDishMapper;


    /**
     * 新增菜品
     */
    @Override
    @Transactional
    public void save(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.save(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();

        Long id = dish.getId();

        if (flavors != null && flavors.size() != 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(id);
            }

            dishFlavorMapper.saveBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> dishVOS = dishMapper.pageQuery(dishPageQueryDTO);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(dishVOS.getTotal());
        pageResult.setRecords(dishVOS.getResult());

        return pageResult;
    }

    /**
     * 菜品删除
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {

        //菜品在售状态不能删
        Integer count = dishMapper.getStatusCountById(ids);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }

        //菜品被套餐绑定不能删
        List<Long> setMealIds = setMealDishMapper.getSetMealByDishIds(ids);
        if(setMealIds != null && setMealIds.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除菜品时，同时删除其对应的flavor(口味)
        dishMapper.deleteBatch(ids);
        dishFlavorMapper.deleteBatchByDishId(ids);
    }

    /**
     * 根据id查询菜品
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {

        //根据id查出菜品
        Dish dish = dishMapper.getById(id);

        //根据id(dishId) 查出其关联的flavor数据
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);

        //将菜品信息与flavor信息封装为一个DishVO返回
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    /**
     * 菜品修改
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {

        //修改Dish信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);

        //删除关联flavor
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //插入新的flavor
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dish.getId());
            }

            dishFlavorMapper.saveBatch(flavors);
        }

    }

    /**
     * 菜品停售起售
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        dishMapper.startOrStop(status,id);
    }

    /**
     * 根据分类id查询菜品
     */
    @Override
    public List<Dish> list(Long categoryId) {

        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        return dishMapper.list(dish);
    }

    /**
     * 根据分类id查询菜品（名称搜索）
     */
    @Override
    public List<Dish> getByName(String name) {

        List<Dish> dishes = dishMapper.getByName(name);

        return dishes;
    }

    /**
     * 通过分类id查询菜品
     */
    @Override
    public List<DishItemVO> getBySetmealId(Long setmealId) {

        List<SetmealDish> setmealDishes = setMealDishMapper.getBySetMealId(setmealId);

        List<DishItemVO> dishItemVOS = new ArrayList<>();

        for (SetmealDish dish : setmealDishes) {
            Long dishId = dish.getDishId();
            Dish dish1 = dishMapper.getById(dishId);

            DishItemVO dishItemVO = new DishItemVO();
            BeanUtils.copyProperties(dish,dishItemVO);

            dishItemVO.setImage(dish1.getImage());
            dishItemVO.setDescription(dish1.getDescription());

            dishItemVOS.add(dishItemVO);
        }

        return dishItemVOS;
    }

    /**
     * 通过分类id查询菜品
     */
    @Override
    public List<DishVO> listVO(Long categoryId) {

        Dish build = Dish.builder()
                .categoryId(categoryId)
                .status(1).build();

        List<Dish> dishes = dishMapper.getByCategoryId(build);

        List<DishVO> dishVOS = new ArrayList<>();

        for (Dish dish : dishes) {

            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish,dishVO);

            dishVO.setFlavors(dishFlavorMapper.getByDishId(dish.getId()));

            dishVOS.add(dishVO);
        }

        return dishVOS;
    }
}

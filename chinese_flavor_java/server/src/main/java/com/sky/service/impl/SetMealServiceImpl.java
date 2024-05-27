package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Resource
    private SetMealMapper setMealMapper;

    @Resource
    private SetMealDishMapper setMealDishMapper;

    /**
     * 根据id查询套餐
     */
    @Override
    @Transactional
    public SetmealVO getById(Long id) {

        SetmealVO setmealVO = new SetmealVO();

        Setmeal setmeal = setMealMapper.getById(id);
        if(setmeal == null){
            throw new BaseException("该套餐不存在");
        }

        BeanUtils.copyProperties(setmeal,setmealVO);

        List<SetmealDish> dishes = setMealDishMapper.getBySetMealId(id);
        setmealVO.setSetmealDishes(dishes);

        return setmealVO;
    }

    /**
     * 分页查询
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> setmealVOS = setMealMapper.pageQuery(setmealPageQueryDTO);

        return new PageResult(setmealVOS.getTotal(),setmealVOS.getResult());
    }

    /**
     * 套餐新增
     * @return
     */
    @Override
    @Transactional
    public void addWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setMealMapper.add(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        Long id = setmeal.getId();

        if(setmealDishes != null && setmealDishes.size() > 0){
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(id);
            }

            setMealDishMapper.addBatch(setmealDishes);
        }

        //TODO 未起售的菜品不能添加到套餐
    }

    /**
     * 套餐起售停售
     */
    @Override
    public void startOrStop(Integer status, Long id) {

        setMealMapper.startOrStop(status,id);
    }

    /**
     * 批量删除套餐
     */
    @Override
    public void deleteBatch(List<Long> ids) {

        Integer count = setMealMapper.countSelling(ids);

        if(count > 0)
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);

        setMealMapper.deleteBatch(ids);
        setMealDishMapper.deleteBySetmealIds(ids);
    }

    /**
     * 修改菜品
     */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();

        //将setmeal基本信息填入到setmeal表中
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMealMapper.update(setmeal);

        //删除该套餐修改前对应的菜品
        Long setmealId = setmealDTO.getId();
        setMealDishMapper.deleteBySetmealId(setmealId);

        //将改套餐修改后的菜品添加到setmesl_dish表中
        for (SetmealDish dish : dishes) {
            dish.setSetmealId(setmealId);
        }
        setMealDishMapper.addBatch(dishes);
    }

    /**
     * 通过分类id查询菜品
     */
    @Override
    public List<Setmeal> getByCategoryId(Long categoryId) {

        Setmeal build = Setmeal.builder()
                .categoryId(categoryId)
                .status(1).build();

        List<Setmeal> setMeals = setMealMapper.getByCategoryId(build);

        return setMeals;
    }
}

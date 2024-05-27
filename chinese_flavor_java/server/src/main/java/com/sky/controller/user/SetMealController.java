package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("userSetMealController")
@Slf4j
@Api(tags = "套餐管理")
@RequestMapping("/user/setmeal")
public class SetMealController {

    @Resource
    private SetMealService setMealService;

    @Resource
    private DishService dishService;

    /**
     * 根据分类id查询套餐
     */
    @GetMapping("list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId){

        List<Setmeal> setMeals = setMealService.getByCategoryId(categoryId);

        return Result.success(setMeals);
    }

    /**
     * 根据套餐Id查询包含的菜品
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getDishesBySetmealId(@PathVariable("id") Long setmealId){

        List<DishItemVO> dishItemVOS = dishService.getBySetmealId(setmealId);

        return Result.success(dishItemVOS);
    }
}

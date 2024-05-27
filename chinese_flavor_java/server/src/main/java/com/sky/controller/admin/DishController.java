package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "admin/dish")
@Slf4j
@Api(tags = "菜品controller")
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 新增菜品
     */
    @PostMapping()
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){

        String key ="dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(key);

        dishService.save(dishDTO);

        return Result.success();
    }

    /**
     * 菜品分页查询
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){

        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 菜品删除
     */
    @DeleteMapping()
    @ApiOperation("菜品删除")
    public Result deleteBatch(@RequestParam List<Long> ids){

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        dishService.deleteBatch(ids);

        return Result.success();
    }

    /**
     * 根据id查询菜品
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getByIdWithFlavor(@PathVariable("id") Long id){

        DishVO dishVO = dishService.getByIdWithFlavor(id);

        return Result.success(dishVO);
    }

    /**
     * 菜品修改
     */
    @PutMapping()
    @ApiOperation("菜品修改")
    public Result updateWithFlavor(@RequestBody DishDTO dishDTO){

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        dishService.updateWithFlavor(dishDTO);

        return Result.success();
    }

    /**
     * 菜品停售起售
     */
    @PostMapping(value = "/status/{status}")
    @ApiOperation("菜品停售起售")
    public Result startOrStop(@PathVariable("status") Integer status,Long id){

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        dishService.startOrStop(status,id);

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId,String name){

        List<Dish> list = dishService.list(categoryId);

        if(name != null && name != " " && categoryId == null){

            List<Dish> dishes = dishService.getByName(name);

            return Result.success(dishes);
        }

        return Result.success(list);
    }

}

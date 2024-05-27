package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
@Slf4j
public class SetMealController {

    @Resource
    private SetMealService setMealService;

    /**
     * 根据id查询套餐
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable("id") Long id){

        SetmealVO setmealVO = setMealService.getById(id);

        return Result.success(setmealVO);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询");

        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 套餐新增
     * @return
     */
    @PostMapping()
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setMealDTO.categoryId",condition = "#setMealDTO != null")
    public Result addWithDishes(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐");

        setMealService.addWithDishes(setmealDTO);

        return Result.success();
    }

    /**
     * 套餐起售停售
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable("status") Integer status,Long id){
        log.info("套餐起售停售");

        setMealService.startOrStop(status,id);

        return Result.success();
    }

    /**
     * 批量删除套餐
     */
    @DeleteMapping()
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteBatch(@RequestParam("ids") List<Long> ids){
        log.info("批量删除套餐");

        setMealService.deleteBatch(ids);

        return Result.success();
    }

    /**
     * 修改菜品
     */
    @PutMapping()
    @ApiOperation("修改菜品")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改菜品");

        setMealService.update(setmealDTO);

        return Result.success();
    }
}

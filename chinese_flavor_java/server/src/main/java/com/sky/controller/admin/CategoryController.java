package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/category")
@Slf4j
@Api(tags = "菜品分类")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    /**
     * 菜品分类分页查询
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> PageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("菜品分类查询信息：{}",categoryPageQueryDTO);

        Page<Category> page = categoryService.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> result = page.getResult();

        return Result.success(new PageResult(total,result));
    }

    /**
     * 菜品分类修改
     */
    @PutMapping()
    @ApiOperation("菜品分类修改")
    public Result update(@RequestBody CategoryDTO categoryDto){
        categoryService.update(categoryDto);

        return Result.success();
    }

    /**
     * 菜品分类删除
     */
    @DeleteMapping()
    @ApiOperation("菜品分类删除")
    public Result delete(Integer id){
        categoryService.delete(id);

        return Result.success();
    }

    /**
     * 菜品分类启用禁用
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品分类启用禁用")
    public Result startOrStop(@PathVariable("status") Integer status,Integer id){
        categoryService.startOrStop(id,status);

        return Result.success();
    }

    /**
     * 菜品分类新增
     */
    @PostMapping()
    @ApiOperation("菜品分类新增")
    public Result insert(@RequestBody CategoryDTO categoryDTO){
        categoryService.insert(categoryDTO);
        
        return Result.success();
    }

    /**
     * 根据类型查询分类
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);

        return Result.success(list);
    }
}

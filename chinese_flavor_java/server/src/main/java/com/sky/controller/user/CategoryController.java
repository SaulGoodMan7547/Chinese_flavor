package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("userCategoryController")
@Api(tags = "用户分类")
@RequestMapping("/user/category")
@Slf4j
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    /**
     * 分类展示
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){

        List<Category> categories = categoryService.selectByType(type);

        return Result.success(categories);
    }
}

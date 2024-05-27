package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("userDishController")
@Slf4j
@RequestMapping("/user/dish")
@Api("用户端菜品管理")
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 通过分类id查询菜品
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId){

        String key = "dish_" + categoryId;
        List<DishVO> dishVOS = (List<DishVO>)redisTemplate.opsForValue().get(key);

        if(dishVOS != null && dishVOS.size() != 0){
            return Result.success(dishVOS);
        }

        dishVOS = dishService.listVO(categoryId);

        redisTemplate.opsForValue().set(key,dishVOS);

        return Result.success(dishVOS);
    }
}

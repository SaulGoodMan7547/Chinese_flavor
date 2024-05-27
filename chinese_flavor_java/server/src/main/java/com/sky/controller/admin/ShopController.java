package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "店铺相关")
@RequestMapping("/admin/shop")
public class ShopController {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺状态
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺状态");

        redisTemplate.opsForValue().set("SHOP_STATUS",status);

        return Result.success();
    }

    /**
     * 获取店铺状态
     */
    @GetMapping("/status")
    @ApiOperation("管理员获取店铺状态")
    public Result getStatus(){

        Integer shop_status = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
        if(shop_status == null){
            redisTemplate.opsForValue().set("SHOP_STATUS",0);
        }

        log.info("status:{}",shop_status);
        return Result.success(shop_status);
    }
}

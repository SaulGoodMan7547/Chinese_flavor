package com.sky.controller.user;
//微信小程序appid:wxa32837a16a0eb457
//密钥：8ae895999113a59fb345ebb28741a7e3

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("userShopController")
@Slf4j
@Api(tags = "店铺相关")
@RequestMapping("/user/shop")
public class ShopController {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取店铺状态
     */
    @GetMapping("/status")
    @ApiOperation("用户获取店铺状态")
    public Result getStatus(){
        Integer shop_status = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");

        if(shop_status == null){
            return Result.error("当前店铺繁忙，请稍后再试");
        }

        return Result.success(shop_status);
    }
}

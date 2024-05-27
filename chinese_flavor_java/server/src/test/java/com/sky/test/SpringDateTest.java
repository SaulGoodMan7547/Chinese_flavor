package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class SpringDateTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        System.out.println(redisTemplate);

        //用于操作字符串类型
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //用于操作hash类型
        HashOperations hashOperations = redisTemplate.opsForHash();
        //用于操作list
        ListOperations listOperations = redisTemplate.opsForList();
        //用于操作set
        SetOperations setOperations = redisTemplate.opsForSet();
        //用于操作zset
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

    }

    @Test
    public void stringTest(){
        //set get setex setnx
        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set("name","jack");
        String name = (String)valueOperations.get("name");

        valueOperations.set("code","123456",50, TimeUnit.SECONDS);
        valueOperations.setIfAbsent("job","java");
    }

    @Test
    public void hashTest(){
        //hset hget hkeys hvals hdel
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user","name","jesse");
        hashOperations.put("user","job","java");

        hashOperations.get("user","name");

        Set user = hashOperations.keys("user");
        System.out.println(user);
        hashOperations.values("user");

        hashOperations.delete("user","name");
    }
}

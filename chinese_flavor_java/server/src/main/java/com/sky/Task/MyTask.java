package com.sky.Task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Task测试类
 */

@Slf4j
public class MyTask {

    @Scheduled(cron = "0/5 * * * * ? ")
    public void executeTask(){
        System.out.println(LocalDateTime.now());
    }
}

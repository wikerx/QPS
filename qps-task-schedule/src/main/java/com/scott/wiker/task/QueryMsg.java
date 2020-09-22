package com.scott.wiker.task;

import com.alibaba.fastjson.JSON;
import com.scott.wiker.entity.test.Users;
import com.scott.wiker.service.test.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :QueryMsg
 * @description :
 * @data :2020/9/21 0021 下午 4:49
 * @status : 编写
 **/
@Component
@Slf4j
public class QueryMsg {
    @Autowired
    private UserService userService;


    /**
     * 间隔10秒
     * 多线程任务并发调度
     * @throws InterruptedException
     */
    @Async
    @Scheduled(fixedDelay = 10000)
    public void first() throws InterruptedException {
        log.info("第一个定时任务开始 : {} \r\n线程 : {}" , LocalDateTime.now().toLocalTime(), Thread.currentThread().getName());
        Thread.sleep(1000 * 10);
    }

    /**
     * 间隔两秒
     * 多线程任务并发调度
     */
    @Async
    @Scheduled(fixedDelay = 10000)
    public void second() {
        log.info("第二个定时任务开始 : {}\r\n线程 :{} " ,LocalDateTime.now().toLocalTime(), Thread.currentThread().getName());
    }

    /**
     * 单线程任务调度
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void third() throws InterruptedException {
        log.info("cron独立单个线程执行任务调度1s一次-无堵塞 \r\n线程：{}" + Thread.currentThread().getName());
    }

    /**
     * 单线程任务调度
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void fourth() throws InterruptedException {
        log.info("cron独立单个线程执行任务调度1s一次-堵塞 \r\n线程：{}" + Thread.currentThread().getName());
        Thread.sleep(5000);
    }


    /**
     * 单线程任务调度 - 执行DB
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/1 * * * * *")
    public void queryDb() throws InterruptedException {
        List<Users> list = userService.selectListUsers();
        log.info("用户列表：{}",JSON.toJSONString(list));
    }

}

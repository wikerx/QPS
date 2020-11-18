package com.scott.wiker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :RedisTestController
 * @description : 也可以使用@Cacheable等注解方式将数据分组使用缓存
 * @data :2020/11/17 0017 下午 3:26
 * @status : 编写
 **/
@RestController
@RequestMapping(value = "/redis")
@Slf4j
public class RedisTestController {

    /**
     * 读取和写入缓存
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试
     * @return
     */
    @RequestMapping(value = "test1")
    public String test1(){
        redisTemplate.opsForValue().set("test:set1", "testValue1");
        redisTemplate.opsForSet().add("test:set2", "testValue2");
        redisTemplate.opsForHash().put("hash1", "name1", "lms1");
        redisTemplate.opsForHash().put("hash1", "name2", "lms2");
        redisTemplate.opsForHash().put("hash1", "name3", "lms3");
        log.info("String:test:set   {}",redisTemplate.opsForValue().get("test:set1").toString());
        log.info("Hash:hash1:name1   {}",redisTemplate.opsForHash().get("hash1", "name1").toString());
        return "Redis数据处理OK";
    }

}

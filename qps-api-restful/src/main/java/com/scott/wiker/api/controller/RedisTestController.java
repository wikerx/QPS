package com.scott.wiker.api.controller;

import com.alibaba.fastjson.JSON;
import com.scott.wiker.entity.TradeRecordEntity;
import com.scott.wiker.service.DbService;
import com.scott.wiker.utils.DefaultStringConfig;
import com.scott.wiker.utils.OrderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :RedisTestController
 * @description : 也可以使用@Cacheable等注解方式将数据分组使用缓存
 * @data :2020/11/17 0017 下午 3:26
 * @status : 编写
 **/
@Api("RabbitMQ-Redis接口管理")
@RestController
@RequestMapping(value = "/redis")
@Slf4j
public class RedisTestController {

    /**
     * 读取和写入缓存
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DbService dbService;

    /**
     * 测试
     * @return
     */

    @ApiOperation(value = "Redis-putHash")
    @RequestMapping(value = "testHash")
    public String testHash(){
        redisTemplate.opsForValue().set("test:set1", "testValue1");
        redisTemplate.opsForSet().add("test:set2", "testValue2");
        redisTemplate.opsForHash().put("hash1", "name1", "lms1");
        redisTemplate.opsForHash().put("hash1", "name2", "lms2");
        redisTemplate.opsForHash().put("hash1", "name3", "lms3");
        log.info("String:test:set   {}",redisTemplate.opsForValue().get("test:set1").toString());
        log.info("Hash:hash1:name1   {}",redisTemplate.opsForHash().get("hash1", "name1").toString());
        return "Redis数据处理OK";
    }

    /**
     * 订单查询
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Redis查询")
    @RequestMapping(value = "selectCacheable/{tradeNo}")
    public Object selectCacheable(@PathVariable(required = false) String tradeNo) throws Exception {
        TradeRecordEntity record = dbService.selectByOrderNo1(tradeNo);
        log.info("缓存-订单查询：{}",JSON.toJSONString(record));
        return JSON.toJSONString(record);
    }

    /**
     * 订单更新
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Redis更新")
    @RequestMapping(value = "selectCachePut")
    public Object selectCachePut() throws Exception {
        String tradeNo = OrderUtils.getOrderIdByTime();
        TradeRecordEntity record = dbService.selectByOrderNo2(tradeNo);
        log.info("缓存-订单更新：{}",JSON.toJSONString(record));
        return JSON.toJSONString(record);
    }

    /**
     * 订单删除
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Redis删除")
    @RequestMapping(value = "redisCacheEvict/{tradeNo}")
    public Object redisCacheEvict(@PathVariable(required = false) String tradeNo) {
        String result = dbService.deleteByOrderNo(tradeNo);
        log.info("缓存-订单删除：{}",result);
        return result;
    }

    /**
     * 查询redis列表
     * @return
     */
    @ApiOperation(value = "Redis查询")
    @RequestMapping(value = "redisList")
    public Object redisList(){
        List<TradeRecordEntity> list = redisTemplate.getClientList();
        Set keys = redisTemplate.keys(DefaultStringConfig.ORDER_LIST + "*");
//        log.info("keys:{}",JSON.toJSONString(keys));
        List strings = redisTemplate.opsForValue().multiGet(keys);
//        for (int i = 0; i < strings.size(); i++){
//            log.info(JSON.toJSONString(strings));
//        }
//        清空所有key
        redisTemplate.delete(keys);

        log.info(JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }


}

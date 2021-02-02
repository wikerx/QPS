package com.scott.wiker.service.impl;

import com.scott.wiker.entity.TradeRecordEntity;
import com.scott.wiker.service.DbService;
import com.scott.wiker.utils.DefaultStringConfig;
import com.scott.wiker.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @calssname:DbServiceImpl
 * @author:Mr.薛
 * @date:2021/1/29 11:25
 * @version:v1.0
 * @status:create
 */
@Service
@Slf4j
public class DbServiceImpl implements DbService {


    /**
     * 订单号查询订单信息 - 查询缓存
     * @param orderNo
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Cacheable(cacheNames = {DefaultStringConfig.ORDER_LIST},
            key = "#orderNo",
            unless = "#result==null")
    public TradeRecordEntity selectByOrderNo1(String orderNo) throws Exception {
        TradeRecordEntity record = new TradeRecordEntity();
        return record;
    }


    /**
     * 订单号查询-更新订单信息 - 更新缓存
     * @param orderNo
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @CachePut(cacheNames = {DefaultStringConfig.ORDER_LIST},
            key = "#orderNo",
            unless = "#result==null")
    public TradeRecordEntity selectByOrderNo2(String orderNo) throws Exception {
        TradeRecordEntity record = new TradeRecordEntity();
        record.setTradeNo(orderNo);
        record.setMerchantNo("110056111834");
        record.setMerOrderNo(String.valueOf(System.currentTimeMillis()));
        record.setRespCode("00");
        record.setRespDesc("Approved");
        record.setStatus(1);
        record.setTradeAmount(new BigDecimal(OrderUtils.nextDouble(100,1000)));
        record.setTradeCurrency("USD");
        return record;
    }


    /**
     * 数据清除 - 缓存
     * @param orderNo
     * allEntries : true  无视key值，直接清空cacheNames中的所有元素集合，false，清除指定key中的数据
     * beforeInvocation ： true Spring会在调用该方法之前清除缓存中的指定元素，false ：先执行方法在清除key'
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @CacheEvict(cacheNames = DefaultStringConfig.ORDER_LIST,
            key = "#orderNo",
            allEntries = false,
            beforeInvocation = false)
    public String deleteByOrderNo(String orderNo){
        return "清除命令已经执行！";
    }

}

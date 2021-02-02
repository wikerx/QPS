package com.scott.wiker.service;

import com.scott.wiker.entity.TradeRecordEntity;

/**
 * @calssname:DbService
 * @author:Mr.薛
 * @date:2021/1/29 11:17
 * @version:v1.0
 * @status:create
 * 模拟数据库数据交互
 */
public interface DbService {
    /**
     * 订单号查询订单信息
     * @param orderNo
     * @return
     */
    TradeRecordEntity selectByOrderNo1(String orderNo) throws Exception;

    /**
     * 订单号查询-更新订单信息
     * @param orderNo
     * @return
     */
    TradeRecordEntity selectByOrderNo2(String orderNo) throws Exception;

    /**
     * 数据清除 - 缓存
     * @param OrderNo
     * @return
     */
    String deleteByOrderNo(String OrderNo);

}

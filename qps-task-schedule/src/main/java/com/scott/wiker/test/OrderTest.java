package com.scott.wiker.test;

import com.scott.wiker.utils.OrderUtils;
import com.scott.wiker.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :OrderTest
 * @description :
 * @data :2020/11/30 0030 上午 10:41
 * @status : 编写
 **/
@Slf4j
public class OrderTest {

    public static void main(String[] args) {
        SnowFlake flake = new SnowFlake(2,3);
        log.info("订单号  ：{}" , flake.nextId());
        log.info("订单号  ：{}" , OrderUtils.getOrderCode(1));
        log.info("退货单号：{}" , OrderUtils.getReturnCode(2));
        log.info("退款单号：{}" , OrderUtils.getRefundCode(3));
    }

}

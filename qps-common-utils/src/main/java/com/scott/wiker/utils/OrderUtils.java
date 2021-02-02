package com.scott.wiker.utils;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :OrderUtils
 * @description :订单编码码生成器，生成32位数字编码，
 * 生成规则 1位单号类型+17位时间戳+14位(用户id加密&随机数)
 * @data :2020/11/30 0030 上午 9:49
 * @status : 编写
 **/
public class OrderUtils {
    /**
     * 订单类别头
     */

    private static final String ORDER_CODE = "T";
    /**
     * 退货类别头
     */

    private static final String RETURN_ORDER = "R";
    /**
     * 退款类别头
     */

    private static final String REFUND_ORDER = "F";


    /**
     * 随即编码
     */

    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /**
     * 用户id和随机数总长度
     */

    private static final int maxLength = 14;

    /**
     * 根据id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Integer userId) {
        String idStr = userId.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i) - '0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     * 生成17位时间戳
     */
    public static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 生成固定长度随机码
     *
     * @param n 长度
     */

    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new SecureRandom().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }


    /**
     * 生成不带类别标头的编码
     *
     * @param userId
     */
    private static synchronized String getCode(Integer userId) {
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }


    /**
     * 生成订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */

    public static String getOrderCode(Integer userId) {
        return ORDER_CODE + getCode(userId);
    }


    /**
     * 生成退货单号编码（调用方法）
     * @param userId 网站中该用户唯一ID 防止重复
     */
    public static String getReturnCode(Integer userId) {
        return RETURN_ORDER + getCode(userId);
    }


    /**
     * 生成退款单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getRefundCode(Integer userId) {
        return REFUND_ORDER + getCode(userId);
    }

    /**
     * 生成时间订单号
     * @return
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newDate=sdf.format(new Date());
        String result="";
        SecureRandom random=new SecureRandom();
        for(int i = 0; i < 3; i++){
            result += random.nextInt(10);
        }
        return newDate+result;
    }

    /**
     * 随机数
     * @param min
     * @param max
     * @return
     * @throws Exception
     */
    public static double nextDouble(final double min, final double max) throws Exception {
//保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        return  Double.parseDouble(df.format(min + ((max - min) * new SecureRandom().nextDouble())));
    }


    public static void main(String[] args) {
        System.out.println("时间戳  ：" + getDateTime());
        System.out.println("订单号  ：" + getOrderCode(1));
        System.out.println("退货单号：" + getReturnCode(2));
        System.out.println("退款单号：" + getRefundCode(3));
    }
}

package com.scott.wiker.utils;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :MsgContent
 * @description : 所有用到的常规数据常量
 * @data :2020/9/23 0023 下午 4:28
 * @status : 编写
 **/
public class MsgContent {
    public final static String CONTENY_TYPE = "text/html; charset=utf-8";
    public final static String CONTENY_TYPE_JSON = "application/json";
    public final static String ENCODING_UTF8 = "UTF-8";
    public final static String ENCODING_MD5 = "MD5";
    public final static String TIMESTAMP = "Timestamp";
    public final static String MID = "Mid";
    public final static String AUTHENTICATION = "Authentication";
    public final static String BASIC = "Basic ";
    public final static String APPID = "appid";
    public final static String ALLOW_ORIGN = "Access-Control-Allow-Origin";
    public final static String ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public final static String ALLOW_TRUE = "TRUE";
    /**
     * 超时时间5分钟 - 授权只能接受的时间差为5分钟
     */
    public final static Long AUTHORIZATION_TIME_LIMIT = 5*60*1000L;
}

package com.scott.wiker.exception;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :ResponseUtils
 * @description :
 * @data :2020/8/31 0031 上午 10:05
 * @status : 编写
 **/
public class ResponseUtils {
    /**
     * 调用成功
     */
    private static final String SUCCESS = "调用成功！";

    public static Response success(Object obj){
        Response res = new Response();
        res.setCode(200);
        res.setData(obj);
        res.setMsg(SUCCESS);
        return res;
    }

    public static Response success(){
        return success(null);
    }

    public static Response error(Integer code, String msg){
        Response res = new Response();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

}
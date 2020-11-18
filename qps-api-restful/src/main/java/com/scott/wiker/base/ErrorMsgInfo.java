package com.scott.wiker.base;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :ErrorMsgInfo
 * @description : 定义异常数据信息的实体及细化
 * @data :2020/9/23 0023 下午 4:29
 * @status : 编写
 **/
@Slf4j
@Data
public class ErrorMsgInfo {
    /**
     * 标识错误的出
     */
    private int errorCode;
    /***
     * 对外返回码
     */
    private String respCode;
    /**
     * 返回码说明 - 英文
     */
    private String respDescUs;
    /**
     * 返回码说明 - 中文
     */
    private String respDescCn;


    public ErrorMsgInfo getErrorInfo(int errorCode) {
        ErrorMsgInfo msg = new ErrorMsgInfo();
        String respCode = "";
        String respDescUs = "";
        String respDescCn = "";
        switch (errorCode) {
            case 0:
                respCode = "F0000";
                respDescCn = "交易失败";
                respDescUs = "Transaction Failed";
                break;
            case 1:
                respCode = "F0001";
                respDescCn = "交易成功";
                respDescUs = "Approved";
                break;
            case 2:
                respCode = "F0002";
                respDescCn = "系统繁忙，请稍后再试";
                respDescUs = "The system is busy. Please try again later";
                break;
            case 3:
                respCode = "F0003";
                respDescCn = "head信息缺失";
                respDescUs = "[Authentication]Missing head data!";
                break;
            case 4:
                respCode = "F0004";
                respDescCn = "商户号不存在";
                respDescUs = "[Authentication]Mid Non-existent!";
                break;
            case 5:
                respCode = "F0005";
                respDescCn = "过期的请求";
                respDescUs = "[Authentication]Visit expired!";
                break;
            case 6:
                respCode = "F0006";
                respDescCn = "请求失败，请检查head参数";
                respDescUs = "[Authentication]Authentication request failed, please check the head parameter configuration!";
                break;
            case 7:
                respCode = "F0007";
                respDescCn = "商户信息错误或商户秘钥未配置";
                respDescUs = "[Authentication]Merchant information error or merchant secret key not configured!";
                break;
            default:
                respCode = "F9999";
                respDescCn = "未知异常，请稍后再试";
                respDescUs = "Unknown exception, please try again later";
                break;
        }

        msg.setErrorCode(errorCode);
        msg.setRespCode(respCode);
        msg.setRespDescUs(respDescUs);
        msg.setRespDescCn(respDescCn);
        return msg;
    }


    public static void main(String[] args) {
        ErrorMsgInfo msg = new ErrorMsgInfo().getErrorInfo(2);
        log.info(JSON.toJSONString(msg));
    }

}

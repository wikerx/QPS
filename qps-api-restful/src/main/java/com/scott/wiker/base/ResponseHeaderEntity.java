package com.scott.wiker.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :ResponseHeaderEntity
 * @description :商户返回信息中的头信息
 * @data :2020/9/23 0023 下午 3:48
 * @status : 编写
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseHeaderEntity {
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 请求响应码
     */
    private String respCode;
    /**
     * 请求响应说明
     */
    private String respDesc;
    /**
     * 交易编码
     */
    private String tranCode;

}

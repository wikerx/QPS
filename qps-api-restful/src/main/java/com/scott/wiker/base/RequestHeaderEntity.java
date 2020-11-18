package com.scott.wiker.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :RequestHeaderEntity
 * @description : 请求中的实体对象 - 头信息
 * @data :2020/9/23 0023 下午 3:46
 * @status : 编写
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RequestHeaderEntity {
    /**
     * 交易编码
     */
    private String tranCode;

    /**
     * 商户号
     */
    private String merchantNo;

}

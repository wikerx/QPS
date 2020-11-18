package com.scott.wiker.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :TransMsgEntity
 * @description : 交易信息 包括报文头和报文体
 * @data :2020/9/23 0023 下午 3:36
 * @status : 编写
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TransMsgEntity {
    /**
     * 请求或者响应数据的报文头
     */
    private Object head;

    /**
     * 请求或者响应的报文体
     */
    private Object body;

}

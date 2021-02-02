package com.scott.wiker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @calssname:TradeRecordEntity
 * @author:Mr.薛
 * @date:2021/1/29 11:19
 * @version:v1.0
 * @status:create
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeRecordEntity {
    private String tradeNo;
    private String tradeCurrency;
    private BigDecimal tradeAmount;
    private int status;
    private String respCode;
    private String respDesc;
    private String merchantNo;
    private String merOrderNo;


}

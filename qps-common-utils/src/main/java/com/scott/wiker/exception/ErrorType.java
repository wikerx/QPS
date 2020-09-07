package com.scott.wiker.exception;

import lombok.AllArgsConstructor;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :ErrorType
 * @description :
 * @data :2020/8/31 0031 上午 10:02
 * @status : 编写
 **/
@AllArgsConstructor
public enum  ErrorType {
    /**
     * 错误类型
     */
    OBJECT_NOT_FOUND(0,"代码异常"),
    INVALID_PARAMS(1,"参数不正确"),
    SYSTEM_ERROR(2,"系统异常"),
    REQUEST_ERROR(3,"请求异常"),
    INTERNAL_PROCESSING_EXCEPTION(4,"内部处理异常"),
    CODING_EXCEPTION(5,"编码异常")

    ;

    /**
     * 错误码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

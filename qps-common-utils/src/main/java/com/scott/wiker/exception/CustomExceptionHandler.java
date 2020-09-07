package com.scott.wiker.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :CustomExceptionHandler
 * @description :
 * @data :2020/8/31 0031 上午 10:06
 * @status : 编写
 **/
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * @ExceptionHandler相当于controller的@RequestMapping
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Response handle(CustomException se){
        return ResponseUtils.error(se.getCode(),se.getMessage());
    }
}

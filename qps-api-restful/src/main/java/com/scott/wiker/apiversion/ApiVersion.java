package com.scott.wiker.apiversion;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @ClassName :ApiVersion
 * @Description : 接口版本控制器
 * @Author :Mr.薛
 * @Data :2019/12/17 0017 上午 11:20
 * @Version :V1.0
 * @Status : 编写
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    /**
     * 标识版本号
     * int 类型表示版本系列 eg：1
     * @return
     */
    int value() default 1;
}
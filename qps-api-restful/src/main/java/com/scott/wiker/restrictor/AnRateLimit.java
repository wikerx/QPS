package com.scott.wiker.restrictor;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @InterfaceName :AnRateLimiter
 * @Description :自定义注解
 * @Author :Mr.薛
 * @Data :2019/11/15  10:03
 * @Version :V1.0
 * @Status : 编写
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AnRateLimit {
    /**
     * 以固定数值往令牌桶添加令牌
     * @return
     */
    double permitsPerSecond() default 200;

    /**
     * 获取令牌最大等待时间 默认3秒
     * @return
     */
    long timeout() default 3;

    /**
     * 单位(例:分钟/秒/毫秒) 默认:秒
     * @return
     */
    TimeUnit timeunit() default TimeUnit.SECONDS;

    /**
     * 无法获取令牌返回提示信息 默认值可以自行修改
     * @return
     */
    String msg() default "The system is busy. Please try again later.";

    /**
     * 是否需要授权
     * 暂时吧鉴权和限流分开，以后考虑合并的时候再使用这个字段
     * @return
     */
    boolean needAuth() default true;
}

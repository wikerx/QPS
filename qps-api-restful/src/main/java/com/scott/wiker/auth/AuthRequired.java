package com.scott.wiker.auth;
/**
 * 在需要授权验证的Controller的方法上使用此注解
 * @author Mr.薛
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.METHOD)
//运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {
    /**
     * 只要加入注解默认就需要鉴权
     * 默认都需要授权 不需要授权的就不用引入注解
     * @return
     */
    boolean needs() default true;
}
package com.scott.wiker.restrictor;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.scott.wiker.base.ErrorMsgInfo;
import com.scott.wiker.base.ResponseHeaderEntity;
import com.scott.wiker.base.TransMsgEntity;
import com.scott.wiker.utils.MsgContent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName :RateLimiterAspect
 * @Description :DOTO
 * @Author :Mr.薛
 * @Data :2019/11/15  10:05
 * @Version :V1.0
 * @Status : 编写
 **/
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    /**
     * 使用url做为key,存放令牌桶 防止每次重新创建令牌桶
     */
    private Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();

    @Pointcut("@annotation(com.scott.wiker.restrictor.AnRateLimit)")
    public void anRateLimiter() {
    }

    @SuppressWarnings("unused")
	@Around("anRateLimiter()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取request,response
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 或者url(存在map集合的key)
        String url = request.getRequestURI();
        // 获取自定义注解
        AnRateLimit rateLimiter = getAnRateLimiter(joinPoint);
//        log.info("<<=================  请求【"+url+"】,创建令牌桶,容量{"+rateLimiter.permitsPerSecond()+"} 成功!!!  ");
        if (rateLimiter != null) {
            RateLimiter limiter = null;
            // 判断map集合中是否有创建有创建好的令牌桶
            if (!limitMap.containsKey(url)) {
                // 创建令牌桶
                limiter = RateLimiter.create(rateLimiter.permitsPerSecond());
                limitMap.put(url, limiter);
//                log.info("<<=================  请求【"+url+"】,创建令牌桶,容量{"+rateLimiter.permitsPerSecond()+"} 成功!!!  ");
            }
            limiter = limitMap.get(url);
            // 获取令牌
            boolean acquire = limiter.tryAcquire(rateLimiter.timeout(), rateLimiter.timeunit());
            if (!acquire) {
//                限流 - 返回失败
                return this.limiting();
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 获取注解对象
     * @param joinPoint 对象
     * @return ten LogAnnotation
     */
    private AnRateLimit getAnRateLimiter(final JoinPoint joinPoint) {
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        String name = joinPoint.getSignature().getName();
        if (!StringUtils.isEmpty(name)) {
            for (Method method : methods) {
                AnRateLimit annotation = method.getAnnotation(AnRateLimit.class);
                if (!Objects.isNull(annotation) && name.equals(method.getName())) {
                    return annotation;
                }
            }
        }
        return null;
    }

    /**
     * 自定义响应结果
     *
     * @param response 响应
     * @param code     响应码
     */
    @SuppressWarnings("unused")
	private void responseResult(HttpServletResponse response, Integer code) {
        response.resetBuffer();
        response.setHeader(MsgContent.ALLOW_ORIGN, "*");
        response.setHeader(MsgContent.ALLOW_CREDENTIALS,
                MsgContent.ALLOW_TRUE);
        response.setContentType(MsgContent.CONTENY_TYPE_JSON);
        response.setCharacterEncoding(MsgContent.ENCODING_UTF8);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            ResponseHeaderEntity head = new ResponseHeaderEntity();
            TransMsgEntity transMsgEntity = new TransMsgEntity();
            ErrorMsgInfo info = new ErrorMsgInfo().getErrorInfo(code);
            head.setRespCode(info.getRespCode());
            head.setRespDesc(info.getRespDescUs());

            transMsgEntity.setHead(head);

            writer.println(JSON.toJSONString(transMsgEntity));
            response.flushBuffer();
        } catch (IOException e) {
            log.error(" 输入响应出错 e = {}" + e.getMessage() + e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * 限流
     * @return
     */
    private String limiting(){
        ResponseHeaderEntity head = new ResponseHeaderEntity();
        TransMsgEntity transMsgEntity = new TransMsgEntity();
        ErrorMsgInfo info = new ErrorMsgInfo().getErrorInfo(2);
        head.setRespCode(info.getRespCode());
        head.setRespDesc(info.getRespDescUs());

        transMsgEntity.setHead(head);

        return JSON.toJSONString(transMsgEntity);
    }
}
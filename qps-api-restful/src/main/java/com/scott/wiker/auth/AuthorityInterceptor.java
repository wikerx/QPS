package com.scott.wiker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ClassName :AuthorityInterceptor
 * @Description :
 * @Author :Mr.薛
 * @Data :2019/12/16 0016 下午 1:54
 * @Version :V1.0
 * @Status : 编写
 **/
@Slf4j
@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // ①:START 方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AuthRequired authLimit = handlerMethod.getMethodAnnotation(AuthRequired.class);

        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        AuthRequired methodAnnotation = method.getAnnotation(AuthRequired.class);
        // 有 @AuthRequired 注解，需要认证
        if (methodAnnotation != null) {
//            拦截API请求头部   true 通过    false 拦截
            return new Authentication().authentication(request, response);
        }

        return true;
    }

    /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }
    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    }


}
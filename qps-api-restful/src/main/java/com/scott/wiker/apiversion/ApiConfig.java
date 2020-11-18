package com.scott.wiker.apiversion;

import com.scott.wiker.auth.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;


/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :ApiConfig
 * @description :
 * @data :2020/9/24 0024 上午 9:56
 * @status : 编写
 **/
@SpringBootConfiguration
public class ApiConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping(){
        RequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }

    /**
     * 重写请求过处理的方法
     * @param contentNegotiationManager
     * @param conversionService
     * @param resourceUrlProvider
     * @return
     */
//    @Override
//    public RequestMappingHandlerMapping requestMappingHandlerMapping(
//            ContentNegotiationManager contentNegotiationManager,
//            FormattingConversionService conversionService,
//            ResourceUrlProvider resourceUrlProvider) {
//        RequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping();
//        handlerMapping.setOrder(0);
//        return handlerMapping;
//    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exculdPath = {"/static/**","*.js","*.css"};
        // 拦截所有请求，通过判断是否有 @AuthRequired 注解 决定是否需要验证
        registry.addInterceptor(authorityInterceptor).addPathPatterns("/**").excludePathPatterns(exculdPath);
        super.addInterceptors(registry);
    }

    /**
     * 静态资源释放
     * */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
//
//    @Bean
//    public AuthorityInterceptor authorityInterceptor() {
//        return new AuthorityInterceptor();
//    }

}
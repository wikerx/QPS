package com.scott.wiker.apiversion;

import com.scott.wiker.auth.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.Charset;
import java.util.List;


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
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }


    /**
     * 建议使用该方法进行HttpMessageConverters的修改，此时的converters已经是Spring初始化过的
     * 因为加入了WebMvcConfigure，导致Spring的HttpMessageConverters中的String转换类默认使用ISO-8859-1编码
     * 所以这里对Spring已经初始化过的StringHttpMessageConverter的编码进行修改
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(converter -> {
            if (converter instanceof StringHttpMessageConverter){
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        });
    }

//    @Bean
//    public AuthorityInterceptor authorityInterceptor() {
//        return new AuthorityInterceptor();
//    }


}
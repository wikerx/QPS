package com.scott.wiker;

import com.scott.wiker.dataSource.aop.DynamicDataSourceAnnotationAdvisor;
import com.scott.wiker.dataSource.aop.DynamicDataSourceAnnotationInterceptor;
import com.scott.wiker.dataSource.register.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import(DynamicDataSourceRegister.class)
@MapperScan("com.scott.wiker.mapper.*")
@SpringBootApplication
/**
 * 开始事务管理
 * */
@EnableTransactionManagement
/**
 * 开启注解驱动的缓存管理
 * */
@EnableCaching
/**
 * 开启定时任务扫描
 * */
@EnableScheduling
public class ApiApplication {

    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}

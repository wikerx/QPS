package com.scott.wiker;

import com.scott.wiker.dataSource.aop.DynamicDataSourceAnnotationAdvisor;
import com.scott.wiker.dataSource.aop.DynamicDataSourceAnnotationInterceptor;
import com.scott.wiker.dataSource.register.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import(DynamicDataSourceRegister.class)
@MapperScan("com.scott.wiker.mapper.*")
@SpringBootApplication
@EnableTransactionManagement
public class ApiApplication {

    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}

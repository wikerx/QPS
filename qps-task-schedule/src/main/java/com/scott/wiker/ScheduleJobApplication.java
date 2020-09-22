package com.scott.wiker;

import com.scott.wiker.dataSource.aop.DynamicDataSourceAnnotationAdvisor;
import com.scott.wiker.dataSource.aop.DynamicDataSourceAnnotationInterceptor;
import com.scott.wiker.dataSource.register.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mr.薛
 * 任务调度系统 - zookeeper调度任务
 */
@Import(DynamicDataSourceRegister.class)
@MapperScan("com.scott.wiker.mapper.*")
@SpringBootApplication
@EnableTransactionManagement
/**
 * 开启定时任务扫描
 */
@EnableScheduling
/**
 * 开启多线程
 */
@EnableAsync
public class ScheduleJobApplication {

    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }

    public static void main(String[] args) {
        SpringApplication.run(ScheduleJobApplication.class, args);
    }

}

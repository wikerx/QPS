package com.scott.wiker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Mr.薛
 */
@SpringBootApplication
@EnableScheduling
public class QpsMessageCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QpsMessageCenterApplication.class, args);
    }

}

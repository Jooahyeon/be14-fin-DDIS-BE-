package com.ddis.ddis_hr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DdisHrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdisHrApplication.class, args);
    }

}

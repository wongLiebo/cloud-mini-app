package com.mini.cloud.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(value="com.mini.cloud.**.modules.**.mapper")
@ComponentScan(basePackages = {"com.mini"})
@EnableScheduling
public class MiniAppApplication {
    public static void main(String[] args) {

        SpringApplication.run(MiniAppApplication.class,args);
    }


}
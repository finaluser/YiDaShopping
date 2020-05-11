package com.chen.mmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: MmallSpringBoot
 * @description:
 * @author: Mr.Wang
 * @create: 2020-03-20 17:13
 **/
@SpringBootApplication
@ComponentScan("com")
@MapperScan("com.chen.mmall.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

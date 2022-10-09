package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/*
 * @author 孟亚辉
 * @time ${DATE} ${TIME}
 */
@SpringBootApplication
@MapperScan("com.blog.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
package org.easley.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "org.easley.demo.mapper")
@SpringBootApplication(scanBasePackages = "org.easley.demo")
public class JdbcDemoBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(JdbcDemoBootstrap.class, args);
    }
}

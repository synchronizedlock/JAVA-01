package org.easley.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动类
 *
 * @author Easley
 * @date 2021/3/13
 * @since 1.0
 */
@MapperScan(basePackages = "org.easley.sharding.mapper")
@SpringBootApplication
public class AppBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(AppBootstrap.class, args);
    }
}

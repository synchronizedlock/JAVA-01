package org.easley.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
@MapperScan(basePackages = "org.easley.dynamic.mapper")
@SpringBootApplication(scanBasePackages = "org.easley.dynamic")
public class SimpleDynamicDsBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SimpleDynamicDsBootstrap.class, args);
    }
}

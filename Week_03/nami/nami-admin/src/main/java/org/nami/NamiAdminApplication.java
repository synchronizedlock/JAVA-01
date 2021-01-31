package org.nami;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "org.nami.mapper")
@SpringBootApplication
public class NamiAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(NamiAdminApplication.class, args);
    }
}

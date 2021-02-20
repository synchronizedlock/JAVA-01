package org.easley;

import org.easley.beans.CustomDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "org.easley")
public class DemoBootstrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoBootstrap.class, args);
        // 打印yml配置的数据源信息
        System.out.println(ctx.getBean(CustomDataSourceProperties.class));
    }
}

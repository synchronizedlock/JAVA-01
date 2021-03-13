package org.easley.sharding.config;

import org.easley.sharding.utils.IdWorker;
import org.easley.sharding.utils.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类
 *
 * @author Easley
 * @date 2021/3/13
 * @since 1.0
 */
@Configuration
public class AppConfig {

    @Bean
    public IdWorker idWorker() {
        return new SnowflakeIdWorker(0, 0);
    }
}

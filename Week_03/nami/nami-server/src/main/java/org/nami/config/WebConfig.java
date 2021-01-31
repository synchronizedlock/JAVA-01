package org.nami.config;

import org.nami.filter.PluginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * WebConfig
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Configuration
@EnableWebFlux
@EnableConfigurationProperties(ServerConfigProperties.class)
public class WebConfig {

    @Bean
    public PluginFilter pluginFilter(@Autowired ServerConfigProperties properties) {
        return new PluginFilter(properties);
    }
}

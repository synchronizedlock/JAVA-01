package org.easley.beans.autoconf;

import org.easley.beans.CustomDataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义数据源配置自动装配
 *
 * @author Easley
 * @date 2021/2/21
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(value = {CustomDataSourceProperties.class})
public class CustomDataSourceAutoConfigure {
}

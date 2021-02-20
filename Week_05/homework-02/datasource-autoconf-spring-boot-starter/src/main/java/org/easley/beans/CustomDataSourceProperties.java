package org.easley.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义数据源属性
 *
 * @author Easley
 * @date 2021/2/21
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "datasource")
public class CustomDataSourceProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;
}

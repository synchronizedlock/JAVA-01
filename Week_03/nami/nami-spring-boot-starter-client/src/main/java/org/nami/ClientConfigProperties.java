package org.nami;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ClientConfigProperties
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "nami.http")
public class ClientConfigProperties {

    /**
     * 启动端口号
     */
    private Integer port;

    /**
     * 请求根路径
     */
    private String contextPath;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 接口版本号
     */
    private String version;

    private String adminUrl;
}

package org.nami.config;

import lombok.Data;
import org.nami.constants.LoadBalanceConstants;
import org.nami.enums.NamiExceptionEnum;
import org.nami.exception.NamiException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ServerConfigProperties
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "nami.gateway")
public class ServerConfigProperties implements InitializingBean {

    /**
     * 负载均衡算法，默认轮询
     */
    private String loadBalance = LoadBalanceConstants.ROUND;

    /**
     * 网关超时时间，默认3s
     */
    private Long timeOutMillis = 3000L;

    /**
     * 缓存刷新间隔，默认10s
     */
    private Long cacheRefreshInterval = 10L;

    private Integer webSocketPort;

    @Override
    public void afterPropertiesSet() {
        if (this.webSocketPort == null || this.webSocketPort <= 0) {
            throw new NamiException(NamiExceptionEnum.CONFIG_ERROR);
        }
    }
}

package org.nami.filter;

import org.nami.cache.ServiceCache;
import org.nami.chain.PluginChain;
import org.nami.config.ServerConfigProperties;
import org.nami.enums.NamiExceptionEnum;
import org.nami.exception.NamiException;
import org.nami.plugin.AuthPlugin;
import org.nami.plugin.DynamicRoutePlugin;
import org.springframework.http.server.RequestPath;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * PluginFilter
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class PluginFilter implements WebFilter {

    private ServerConfigProperties properties;

    public PluginFilter(ServerConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String appName = parseAppName(exchange);
        if (CollectionUtils.isEmpty(ServiceCache.getAllInstances(appName))) {
            throw new NamiException(NamiExceptionEnum.SERVICE_NOT_FIND);
        }
        PluginChain pluginChain = new PluginChain(properties, appName);
        pluginChain.addPlugin(new DynamicRoutePlugin(properties));
        pluginChain.addPlugin(new AuthPlugin(properties));
        return pluginChain.execute(exchange, pluginChain);
    }

    private String parseAppName(ServerWebExchange exchange) {
        RequestPath path = exchange.getRequest().getPath();
        String appName = path.value().split("/")[1];
        return appName;
    }
}

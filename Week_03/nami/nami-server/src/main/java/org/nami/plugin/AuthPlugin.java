package org.nami.plugin;

import org.nami.chain.PluginChain;
import org.nami.config.ServerConfigProperties;
import org.nami.enums.NamiPluginEnum;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * AuthPlugin
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class AuthPlugin extends AbstractNamiPlugin {

    public AuthPlugin(ServerConfigProperties properties) {
        super(properties);
    }

    @Override
    public Integer order() {
        return NamiPluginEnum.AUTH.getOrder();
    }

    @Override
    public String name() {
        return NamiPluginEnum.AUTH.getName();
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        System.out.println("auth plugin");
        return pluginChain.execute(exchange, pluginChain);
    }
}

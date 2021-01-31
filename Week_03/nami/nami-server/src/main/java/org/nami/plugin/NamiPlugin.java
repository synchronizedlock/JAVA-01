package org.nami.plugin;


import org.nami.chain.PluginChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Nami Plugin
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface NamiPlugin {

    /**
     * lower values have higher priority
     */
    Integer order();

    /**
     * return current plugin name
     */
    String name();

    Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain);
}

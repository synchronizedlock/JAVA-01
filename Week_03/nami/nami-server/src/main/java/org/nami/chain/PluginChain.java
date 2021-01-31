package org.nami.chain;

import org.nami.cache.PluginCache;
import org.nami.config.ServerConfigProperties;
import org.nami.plugin.AbstractNamiPlugin;
import org.nami.plugin.NamiPlugin;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * PluginChain
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class PluginChain extends AbstractNamiPlugin {

    /**
     * the pos point to current plugin
     */
    private int pos;

    /**
     * the plugins of chain
     */
    private List<NamiPlugin> plugins;

    private final String appName;

    public PluginChain(ServerConfigProperties properties, String appName) {
        super(properties);
        this.appName = appName;
    }

    /**
     * add enabled plugin to chain
     */
    public void addPlugin(NamiPlugin namiPlugin) {
        if (plugins == null) {
            plugins = new ArrayList<>();
        }
        if (!PluginCache.isEnabled(appName, namiPlugin.name())) {
            return;
        }
        plugins.add(namiPlugin);
        // order by the plugin's order
        plugins.sort(Comparator.comparing(NamiPlugin::order));
    }

    @Override
    public Integer order() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        if (pos == plugins.size()) {
            return exchange.getResponse().setComplete();
        }
        return pluginChain.plugins.get(pos++).execute(exchange, pluginChain);
    }

    public String getAppName() {
        return appName;
    }
}

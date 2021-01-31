package org.nami.plugin;


import org.nami.config.ServerConfigProperties;

/**
 * AbstractShipPlugin
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public abstract class AbstractNamiPlugin implements NamiPlugin {

    protected ServerConfigProperties properties;

    public AbstractNamiPlugin(ServerConfigProperties properties) {
        this.properties = properties;
    }
}

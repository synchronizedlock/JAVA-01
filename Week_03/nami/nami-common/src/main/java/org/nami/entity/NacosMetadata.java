package org.nami.entity;

/**
 * NacosMetadata
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class NacosMetadata {

    private String appName;
    private String version;
    private String plugins;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }
}

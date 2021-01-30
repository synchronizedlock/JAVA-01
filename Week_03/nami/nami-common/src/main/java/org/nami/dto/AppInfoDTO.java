package org.nami.dto;

import org.nami.entity.ServiceInstance;

import java.util.List;

/**
 * AppInfoDTO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class AppInfoDTO {

    private Integer appId;
    private String appName;
    private Byte enabled;

    private List<ServiceInstance> instances;

    private List<String> enabledPlugins;

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<ServiceInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<ServiceInstance> instances) {
        this.instances = instances;
    }

    public List<String> getEnabledPlugins() {
        return enabledPlugins;
    }

    public void setEnabledPlugins(List<String> enabledPlugins) {
        this.enabledPlugins = enabledPlugins;
    }
}

package org.nami.cache;


import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PluginCache
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class PluginCache {

    /**
     * key: appName value: enabled plugins name
     */
    private static final Map<String, List<String>> PLUGIN_MAP = new ConcurrentHashMap<>();

    public static boolean isEnabled(String appName, String pluginName) {
        if (PLUGIN_MAP.containsKey(appName)) {
            return PLUGIN_MAP.get(appName).contains(pluginName);
        }
        return false;
    }


    public static void add(String appName, List<String> pluginNames) {
        PLUGIN_MAP.put(appName, pluginNames);
    }

    public static void removeExpired(List<String> appNames) {
        List<String> expiredKeys = Lists.newLinkedList();
        PLUGIN_MAP.keySet().forEach(k -> {
            if (!appNames.contains(k)) {
                expiredKeys.add(k);
            }
        });
        expiredKeys.forEach(PLUGIN_MAP::remove);
    }
}

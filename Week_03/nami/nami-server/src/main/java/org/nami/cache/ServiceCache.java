package org.nami.cache;

import com.google.common.collect.Lists;
import org.nami.entity.ServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServiceCache
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class ServiceCache {

    /**
     * key: serviceName
     */
    private static final Map<String, List<ServiceInstance>> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * get all instances by serviceName
     */
    public static List<ServiceInstance> getAllInstances(String serviceName) {
        return SERVICE_MAP.get(serviceName);
    }

    /**
     * add service to cache
     */
    public static void add(String serviceName, List<ServiceInstance> list) {
        SERVICE_MAP.put(serviceName, list);
    }

    /**
     * remove the expired service
     */
    public static void removeExpired(List<String> onlineServices) {
        List<String> expiredKeys = Lists.newLinkedList();
        SERVICE_MAP.keySet().forEach(k -> {
            if (!onlineServices.contains(k)) {
                expiredKeys.add(k);
            }
        });
        expiredKeys.forEach(SERVICE_MAP::remove);
    }
}

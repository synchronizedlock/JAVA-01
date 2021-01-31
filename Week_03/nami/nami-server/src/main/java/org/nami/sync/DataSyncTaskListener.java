package org.nami.sync;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.nami.cache.PluginCache;
import org.nami.cache.ServiceCache;
import org.nami.config.ServerConfigProperties;
import org.nami.constants.NacosConstants;
import org.nami.entity.ServiceInstance;
import org.nami.enums.NamiPluginEnum;
import org.nami.utils.NamiThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * DataSyncTaskListener
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Configuration
public class DataSyncTaskListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final ScheduledThreadPoolExecutor SCHEDULED_POOL = new ScheduledThreadPoolExecutor(1,
            new NamiThreadFactory("service-sync", true).create());

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private ServerConfigProperties properties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        SCHEDULED_POOL.scheduleWithFixedDelay(new DataSyncTask(namingService)
                , 0L, properties.getCacheRefreshInterval(), TimeUnit.SECONDS);
        WebsocketSyncCacheServer websocketSyncCacheServer = new WebsocketSyncCacheServer(properties.getWebSocketPort());
        websocketSyncCacheServer.start();
    }


    class DataSyncTask implements Runnable {

        private final NamingService namingService;

        public DataSyncTask(NamingService namingService) {
            this.namingService = namingService;
        }

        @Override
        public void run() {
            try {
                // get all app names
                ListView<String> services = namingService.getServicesOfServer(1, Integer.MAX_VALUE, NacosConstants.APP_GROUP_NAME);
                if (CollectionUtils.isEmpty(services.getData())) {
                    return;
                }
                List<String> appNames = services.getData();
                List<String> onlineAppNames = new ArrayList<>();
                // get all instances
                for (String appName : appNames) {
                    List<Instance> instanceList = namingService.getAllInstances(appName, NacosConstants.APP_GROUP_NAME);
                    if (CollectionUtils.isEmpty(instanceList)) {
                        continue;
                    }
                    ServiceCache.add(appName, buildServiceInstances(instanceList));
                    List<String> pluginNames = getEnabledPlugins(instanceList);
                    PluginCache.add(appName, pluginNames);
                    onlineAppNames.add(appName);
                }
                ServiceCache.removeExpired(onlineAppNames);
                PluginCache.removeExpired(onlineAppNames);

            } catch (NacosException e) {
                e.printStackTrace();
            }
        }

        private List<String> getEnabledPlugins(List<Instance> instanceList) {
            Instance instance = instanceList.get(0);
            Map<String, String> metadata = instance.getMetadata();
            // plugins: DynamicRoute,Auth
            String plugins = metadata.getOrDefault("plugins", NamiPluginEnum.DYNAMIC_ROUTE.getName());
            return Arrays.stream(plugins.split(",")).collect(Collectors.toList());
        }

        private List<ServiceInstance> buildServiceInstances(List<Instance> instanceList) {
            List<ServiceInstance> list = new LinkedList<>();
            instanceList.forEach(instance -> {
                Map<String, String> metadata = instance.getMetadata();
                ServiceInstance serviceInstance = new ServiceInstance();
                serviceInstance.setAppName(metadata.get("appName"));
                serviceInstance.setIp(instance.getIp());
                serviceInstance.setPort(instance.getPort());
                serviceInstance.setVersion(metadata.get("version"));
                serviceInstance.setWeight((int) instance.getWeight());
                list.add(serviceInstance);
            });
            return list;
        }
    }
}

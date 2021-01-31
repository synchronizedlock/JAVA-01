package org.nami.sync;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.nami.constants.NacosConstants;
import org.nami.dto.AppInfoDTO;
import org.nami.entity.NacosMetadata;
import org.nami.entity.ServiceInstance;
import org.nami.enums.EnabledEnum;
import org.nami.service.AppService;
import org.nami.utils.NamiThreadFactory;
import org.nami.utils.OkhttpTools;
import org.nami.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/30
 */
@Configuration
public class NacosSyncListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosSyncListener.class);

    private static ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(1,
            new NamiThreadFactory("nacos-sync", true).create());

    @NacosInjected
    private NamingService namingService;

    @Value("${nacos.discovery.server-addr}")
    private String baseUrl;

    @Resource
    private AppService appService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        String url = "http://" + baseUrl + NacosConstants.INSTANCE_UPDATE_PATH;
        scheduledPool.scheduleWithFixedDelay(new NacosSyncTask(namingService, url, appService), 0, 30L, TimeUnit.SECONDS);
    }

    class NacosSyncTask implements Runnable {

        private final NamingService namingService;
        private final String url;
        private final AppService appService;
        private final Gson gson = new GsonBuilder().create();

        public NacosSyncTask(NamingService namingService, String url, AppService appService) {
            this.namingService = namingService;
            this.url = url;
            this.appService = appService;
        }

        /**
         * Regular update weight,enabled plugins,enabled status to nacos instance
         */
        @Override
        public void run() {
            try {
                // get all app names
                ListView<String> services = namingService.getServicesOfServer(1, Integer.MAX_VALUE, NacosConstants.APP_GROUP_NAME);
                if (CollectionUtils.isEmpty(services.getData())) {
                    return;
                }
                List<String> appNames = services.getData();
                List<AppInfoDTO> appInfos = appService.getAppInfos(appNames);
                for (AppInfoDTO appInfo : appInfos) {
                    if (CollectionUtils.isEmpty(appInfo.getInstances())) {
                        continue;
                    }
                    for (ServiceInstance instance : appInfo.getInstances()) {
                        Map<String, Object> queryMap = buildQueryMap(appInfo, instance);
                        String resp = OkhttpTools.doPut(url, queryMap, "");
                        LOGGER.debug("response :{}", resp);
                    }
                }

            } catch (Exception e) {
                LOGGER.error("nacos sync task error", e);
            }
        }

        private Map<String, Object> buildQueryMap(AppInfoDTO appInfo, ServiceInstance instance) {
            Map<String, Object> map = new HashMap<>();
            map.put("serviceName", appInfo.getAppName());
            map.put("groupName", NacosConstants.APP_GROUP_NAME);
            map.put("ip", instance.getIp());
            map.put("port", instance.getPort());
            map.put("weight", instance.getWeight().doubleValue());

            NacosMetadata metadata = new NacosMetadata();
            metadata.setAppName(appInfo.getAppName());
            metadata.setVersion(instance.getVersion());
            metadata.setPlugins(String.join(",", appInfo.getEnabledPlugins()));
            map.put("metadata", StringTools.urlEncode(gson.toJson(metadata)));

            map.put("ephemeral", true);
            map.put("enabled", EnabledEnum.ENABLE.getCode().equals(appInfo.getEnabled()));
            return map;
        }
    }
}

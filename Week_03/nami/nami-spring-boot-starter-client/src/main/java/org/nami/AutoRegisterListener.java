package org.nami;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.nami.constants.AdminConstants;
import org.nami.constants.NacosConstants;
import org.nami.dto.RegisterAppDTO;
import org.nami.dto.UnregisterAppDTO;
import org.nami.exception.NamiException;
import org.nami.utils.IpUtils;
import org.nami.utils.OkhttpTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AutoRegisterListener
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class AutoRegisterListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AutoRegisterListener.class);

    private volatile AtomicBoolean registered = new AtomicBoolean(false);

    private final ClientConfigProperties properties;

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private final ExecutorService pool;

    /**
     * url list to ignore
     */
    private static final List<String> IGNORE_URL_LIST = new LinkedList<>();

    static {
        IGNORE_URL_LIST.add("/error");
    }

    public AutoRegisterListener(ClientConfigProperties properties) {
        if (!check(properties)) {
            LOGGER.error("client config port,contextPath,appName adminUrl and version can't be empty!");
            throw new NamiException("client config port,contextPath,appName adminUrl and version can't be empty!");
        }
        this.properties = properties;
        pool = new ThreadPoolExecutor(1, 4, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    /**
     * check the ClientConfigProperties
     */
    private boolean check(ClientConfigProperties properties) {
        if (properties.getPort() == null || properties.getContextPath() == null
                || properties.getVersion() == null || properties.getAppName() == null
                || properties.getAdminUrl() == null) {
            return false;
        }
        return true;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!registered.compareAndSet(false, true)) {
            return;
        }
        doRegister();
        registerShutDownHook();
    }

    /**
     * send unregister request to admin when jvm shutdown
     */
    private void registerShutDownHook() {
        final String url = "http://" + properties.getAdminUrl() + AdminConstants.UNREGISTER_PATH;

        final UnregisterAppDTO unregisterAppDTO = new UnregisterAppDTO();
        unregisterAppDTO.setAppName(properties.getAppName());
        unregisterAppDTO.setVersion(properties.getVersion());
        unregisterAppDTO.setIp(IpUtils.getLocalIpAddress());
        unregisterAppDTO.setPort(properties.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            OkhttpTools.doPost(url, unregisterAppDTO);
            LOGGER.info("[{}:{}] unregister from ship-admin success!", unregisterAppDTO.getAppName(), unregisterAppDTO.getVersion());
        }));
    }

    /**
     * register all interface info to register center
     */
    private void doRegister() {
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("version", properties.getVersion());
        metadataMap.put("appName", properties.getAppName());

        Instance instance = new Instance();
        instance.setIp(IpUtils.getLocalIpAddress());
        instance.setPort(properties.getPort());
        instance.setEphemeral(true);
        instance.setMetadata(metadataMap);

        try {
            namingService.registerInstance(properties.getAppName(), NacosConstants.APP_GROUP_NAME, instance);
        } catch (NacosException e) {
            LOGGER.error("register to nacos fail", e);
            throw new NamiException(e.getErrCode(), e.getErrMsg());
        }
        LOGGER.info("register interface info to nacos success!");
        // send register request to ship-admin
        String url = "http://" + properties.getAdminUrl() + AdminConstants.REGISTER_PATH;
        RegisterAppDTO registerAppDTO = buildRegisterAppDTO(instance);
        OkhttpTools.doPost(url, registerAppDTO);
        LOGGER.info("register to ship-admin success!");
    }

    private RegisterAppDTO buildRegisterAppDTO(Instance instance) {
        RegisterAppDTO registerAppDTO = new RegisterAppDTO();
        registerAppDTO.setAppName(properties.getAppName());
        registerAppDTO.setContextPath(properties.getContextPath());
        registerAppDTO.setIp(instance.getIp());
        registerAppDTO.setPort(instance.getPort());
        registerAppDTO.setVersion(properties.getVersion());
        return registerAppDTO;
    }
}

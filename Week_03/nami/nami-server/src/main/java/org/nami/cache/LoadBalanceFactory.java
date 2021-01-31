package org.nami.cache;

import org.nami.annotation.EnableLoadBalance;
import org.nami.exception.NamiException;
import org.nami.spi.LoadBalance;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LoadBalanceFactory
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public final class LoadBalanceFactory {

    /**
     * key: appName:version
     */
    private static final Map<String, LoadBalance> LOAD_BALANCE_MAP = new ConcurrentHashMap<>();

    private LoadBalanceFactory(){
        throw new IllegalStateException("Error creating instance of utility class.");
    }

    public static LoadBalance getInstance(final String name, String appName, String version) {
        String key = appName + ":" + version;
        return LOAD_BALANCE_MAP.computeIfAbsent(key, (k) -> getLoadBalance(name));
    }

    /**
     * use spi to match load balance algorithm by server config
     */
    private static LoadBalance getLoadBalance(String name) {
        ServiceLoader<LoadBalance> loader = ServiceLoader.load(LoadBalance.class);
        Iterator<LoadBalance> iterator = loader.iterator();
        while (iterator.hasNext()) {
            LoadBalance loadBalance = iterator.next();
            EnableLoadBalance ano = loadBalance.getClass().getAnnotation(EnableLoadBalance.class);
            Assert.notNull(ano, "load balance name can not be empty!");
            if (name.equals(ano.value())) {
                return loadBalance;
            }
        }
        throw new NamiException("invalid load balance config");
    }
}

package org.easley.dynamic.utils;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

/**
 * 基于ThreadLocal实现的切换数据源工具类
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
public final class DynamicDataSourceContextHolder {

    /**
     * ThreadLocal里存数据源名称，比如使用主库时，String就是master
     */
    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = new NamedThreadLocal<>("dynamic-datasource");

    /**
     * 主库数据源名称
     */
    public static final String DB_MASTER = "master";

    /**
     * 从库数据源名称
     */
    public static final String DB_SLAVE = "slave";

    private DynamicDataSourceContextHolder() {
        throw new IllegalStateException("Can not get instance of utility class.");
    }

    /**
     * 获取当前使用的数据源名称
     */
    public static String getLookupKey() {
        String lookupKey = LOOKUP_KEY_HOLDER.get();
        if (StringUtils.isEmpty(lookupKey)) {
            // 没有设置当前使用哪个库时，默认选择master，避免写从库
            lookupKey = DB_MASTER;
        }
        return lookupKey;
    }

    /**
     * 设置当前使用的数据源名称
     */
    public static void setLookupKey(String lookupKey) {
        LOOKUP_KEY_HOLDER.set(lookupKey);
    }

    /**
     * 清空当前使用的数据源名称
     */
    public static void clear() {
        LOOKUP_KEY_HOLDER.remove();
    }
}

package org.easley.dynamic.ds;

import org.easley.dynamic.utils.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 主库
     */
    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    /**
     * 从库
     */
    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;

    @Override
    public void afterPropertiesSet() {
        // 大部分系统读多，默认拿从库
        // 写的时候在Mybatis Interceptor中会根据 MappedStatement判断是读还是写，写就切主库
        setDefaultTargetDataSource(slaveDataSource);

        // 把主从注册到数据源map里
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slave", slaveDataSource);
        setTargetDataSources(dataSourceMap);

        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // 从contextHolder里取当前应该使用的数据源名称
        return DynamicDataSourceContextHolder.getLookupKey();
    }
}

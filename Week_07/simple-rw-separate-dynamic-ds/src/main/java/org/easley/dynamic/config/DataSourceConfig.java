package org.easley.dynamic.config;

import com.zaxxer.hikari.HikariDataSource;
import org.easley.dynamic.ds.DynamicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
@Configuration
public class DataSourceConfig {

    @Value("${dynamic.datasource.master.url}")
    private String masterUrl;
    @Value("${dynamic.datasource.master.username}")
    private String masterUsername;
    @Value("${dynamic.datasource.master.password}")
    private String masterPwd;

    @Value("${dynamic.datasource.slave.url}")
    private String slaveUrl;
    @Value("${dynamic.datasource.slave.username}")
    private String slaveUsername;
    @Value("${dynamic.datasource.slave.password}")
    private String slavePwd;

    @Bean
    @Primary
    public AbstractRoutingDataSource dynamicDataSource() {
        return new DynamicDataSource();
    }

    @Bean
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterUrl);
        dataSource.setUsername(masterUsername);
        dataSource.setPassword(masterPwd);
        return dataSource;
    }

    @Bean
    public DataSource slaveDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveUrl);
        dataSource.setUsername(slaveUsername);
        dataSource.setPassword(slavePwd);
        return dataSource;
    }
}

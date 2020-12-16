package com.example.demo.apollo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * DataSourceConfiguration
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/9/21
 */
//@Configuration
@Slf4j
public class DataSourceConfiguration {

    private static final String DATASOURCE_KEY = "db.druid";

    @Autowired
    private ApplicationContext context;

    @ApolloConfig("datasource")
    private Config config;

    @Bean("dataSource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(Collections.singletonMap(DATASOURCE_KEY, dataSource()));
        return dynamicDataSource;
    }

    @ApolloConfigChangeListener(value = "datasource", interestedKeyPrefixes = {"spring.datasource.druid", "spring.redis", "spring.rabbitmq"})
    public void configChange(ConfigChangeEvent configChangeEvent) {
        configChangeEvent.changedKeys().forEach(key -> {
            DynamicDataSource dynamicDataSource = context.getBean(DynamicDataSource.class);
            dynamicDataSource.setTargetDataSources(Collections.singletonMap(DATASOURCE_KEY, dataSource()));
            dynamicDataSource.afterPropertiesSet();
            log.info("Apollo动态切换数据源为：{}", config.getProperty("spring.datasource.druid.url", ""));
        });
    }

    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(config.getProperty("spring.datasource.druid.url", ""));
        druidDataSource.setUsername(config.getProperty("spring.datasource.druid.username", ""));
        druidDataSource.setPassword(config.getProperty("spring.datasource.druid.password", ""));
        return druidDataSource;
    }

    class DynamicDataSource extends AbstractRoutingDataSource {
        @Override
        protected Object determineCurrentLookupKey() {
            return DATASOURCE_KEY;
        }
    }
}
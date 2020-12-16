package com.example.demo.apollo.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * LoggerConfiguration
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/9/24
 */
@Configuration
@Slf4j
public class LoggerConfiguration {

    private static final String LOGGING_LEVEL = "logging.level";

    @Autowired
    private LoggingSystem loggingSystem;

    @ApolloConfig
    private Config config;

    @ApolloConfigChangeListener
    private void configChange(ConfigChangeEvent configChangeEvent) {
        refreshLoggingLevels(configChangeEvent.changedKeys());
    }

    @PostConstruct
    private void refreshLoggingLevels() {
        refreshLoggingLevels(config.getPropertyNames());
    }

    private void refreshLoggingLevels(Set<String> keys) {
        keys.stream().forEach(key -> {
            if (key.startsWith(LOGGING_LEVEL)) {
                String level = config.getProperty(key, "info");
                LogLevel logLevel = LogLevel.valueOf(level.toUpperCase());
                loggingSystem.setLogLevel(key.replace(LOGGING_LEVEL, ""), logLevel);
                String msg = "Apollo动态日志级别为：" + level;
                if ("trace".equals(level)) {
                    log.trace(msg);
                }
                if ("debug".equals(level)) {
                    log.debug(msg);
                }
                if ("info".equals(level)) {
                    log.info(msg);
                }
                if ("warn".equals(level)) {
                    log.warn(msg);
                }
                if ("error".equals(level)) {
                    log.error(msg);
                }
            }
        });
    }

}

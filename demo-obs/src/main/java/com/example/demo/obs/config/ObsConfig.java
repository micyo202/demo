package com.example.demo.obs.config;

import com.obs.services.ObsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ObsConfig
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/2/5
 */
@ConditionalOnProperty(prefix = "obs", name = "end-point")
@Configuration
@Slf4j
public class ObsConfig {

    @Value("${obs.end-point:}")
    private String endPoint;

    @Value("${obs.access-key:}")
    private String accessKey;

    @Value("${obs.secret-key:}")
    private String secretKey;

    @Bean
    public ObsClient obsClient() {
        log.warn("加载 OBS");
        return new ObsClient(accessKey, secretKey, endPoint);
    }

}

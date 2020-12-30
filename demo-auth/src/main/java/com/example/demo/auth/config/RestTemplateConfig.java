package com.example.demo.auth.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/12/23
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 设置请求连接超时时间（毫秒）
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 30 * 1000;

    /**
     * 设置连接超时时间（毫秒）
     */
    private static final int CONNECT_TIMEOUT = 90 * 1000;

    /**
     * 设置读取超时时间（毫秒）
     */
    private static final int READ_TIMEOUT = 90 * 1000;

    @Bean
    public RestTemplate restTemplate() {

        /**
         * 使用 HttpClient 请求
         */
        HttpClient httpClient = HttpClientBuilder
                .create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }

}

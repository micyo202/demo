package com.example.demo.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * AckConfirmCallback
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/16
 */
public interface AckConfirmCallback {

    Logger log = LoggerFactory.getLogger(AckConfirmCallback.class);

    void test();

    default void init() {
        log.info("init");
    }

}

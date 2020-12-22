package com.example.demo.rocketmq.controller;

import com.example.demo.rocketmq.producer.RocketMQProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RocketmqController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/11/17
 */
@RestController
public class RocketmqController {

    @Autowired
    RocketMQProducer rocketMQProducer;

    @RequestMapping("/callback")
    public String callback(String text) {
        if (StringUtils.isBlank(text)) {
            text = "Hello";
        }
        rocketMQProducer.sendMsg(text);
        return "SUCCESS";
    }

}
package com.example.demo.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * RocketMQMsgListener
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/11/18
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${spring.application.name}", topic = "demo-rocketmq-topic", selectorExpression = "*")
public class RocketMQMsgListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("接收到消息：{}", msg);
    }

}

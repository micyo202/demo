package com.example.demo.rabbitmq.receiver;

import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * AmqpReceiver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/11/19
 */
//@Component
//@RabbitListener(queues = AmqpConstant.QUEUE_NAME)
public class AmqpReceiver {

    /**
     * 注：消息发送什么对象，这里就接收什么对象
     */
    //@RabbitHandler
    public void receive(@Payload Map<String, Object> map) {
        System.out.println("AmqpReceiver消费者收到消息：" + map);
    }

}

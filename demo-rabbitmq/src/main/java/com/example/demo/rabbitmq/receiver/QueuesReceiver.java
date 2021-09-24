package com.example.demo.rabbitmq.receiver;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import com.example.demo.rabbitmq.entity.RabbitEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * QueuesReceiver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Component
public class QueuesReceiver {

    @RabbitListener(queues = RabbitConstant.QUEUES)
    public void queues_1(RabbitEntity rabbitEntity) {
        System.out.println("Queues_1 接收到消息：" + rabbitEntity);
    }

    @RabbitListener(queues = RabbitConstant.QUEUES)
    public void queues_2(RabbitEntity rabbitEntity) {
        System.out.println("Queues_2 接收到消息：" + rabbitEntity);
    }

}

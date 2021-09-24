package com.example.demo.rabbitmq.receiver;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import com.example.demo.rabbitmq.entity.RabbitEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * TopicReceiver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Component
public class TopicReceiver {

    @RabbitListener(queues = RabbitConstant.TOPIC_QUEUE_A)
    public void queues_a(RabbitEntity rabbitEntity) {
        System.out.println("Topic_Queue_A 接收到消息：" + rabbitEntity);
    }

    @RabbitListener(queues = RabbitConstant.TOPIC_QUEUE_B)
    public void queues_b(RabbitEntity rabbitEntity) {
        System.out.println("Topic_Queue_B 接收到消息：" + rabbitEntity);
    }
}

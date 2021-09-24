package com.example.demo.rabbitmq.receiver;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import com.example.demo.rabbitmq.entity.RabbitEntity;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * QueueReceiver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Component
public class QueueReceiver {

    @RabbitListener(queues = RabbitConstant.QUEUE)
    @RabbitHandler
    public void queue(RabbitEntity rabbitEntity) {
        System.out.println("Queue接收到消息：" + rabbitEntity);
    }

}

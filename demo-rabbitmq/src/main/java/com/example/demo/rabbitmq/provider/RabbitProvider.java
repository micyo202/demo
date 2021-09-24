package com.example.demo.rabbitmq.provider;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import com.example.demo.rabbitmq.entity.RabbitEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitProvider
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Service
public class RabbitProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Queue 消息无序，消费完成队列依然存在
     */
    public void sendQueue() {
        for (int i = 0; i < 10; i++) {
            int id = i + 1;
            RabbitEntity rabbitEntity = new RabbitEntity();
            rabbitEntity.setId(id);
            rabbitEntity.setName("名称_" + id);

            // 无序发送
            rabbitTemplate.convertAndSend(RabbitConstant.QUEUE, rabbitEntity);
            // 有序发送
            //rabbitTemplate.convertSendAndReceive(RabbitConstant.QUEUE, rabbitEntity);
        }
    }

    /**
     * Queues 多个消费者
     */
    public void sendQueues() {
        for (int i = 0; i < 10; i++) {
            int id = i + 1;
            RabbitEntity rabbitEntity = new RabbitEntity();
            rabbitEntity.setId(id);
            rabbitEntity.setName("名称_" + id);

            // 无序发送
            rabbitTemplate.convertAndSend(RabbitConstant.QUEUES, rabbitEntity);
            // 有序发送
            //rabbitTemplate.convertSendAndReceive(RabbitConstant.QUEUES, rabbitEntity);
        }
    }

    public void sendFanout() {
        for (int i = 0; i < 10; i++) {
            int id = i + 1;
            RabbitEntity rabbitEntity = new RabbitEntity();
            rabbitEntity.setId(id);
            rabbitEntity.setName("名称_" + id);

            // 无序发送
            rabbitTemplate.convertAndSend(RabbitConstant.FANOUT_EXCHANGE, "", rabbitEntity);
            // 有序发送
            //rabbitTemplate.convertSendAndReceive(RabbitConstant.FANOUT_EXCHANGE, "", rabbitEntity);
        }
    }

    public void sendDirect() {
        for (int i = 0; i < 10; i++) {
            int id = i + 1;
            RabbitEntity rabbitEntity = new RabbitEntity();
            rabbitEntity.setId(id);
            rabbitEntity.setName("名称_" + id);
            if (i % 2 == 0) {
                rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.DIRECT_ROUTING_KEY_A, rabbitEntity);
            } else {
                rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.DIRECT_ROUTING_KEY_B, rabbitEntity);
            }
        }
    }

    public void sendTopic() {
        for (int i = 0; i < 10; i++) {
            int id = i + 1;
            if (i % 2 == 0) {
                rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE, "demo.topic.routing.key.x", new RabbitEntity().setId(id).setName("* 一个词路由键_" + id));
            } else {
                rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE, "demo.topic.routing.key.x.y", new RabbitEntity().setId(id).setName("# 多个词路由键_" + id));
            }
        }
    }

    public void sendConfirm() {
        rabbitTemplate.convertAndSend(
                RabbitConstant.CONFIRM_EXCHANGE,
                RabbitConstant.CONFIRM_ROUTING_KEY,
                new RabbitEntity().setId(9900).setName("名称_9900"),
                new CorrelationData(""+System.currentTimeMillis())
        );
        rabbitTemplate.setConfirmCallback(confirmCallback);
    }

    private RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        if (ack) {
            System.out.println("消息发送确认成功，消息ID：" + correlationData.getId());
        } else {
            System.out.println("消息发送确认失败，消息ID：" + correlationData.getId() + "，失败原因：" + cause);
        }
    };

}

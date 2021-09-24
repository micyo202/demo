package com.example.demo.rabbitmq.receiver;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import com.example.demo.rabbitmq.entity.RabbitEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * FanoutReceiver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Component
public class FanoutReceiver {

    @RabbitListener(queues = RabbitConstant.FANOUT_QUEUE_A)
    public void queues_a_1(RabbitEntity rabbitEntity) {
        System.out.println("Fanout_Queue_A_1 接收到消息：" + rabbitEntity);
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_QUEUE_A)
    public void queues_a_2(RabbitEntity rabbitEntity) {
        System.out.println("Fanout_Queue_A_2 接收到消息：" + rabbitEntity);
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_QUEUE_B)
    public void queues_b_1(RabbitEntity rabbitEntity) {
        System.out.println("Fanout_Queue_B_1 接收到消息：" + rabbitEntity);
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_QUEUE_B)
    public void queues_b_2(RabbitEntity rabbitEntity) {
        System.out.println("Fanout_Queue_B_2 接收到消息：" + rabbitEntity);
    }
}

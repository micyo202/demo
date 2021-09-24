package com.example.demo.rabbitmq.config;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueuesRabbitConfig
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Configuration
public class QueuesRabbitConfig {

    @Bean
    public Queue queues() {
        return new Queue(RabbitConstant.QUEUES);
    }

}

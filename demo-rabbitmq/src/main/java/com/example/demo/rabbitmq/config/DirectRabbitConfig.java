package com.example.demo.rabbitmq.config;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DirectRabbitConfig
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue directQueueA() {
        return new Queue(RabbitConstant.DIRECT_QUEUE_A);
    }

    @Bean
    public Queue directQueueB() {
        return new Queue(RabbitConstant.DIRECT_QUEUE_B);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstant.DIRECT_EXCHANGE);
    }

    @Bean
    public Binding bindingDirectA() {
        return BindingBuilder
                .bind(directQueueA())
                .to(directExchange())
                .with(RabbitConstant.DIRECT_ROUTING_KEY_A);
    }

    @Bean
    public Binding bindingDirectB() {
        return BindingBuilder
                .bind(directQueueB())
                .to(directExchange())
                .with(RabbitConstant.DIRECT_ROUTING_KEY_B);
    }
}

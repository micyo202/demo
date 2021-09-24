package com.example.demo.rabbitmq.config;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
public class ConfirmRabbitConfig {

    @Bean
    public Queue confirmQueue() {
        return new Queue(RabbitConstant.CONFIRM_QUEUE);
    }

    @Bean
    public TopicExchange confirmExchange() {
        return new TopicExchange(RabbitConstant.CONFIRM_EXCHANGE);
    }

    @Bean
    public Binding bindingConfirm() {
        return BindingBuilder
                .bind(confirmQueue())
                .to(confirmExchange())
                .with(RabbitConstant.CONFIRM_ROUTING_KEY);
    }

}

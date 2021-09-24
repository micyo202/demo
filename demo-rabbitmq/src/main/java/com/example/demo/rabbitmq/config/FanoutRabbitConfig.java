package com.example.demo.rabbitmq.config;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FanoutRabbitConfig
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue fanoutQueueA() {
        return new Queue(RabbitConstant.FANOUT_QUEUE_A);
    }

    @Bean
    public Queue fanoutQueueB() {
        return new Queue(RabbitConstant.FANOUT_QUEUE_B);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConstant.FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bindingFanoutA() {
        return BindingBuilder
                .bind(fanoutQueueA())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutB() {
        return BindingBuilder
                .bind(fanoutQueueB())
                .to(fanoutExchange());
    }

}

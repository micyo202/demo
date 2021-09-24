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
public class TopicRabbitConfig {

    @Bean
    public Queue topicQueueA() {
        return new Queue(RabbitConstant.TOPIC_QUEUE_A);
    }

    @Bean
    public Queue topicQueueB() {
        return new Queue(RabbitConstant.TOPIC_QUEUE_B);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstant.TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bindingTopicA() {
        return BindingBuilder
                .bind(topicQueueA())
                .to(topicExchange())
                .with(RabbitConstant.TOPIC_ROUTING_KEY_A);
    }

    @Bean
    public Binding bindingTopicB() {
        return BindingBuilder
                .bind(topicQueueB())
                .to(topicExchange())
                .with(RabbitConstant.TOPIC_ROUTING_KEY_B);
    }
}

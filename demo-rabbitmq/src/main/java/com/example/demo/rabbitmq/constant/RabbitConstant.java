package com.example.demo.rabbitmq.constant;

/**
 * AmqpConstant
 * AMQP 常量
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/5/29
 */
public interface RabbitConstant {

    /**
     * 消息无序，消费完成后，队列未被删除
     */
    String QUEUE = "demo.queue";

    /**
     * 一个生产者、多个消费者
     */
    String QUEUES = "demo.queues";


    String FANOUT_QUEUE_A = "demo.fanout.queue.a";
    String FANOUT_QUEUE_B = "demo.fanout.queue.b";
    String FANOUT_EXCHANGE = "demo.fanout.exchange";


    String DIRECT_QUEUE_A = "demo.direct.queue.a";
    String DIRECT_QUEUE_B = "demo.direct.queue.b";
    String DIRECT_EXCHANGE = "demo.direct.exchange";
    String DIRECT_ROUTING_KEY_A = "demo.direct.routing.key.a";
    String DIRECT_ROUTING_KEY_B = "demo.direct.routing.key.b";


    String TOPIC_QUEUE_A = "demo.topic.queue.a";
    String TOPIC_QUEUE_B = "demo.topic.queue.b";
    String TOPIC_EXCHANGE = "demo.topic.exchange";
    String TOPIC_ROUTING_KEY_A = "demo.topic.routing.key.*";    // 一个
    String TOPIC_ROUTING_KEY_B = "demo.topic.routing.key.#";    // 多个


    String CONFIRM_QUEUE = "demo.confirm.queue";
    String CONFIRM_EXCHANGE = "demo.confirm.exchange";
    String CONFIRM_ROUTING_KEY = "demo.confirm.routing.key";


    String RABBIT_QUEUE = "demo.rabbit.queue";
    String RABBIT_EXCHANGE = "demo.rabbit.exchange";
    String RABBIT_ROUTING_KEY = "demo.rabbit.routing.key";
}
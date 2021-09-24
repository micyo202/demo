package com.example.demo.rabbitmq.config;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitConfig
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/11/19
 */
@Configuration
@Slf4j
public class RabbitConfig {

    /**
     * 队列起名
     */
    @Bean
    public Queue rabbitQueue() {
        // durable:是否持久化,默认false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:默认false，是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // return new Queue(QUEUE_NAME, true, true, false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(RabbitConstant.RABBIT_QUEUE, true);
    }

    /**
     * 交换机起名
     */
    @Bean
    public TopicExchange rabbitExchange() {
        return new TopicExchange(RabbitConstant.RABBIT_EXCHANGE, true, false);
    }

    /**
     * 绑定 将队列和交换机绑定, 并设置用于匹配键
     */
    @Bean
    public Binding bindingQueueExchange() {
        return BindingBuilder
                .bind(rabbitQueue())
                .to(rabbitExchange())
                .with(RabbitConstant.RABBIT_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback:     " + "相关数据：" + correlationData);
            log.info("ConfirmCallback:     " + "确认情况：" + ack);
            log.info("ConfirmCallback:     " + "原因：" + cause);
            if (ack) {
                log.info("ConfirmCallback:     消息发送成功");
            } else {
                log.error("ConfirmCallback:     消息发送失败\n原因：" + cause + "\n内容：" + correlationData);
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("ReturnCallback:     Exchange 到 Queue 失败，回调该方法");
            log.error("ReturnCallback:     " + "消息：" + message);
            log.error("ReturnCallback:     " + "回应码：" + replyCode);
            log.error("ReturnCallback:     " + "回应信息：" + replyText);
            log.error("ReturnCallback:     " + "交换机：" + exchange);
            log.error("ReturnCallback:     " + "路由键：" + routingKey);
        });

        return rabbitTemplate;
    }

}

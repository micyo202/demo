package com.example.demo.rabbitmq.controller;

import com.example.demo.rabbitmq.AckConfirmCallback;
import com.example.demo.rabbitmq.provider.RabbitProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RabbitMQController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/11/19
 */
@RestController
public class RabbitMQController {

    @Autowired
    private RabbitProvider rabbitProvider;

    @GetMapping("/init")
    public String init() throws JsonProcessingException {

        /*
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1001);
        map.put("name", "张三");

        CorrelationData correlationData = new CorrelationData();
        ObjectMapper objectMapper = new ObjectMapper();

        Message message = MessageBuilder.withBody(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map).getBytes()).build();
        correlationData.setId(map.get("id").toString());
        correlationData.setReturnedMessage(message);

        //rabbitTemplate.convertAndSend("demo.topic.exchange", "demo.topic.routing", map, correlationData);

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(AmqpConstant.EXCHANGE_NAME);
        rabbitTemplate.setRoutingKey(AmqpConstant.ROUTING_KEY);

        Message msg = MessageBuilder.withBody(objectMapper.writeValueAsString(map).getBytes()).build();
        rabbitTemplate.convertAndSend(msg);
        */

        //rabbitProvider.sendQueue();

        rabbitProvider.sendQueues();

        return null;
    }

    @GetMapping("/queue")
    public String queue() {
        rabbitProvider.sendQueue();
        return null;
    }

    @GetMapping("/queues")
    public String queues() {
        rabbitProvider.sendQueues();
        return null;
    }

    @GetMapping("/fanout")
    public String fanout() {
        rabbitProvider.sendFanout();
        return null;
    }

    @GetMapping("/direct")
    public String direct() {
        rabbitProvider.sendDirect();
        return null;
    }

    @GetMapping("/topic")
    public String topic() {
        rabbitProvider.sendTopic();
        return null;
    }

    @GetMapping("/confirm")
    public String comfirm() {
        rabbitProvider.sendConfirm();
        return null;
    }

    @Autowired
    @Qualifier
    private AckConfirmCallback ackConfirmCallback;

    @GetMapping("/ack")
    public String ack(){
        ackConfirmCallback.init();
        ackConfirmCallback.test();
        return null;
    }

}

package com.example.demo.rabbitmq.receiver;

import com.example.demo.rabbitmq.constant.RabbitConstant;
import com.example.demo.rabbitmq.util.ByteUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * TopicReceiver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Component
public class ConfirmReceiver {

    @RabbitListener(queues = RabbitConstant.CONFIRM_QUEUE)
    public void queue(Message message, Channel channel) {
        try {
            //int a = 1 / 0;
            System.out.println("Confirm_Queue 正常接到消息：" + ByteUtil.bytesToObject(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {

            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                System.out.println("消息消费失败，返回到队列中");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}

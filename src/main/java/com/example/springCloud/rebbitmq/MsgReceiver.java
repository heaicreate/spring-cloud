package com.example.springCloud.rebbitmq;

import com.example.springCloud.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * direct 消费者
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {
    @RabbitHandler
    public void process(String content, Message message, Channel channel) {
        System.out.println("接收处理队列A当中的消息： " + content);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


package com.example.springCloud.rebbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
/**
 * 广播交换机队列B--消费者
 */
@Component
@RabbitListener(queues = "fanoutQueueB")
@Slf4j
public class FanoutExchangeConsumerB {
    @Autowired
    private RabbitTemplate rabbitmqTemplate;


    /**
     * 消费消息
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void receiveMessage(Map msg, Message message, Channel channel) {
        log.info("FanoutExchangeA消费者接收到的消息 :" + msg.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

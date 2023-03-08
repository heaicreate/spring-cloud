package com.example.springCloud.rebbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 延时队列消费者--延时插件
 */
@Slf4j
@Component
public class MsgComsumerService {

    // 监听消费延时消息
    @RabbitListener(queues = {"delay_queue"})
    @RabbitHandler
    public void process(String content, Message message, Channel channel) throws IOException {
        try {
            log.info("延迟队列的内容[{}]", content);
            // 消息的可定确认，第二个参数如果为 true 将一次性确认所有小于 deliveryTag的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("超时信息处理完毕");
        } catch (Exception e) {
            log.error("处理失败:{}", e.getMessage());
            // 直接拒绝消费该消息，后面的参数一定要是false，否则会重新进入业务队列，不会进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
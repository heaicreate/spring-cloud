package com.example.springCloud.rebbitmq;

import com.example.springCloud.config.RabbitConfig;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 延时队列生产者
 */
@Service
public class MsgProductionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 发送延时信息
    public void sendTimeoutMsg(String content, String routingKey, int delay) {
        // 通过广播模式发布延时消息，会广播至每个绑定此交换机的队列，这里的路由键没有实质性作用
        rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE_NAME, routingKey, content, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            // 毫秒为单位，指定此消息的延时时长
            message.getMessageProperties().setDelay(delay * 1000);
            return message;
        });
    }

}

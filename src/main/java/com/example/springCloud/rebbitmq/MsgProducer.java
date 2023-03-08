package com.example.springCloud.rebbitmq;

import com.example.springCloud.config.RabbitConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * direct 交换机生产者
 */
@Component
public class MsgProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTING_KEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_DIRECT_A, RabbitConfig.ROUTING_KEY_A, content, correlationId);
    }
}


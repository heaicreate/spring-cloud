package com.example.springCloud.rebbitmq;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 队列交换机--生产者
 */
@Component
@Slf4j
public class FanoutExchangeProduce {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private static final String fanoutExchangeName = "fanoutExchange";


    /**
     * 发送消息
     */
    public void sendMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "hello!";
        String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        log.info("发送的内容 : " + map.toString());
        rabbitTemplate.convertAndSend(fanoutExchangeName, null, map);
    }

}

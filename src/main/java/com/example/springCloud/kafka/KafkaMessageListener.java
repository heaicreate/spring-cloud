package com.example.springCloud.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaMessageListener {
    // 通过注解注入所在的消费组和药监听的Topic
    @KafkaListener(topics = "test3", groupId = "1")
    public void event(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        kafkaMessage.ifPresent(t -> {
            log.info("消费kafka中的数据:{}", t);
        });
    }


}



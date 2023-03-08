package com.example.springCloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMq 配置
 */
@Configuration
@Slf4j
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;
    // 定义一个或多个交换机
    public static final String EXCHANGE_DIRECT_A = "my-direct-exchange_A";

    public static final String QUEUE_A = "QUEUE_A";

    public static final String ROUTING_KEY_A = "spring-boot-routingKey_A";
    private static final String fanoutQueueNameA = "fanoutQueueA";

    private static final String fanoutQueueNameB = "fanoutQueueB";

    private static final String fanoutExchangeName = "fanoutExchange";
    // 延时交换机
    public static final String DELAY_EXCHANGE_NAME = "delay_exchange";

    // 延时队列名称
    public static final String DELAY_QUEUE_NAME = "delay_queue";
    public static final String DELAY_QUEUE_NAME1 = "delay_queue1";

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     **/
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_DIRECT_A);
    }

    /**
     * 获取队列A
     *
     * @return
     */
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }

    // 一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTING_KEY_A);
    }

    // 创建连接工厂,获取MQ的连接
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    // 创建rabbitTemplate
    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        /*消息发送到Exchange的回调，无论成功与否*/
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback：" + "相关数据：" + correlationData);
            log.info("ConfirmCallback：" + "确认情况：" + ack);
            log.info("ConfirmCallback：" + "原因：" + cause);
        });
        return rabbitTemplate;
    }

    /**
     * 队列 起名：fanout
     */
    @Bean
    public Queue fanoutQueueA() {
        return new Queue(fanoutQueueNameA);
    }

    @Bean
    public Queue fanoutQueueB() {
        return new Queue(fanoutQueueNameB);
    }

    /**
     * Fanout交换机 起名：fanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName);
    }


    /**
     * 绑定  将队列和交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingFanoutA() {
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange());
    }


    /**
     * 绑定  将队列和交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingFanoutB() {
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange());
    }

    // ------------------------延时队列------------------------
    // 延时队列
    @Bean
    public Queue delayPayQueue() {
        return new Queue(RabbitConfig.DELAY_QUEUE_NAME, true);
    }

    @Bean
    public Queue delayPayQueue1() {
        return new Queue(RabbitConfig.DELAY_QUEUE_NAME1, true);
    }

    // 延时交换机
    @Bean
    public FanoutExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        FanoutExchange fanoutExchange = new FanoutExchange(RabbitConfig.DELAY_EXCHANGE_NAME, true, false, args);
        fanoutExchange.setDelayed(true);
        return fanoutExchange;
    }

    // 绑定延时队列与延时交换机
    @Bean
    public Binding delayPayBind() {
        return BindingBuilder.bind(delayPayQueue()).to(delayExchange());
    }

    @Bean
    public Binding delayPayBind1() {
        return BindingBuilder.bind(delayPayQueue1()).to(delayExchange());
    }


    // 定义消息转换器
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}


package com.example.springCloud.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value(value = "${spring.redis.host:}")
    private String redisHost;
    @Value(value = "${spring.redis.port:}")
    private String redisPort;
    @Value(value = "${spring.redis.db:}")
    private String db;


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
//		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort).setDatabase(Integer.valueOf(db));
        return Redisson.create(config);
    }

}

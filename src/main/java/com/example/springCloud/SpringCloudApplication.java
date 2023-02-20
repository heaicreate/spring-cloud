package com.example.springCloud;

import com.example.springCloud.config.LoadBalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@LoadBalancerClients(defaultConfiguration = {LoadBalancerConfig.class})
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }
//
//    //使用 RestTemplate客户端进行请求
//    @Bean
//    @LoadBalanced //启用负载均衡
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        RestTemplate restTemplate = builder.build();
//        return restTemplate;
//    }

}

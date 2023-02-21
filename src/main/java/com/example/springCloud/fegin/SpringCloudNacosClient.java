package com.example.springCloud.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "springtest1") // nacos 服务 id
public interface SpringCloudNacosClient {
    @GetMapping(path = "test")
    String test();

}

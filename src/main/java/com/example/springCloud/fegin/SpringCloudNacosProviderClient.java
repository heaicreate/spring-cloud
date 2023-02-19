package com.example.springCloud.fegin;

import com.example.springCloud.model.Response;
import com.example.springCloud.po.UserPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "springtest") // nacos 服务 id
public interface SpringCloudNacosProviderClient {
    @GetMapping(path = "test")
    String test();

    @GetMapping(path = "test1")
    Response<UserPo> test1();
}

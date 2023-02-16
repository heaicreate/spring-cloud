package com.example.springCloud.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.example.springCloud.util.RedisUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    RedissonClient redissonClient;
    @NacosValue(value = "${test.t1:}", autoRefreshed = true)
    private String t1;

    @GetMapping("/test")
    public String test() {
        System.out.println(t1);
        //https://www.cnblogs.com/jelly12345/p/14699492.html
        //https://cloud.tencent.com/developer/article/1730297
        RLock lock = redissonClient.getLock("anyLock");
        lock.lock();
        lock.unlock();
        redisUtils.setStr("test", "test1", 1000l);
        return "66";
    }
}

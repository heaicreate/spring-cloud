package com.example.springCloud.controller;

import com.example.springCloud.util.RedisUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/test")
    public String test() {
        //https://www.cnblogs.com/jelly12345/p/14699492.html
        //https://cloud.tencent.com/developer/article/1730297
        RLock lock = redissonClient.getLock("anyLock");
        lock.lock();
        redisUtils.setStr("test", "test1", 1000l);
        return "66";
    }
}

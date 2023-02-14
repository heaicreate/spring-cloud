package com.example.springCloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springCloud.po.UserPo;
import com.example.springCloud.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/test")
    public String test() {
        redisUtils.setStr("test", "test1", 1000l);
        return "66";
    }
}

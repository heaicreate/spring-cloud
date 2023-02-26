package com.example.springCloud.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.example.springCloud.fegin.SpringCloudNacosProviderClient;
import com.example.springCloud.model.Response;
import com.example.springCloud.po.UserPo;
import com.example.springCloud.support.ResultWrap;
import com.example.springCloud.util.RedisUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class TestController {
    @Autowired
    private RedisUtils redisUtils;
    @Resource
    SpringCloudNacosProviderClient springCloudNacosProviderClient;
    @Resource
    RedissonClient redissonClient;
    @Value(value = "${test.t1:}")
    private String t1;
    @Value(value = "${test2:}")
    private String t2;

    @GetMapping("/test")
    public String test() {
        StopWatch stopWatch=new StopWatch("test");
        stopWatch.start("开始");
        System.out.println(t1);
        System.out.println(t2);
        stopWatch.stop();
        stopWatch.start("远端请求");
        Response<UserPo> test1 = springCloudNacosProviderClient.test1();
        stopWatch.stop();
        //https://www.cnblogs.com/jelly12345/p/14699492.html
        //https://cloud.tencent.com/developer/article/1730297
        stopWatch.start("开始加锁");
        RLock lock = redissonClient.getLock("anyLock");
        lock.lock();
        lock.unlock();
        stopWatch.stop();
        stopWatch.start("redis 设置信息");
        redisUtils.setStr("test", "test1", 1000l);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return test1.getData().getAge()+"";
    }

    @GetMapping("/test1")
    public Response<UserPo> getTest() {
        UserPo userPo = new UserPo();
        userPo.setName("1232312312");
        userPo.setAge(18);
        return ResultWrap.ok(userPo);
    }


}

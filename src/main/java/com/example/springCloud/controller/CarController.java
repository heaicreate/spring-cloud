package com.example.springCloud.controller;

import com.example.springCloud.annotation.MyLog;
import com.example.springCloud.annotation.RequestUser;
import com.example.springCloud.entity.Car;
import com.example.springCloud.model.Response;
import com.example.springCloud.po.LoginInfo;
import com.example.springCloud.service.CarService;
import com.example.springCloud.support.ResultWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "car")
@RefreshScope
@Slf4j
public class CarController {
    @Resource
    CarService carService;

    @GetMapping(value = "/list")
    @MyLog(name = "测试",requestUrl = "test")
    public Response<List<Car>> getTest(@RequestUser LoginInfo loginInfo) {
        log.info("请求");
        List<Car> cars=carService.list();
        log.info("完成");
        return ResultWrap.ok(cars);
    }
}

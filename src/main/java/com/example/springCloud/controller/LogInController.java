package com.example.springCloud.controller;

import com.example.springCloud.model.Response;
import com.example.springCloud.po.req.LogInReq;
import com.example.springCloud.support.ResultWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "auth")
@RefreshScope
@Slf4j
public class LogInController {

    @GetMapping(value = "/login")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @PostMapping(value = "/index")
    public Response login(@RequestBody LogInReq logInReq) {
        return ResultWrap.ok(true);
    }

    @GetMapping(value = "/webSocket")
    public ModelAndView webSocket(@RequestParam("userName")String userName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName", userName);
        modelAndView.setViewName("websocket");
        return modelAndView;
    }
}

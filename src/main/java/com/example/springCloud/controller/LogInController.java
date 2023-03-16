package com.example.springCloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "auth")
@RefreshScope
@Slf4j
public class LogInController {

    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam("userName") String userName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName",userName);
        modelAndView.setViewName("websocket");
        return modelAndView;
    }
}

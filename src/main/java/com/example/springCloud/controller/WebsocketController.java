package com.example.springCloud.controller;

import com.example.springCloud.exception.ErrorCode;
import com.example.springCloud.model.Response;
import com.example.springCloud.po.req.MsgReq;
import com.example.springCloud.service.webSocket.WebSocketServer;
import com.example.springCloud.support.ResultWrap;
import com.example.springCloud.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebsocketController {

    @Resource
    RedisUtils redisUtils;

    //推送数据接口
    @PostMapping("/socket/push/{userName}")
    public Response pushToWeb(@PathVariable String userName, String message, HttpServletRequest httpServletRequest) {
        String token =httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return ResultWrap.error(ErrorCode.ACTIVITY_ACTION_ERROR);
        }
        if (!redisUtils.isExists(token)) {
            return ResultWrap.error(ErrorCode.ACTIVITY_ACTION_ERROR.getStatus(), ErrorCode.ACTIVITY_ACTION_ERROR.getCode(), "重新登陆");
        }
        String userNameLogin = redisUtils.getStr(token);
        message = userNameLogin + ":" + message;
        Map<String, Object> result = new HashMap<>();
        try {
            WebSocketServer.sendInfo(message, userName);
            result.put("userName", userName);
            result.put("msg", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultWrap.ok(result);
    }


    //推送数据接口
    @PostMapping("/msg/push")
    public Response pushMes(@RequestBody MsgReq message, HttpServletRequest httpServletRequest) {
        String token =httpServletRequest.getHeader("token");

//        if (StringUtils.isBlank(token)) {
//            return ResultWrap.error(ErrorCode.ACTIVITY_ACTION_ERROR);
//        }
//        if (!redisUtils.isExists(token)) {
//            return ResultWrap.error(ErrorCode.ACTIVITY_ACTION_ERROR.getStatus(), ErrorCode.ACTIVITY_ACTION_ERROR.getCode(), "重新登陆");
//        }
//        String userNameLogin = redisUtils.getStr(token);
        Map<String, Object> result = new HashMap<>();
        try {

            WebSocketServer.sendInfo(message.getUserName(), message.getUserName());
            result.put("userName", message.getUserName());
            result.put("msg", message.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultWrap.ok(result);
    }
}


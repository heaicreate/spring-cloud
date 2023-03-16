package com.example.springCloud.controller;

import com.example.springCloud.exception.ErrorCode;
import com.example.springCloud.model.Response;
import com.example.springCloud.service.webSocket.WebSocketServer;
import com.example.springCloud.support.ResultWrap;
import com.example.springCloud.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/socket/push/{cid}")
    public Response pushToWeb(@PathVariable String cid, String message, HttpServletRequest httpServletRequest) {
        String token =httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return ResultWrap.error(ErrorCode.ACTIVITY_ACTION_ERROR);
        }
        if (!redisUtils.isExists(token)) {
            return ResultWrap.error(ErrorCode.ACTIVITY_ACTION_ERROR.getStatus(), ErrorCode.ACTIVITY_ACTION_ERROR.getCode(), "重新登陆");
        }
        String userName = redisUtils.getStr(token);
        message = userName + ":" + message;
        Map<String, Object> result = new HashMap<>();
        try {
            WebSocketServer.sendInfo(message, cid);
            result.put("code", cid);
            result.put("msg", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultWrap.ok(result);
    }
}


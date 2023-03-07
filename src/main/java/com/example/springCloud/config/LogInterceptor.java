package com.example.springCloud.config;

import com.example.springCloud.util.TraceIdUtil;
import jodd.util.StringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Configuration
public class LogInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(TraceIdUtil.TRACE_ID);
        if (StringUtil.isEmpty(traceId))
        {
            TraceIdUtil.setTraceId(TraceIdUtil.generateTraceId());
        }
        else
        {
            TraceIdUtil.setTraceId(traceId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //调用结束后删除
        TraceIdUtil.remove();
    }
}


package com.example.springCloud.annotation;

import com.example.springCloud.po.LoginInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType().isAssignableFrom(LoginInfo.class)
                && methodParameter.hasParameterAnnotation(RequestUser.class)) {
            return true;
        }
        return false;
    }

    /**
     * 获取请求头中的Authorization，处理之后返回用户信息
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        String authorization = nativeWebRequest.getHeader("Authorization");
        LoginInfo loginInfo = new LoginInfo();
        if (StringUtils.isEmpty(authorization)) {
            loginInfo.setUserName("test");
            return loginInfo;
        }
        return loginInfo;
    }
}
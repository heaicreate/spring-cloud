package com.example.springCloud.support;

import com.alibaba.nacos.shaded.com.google.common.collect.ImmutableMap;
import com.example.springCloud.exception.ErrorCode;
import com.example.springCloud.exception.ServiceException;
import com.example.springCloud.model.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;


public class ResultWrap {
    public static final int SUCCESS_STATUS = 200;
    public static final int SUCCESS_CODE = 0;

    public static final String DATA_ITEMS = "items";

    private ResultWrap() {
    }

    public static Response ok() {
        return new Response(SUCCESS_STATUS, SUCCESS_CODE, StringUtils.EMPTY, "");
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(SUCCESS_STATUS, SUCCESS_CODE, StringUtils.EMPTY, data);
    }

    public static <T> Response<List<T>> ok(List<T> data) {
        Map<String, List<T>> items = ImmutableMap.of(DATA_ITEMS, data);
        return new Response(SUCCESS_STATUS, SUCCESS_CODE, StringUtils.EMPTY, items);
    }
    public static <T> Response<T> ok(String name, T data) {
        Map<String, T> item= ImmutableMap.of(name, data);
        return new Response(SUCCESS_STATUS, SUCCESS_CODE, StringUtils.EMPTY, item);
    }
    public static <T> Response<List<T>> ok(String name, List<T> data) {
        Map<String, List<T>> items = ImmutableMap.of(name, data);
        return new Response(SUCCESS_STATUS, SUCCESS_CODE, StringUtils.EMPTY, items);
    }
    public static <T> Response<List<T>> okCanCelItems(String name, List<T> data) {
        return new Response(SUCCESS_STATUS, SUCCESS_CODE, StringUtils.EMPTY,data);
    }
    public static Response error(ServiceException ex) {
        return new Response<>(ex.getStatus(), ex.getCode(), ex.getMessage());
    }

    public static Response error(ErrorCode errorCode) {
        return new Response<>(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }

    public static Response error(int status, int code, String msg) {
        return new Response(status, code, msg);
    }
}

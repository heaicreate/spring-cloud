package com.example.springCloud.designpattern.filter;

import java.util.List;

/**
 * 过滤器模式 +自定义注解 实现
 */
public interface FilterMode {

    List<String> filter(List<String> result);
}

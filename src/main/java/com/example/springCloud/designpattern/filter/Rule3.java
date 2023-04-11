package com.example.springCloud.designpattern.filter;

import com.example.springCloud.annotation.FilterModeMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@FilterModeMethod(group = "FilterMode",order = 3)
@Component
public class Rule3 implements FilterMode{
    @Override
    public List<String> filter(List<String> result) {
        System.out.println(1231313123);
        return null;
    }
}

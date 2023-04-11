package com.example.springCloud.designpattern.filter;

import com.example.springCloud.annotation.FilterModeMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@FilterModeMethod(group = "FilterMode",order = 1)
@Component
public class Rule1 implements FilterMode{
    @Override
    public List<String> filter(List<String> result) {
        return result.subList(0,1);
    }
}

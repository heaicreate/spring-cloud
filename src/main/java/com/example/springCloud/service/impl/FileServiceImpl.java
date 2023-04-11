package com.example.springCloud.service.impl;

import com.example.springCloud.annotation.FilterModeMethod;
import com.example.springCloud.designpattern.filter.FilterMode;
import com.example.springCloud.designpattern.filter.ListMode;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl {
    @Resource
    private ApplicationContext applicationContext;
    List<FilterMode> filterModes = new ArrayList<>();

    public void test() {
        List<String> stringList = Arrays.asList("12", "15", "66");
        for (FilterMode filterMode : filterModes) {
            stringList = filterMode.filter(stringList);
        }

    }

    @PostConstruct
    public void initMethod() {
        Map<String, Object> beansWithAnnotationMap = this.applicationContext.getBeansWithAnnotation(FilterModeMethod.class);
        List<ListMode> objects = new ArrayList<>();
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            FilterModeMethod reqMap = AnnotationUtils.findAnnotation(entry.getValue().getClass(), FilterModeMethod.class);
            if (Objects.isNull(reqMap)) {
                continue;
            }
            if (reqMap.group().equals("FilterMode")) {
                ListMode listMode = new ListMode();
                listMode.setOrder(reqMap.order());
                listMode.setObject(entry.getValue());
                objects.add(listMode);
            }
        }
        objects = objects.stream().sorted(Comparator.comparing(ListMode::getOrder)).collect(Collectors.toList());
        //由于顺序问题 所以验证同一个组下不能具备同样的order
        List<Integer> distincts = objects.stream().map(ListMode::getOrder).distinct().collect(Collectors.toList());
        if (distincts.size()!=objects.size()){
            throw new RuntimeException("FilterMode分组下有重复排序");
        }
        objects.forEach(s -> {
            filterModes.add((FilterMode) s.getObject());
        });

    }
}

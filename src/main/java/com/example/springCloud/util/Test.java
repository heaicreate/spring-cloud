package com.example.springCloud.util;

import com.alibaba.nacos.client.utils.ParamUtil;

public class Test {
    public static void main(String[] args) {
//        String projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);


        int longingTaskCount = (int)Math.ceil((double)2000 / ParamUtil.getPerTaskConfigSize());
        System.out.println(longingTaskCount);
    }
}

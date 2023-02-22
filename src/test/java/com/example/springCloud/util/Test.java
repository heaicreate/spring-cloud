package com.example.springCloud.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Test {
    @org.junit.Test
    public void test() {
//        int num = ThreadLocalRandom.current().nextInt(1);
//        System.out.println(num);
//
//
//        long time = TimeUnit.SECONDS.toMillis(5L);
//        System.out.println(time);

        try {
            System.out.println(URLEncoder.encode("nacos!sfsj", "utf-8"));
            System.out.println(URLEncoder.encode("测试", "utf-8"));
            System.out.println(URLDecoder.decode(URLEncoder.encode("nacos!sfsj", "utf-8"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

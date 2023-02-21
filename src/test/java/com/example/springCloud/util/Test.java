package com.example.springCloud.util;

import java.util.concurrent.ThreadLocalRandom;

public class Test {
    @org.junit.Test
    public void test() {
        int num = ThreadLocalRandom.current().nextInt(1);
        System.out.println(num);
    }
}

package com.example.springCloud.util;

import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.alibaba.nacos.shaded.com.google.common.hash.HashCode;
import com.alibaba.nacos.shaded.com.google.common.hash.HashFunction;
import com.alibaba.nacos.shaded.com.google.common.hash.Hashing;
import org.junit.Test;

import java.util.Map;

public class MurmurhashTest {
    @Test
    public void murmurhash_01() {
        long max = 100000000;
        Map<Long, Long> map = Maps.newHashMap();
        int mo = 10;
        for (long i = 0; i < mo; i++) {
            map.put(i, 0L);
        }

        Map<Long, Long> map2 = Maps.newHashMap();
        for (long i = 0; i < mo; i++) {
            map2.put(i, 0L);
        }

        for (long i1 = 0; i1 < max; i1 += 2) {
            HashFunction hashFunction = Hashing.murmur3_128();
            HashCode code = hashFunction.hashLong(i1);
            long v = code.asLong();
            if (v < 0) {
                v = 0 - v;
            }
            Long count = map.get(v % mo);
            count++;
            map.put(v % mo, count);

            long v2 = Long.valueOf(i1).hashCode();
            if (v2 < 0) {
                v2 = 0 - v2;
            }
            count = map2.get(v2 % 10);
            count++;
            map2.put(v2 % 10, count);
        }
        System.out.println("murmurhash:" + map);
        System.out.println("Long hashCode:" + map2);

    }
}

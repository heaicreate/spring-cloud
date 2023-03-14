package com.example.springCloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SlidingWindowCounter {

    /**
     * redis key前缀
     */
    private static final String SLIDING_WINDOW = "sliding_window_";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 判断key的value中的有效访问次数是否超过最大限定值maxCount
     * 判断与数量增长分开处理
     *
     * @param key            redis key
     * @param windowInSecond 窗口间隔，秒
     * @param maxCount       最大计数
     * @return 是 or 否
     */
    public boolean overMaxCount(String key, int windowInSecond, long maxCount) {
        key = SLIDING_WINDOW + key;
        log.info("redis key = {}", key);
        // 当前时间
        long currentMs = System.currentTimeMillis();
        // 窗口开始时间
        long windowStartMs = currentMs - windowInSecond * 1000L;
        // 按score统计key的value中的有效数量
        Long count = redisTemplate.opsForZSet().count(key, windowStartMs, currentMs);
        // 已访问次数 >= 最大可访问值
        return count >= maxCount;
    }

    /**
     * 判断key的value中的有效访问次数是否超过最大限定值maxCount，若没超过，调用increment方法，将窗口内的访问数加一
     * 判断与数量增长同步处理
     *
     * @param key            redis key
     * @param windowInSecond 窗口间隔，秒
     * @param maxCount       最大计数
     * @return 可访问 or 不可访问
     */
    public boolean canAccess(String key, int windowInSecond, long maxCount) {
        key = SLIDING_WINDOW + key;
        log.info("redis key = {}", key);
        //按key统计集合中的有效数量
        Long count = redisTemplate.opsForZSet().zCard(key);
        if (count < maxCount) {
            increment(key, windowInSecond);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 滑动窗口计数增长
     *
     * @param key            redis key
     * @param windowInSecond 窗口间隔，秒
     */
    public void increment(String key, Integer windowInSecond) {
        // 当前时间
        long currentMs = System.currentTimeMillis();
        // 窗口开始时间
        long windowStartMs = currentMs - windowInSecond * 1000;
        // 单例模式(提升性能)
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // 清除窗口过期成员
        zSetOperations.removeRangeByScore(key, 0, windowStartMs);
        // 添加当前时间 value=当前时间戳 score=当前时间戳
        zSetOperations.add(key, String.valueOf(currentMs), currentMs);
        // 设置key过期时间
        redisTemplate.expire(key, windowInSecond, TimeUnit.SECONDS);
    }
}
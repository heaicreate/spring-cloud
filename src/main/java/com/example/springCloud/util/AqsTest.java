package com.example.springCloud.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class AqsTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                log.info("我抢到锁了 哈哈我是 ：{}",Thread.currentThread().getName());
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };
        Thread threadA = new Thread(runnable, "Thread A");
        Thread threadB = new Thread(runnable, "Thread B");

        threadA.start();
        threadB.start();
        log.info("线程A状态:{}",threadA.getState());
        log.info("线程B状态:{},线程A不释放 没办法 我只能死等了 ",threadB.getState());

    }

}

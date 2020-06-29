package com.thread.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ThreadSecurity {

    //状态锁
    private Object lock;
    //条件变量
    private int number = 0;

    @Test
    @Order(1)
    @DisplayName("线程安全")
    public void threadSecurityTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                number++;
                log.info(Thread.currentThread().getName() + "-" + System.nanoTime() + "-" + i + "->" + number);
            }
            countDownLatch.countDown();
        }, "111").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                number--;
                log.info(Thread.currentThread().getName() + "-" + System.nanoTime() + "-" + i + "->" + number);
            }
            countDownLatch.countDown();
        }, "222").start();

        countDownLatch.await();
    }


    @Test
    @Order(2)
    @DisplayName("线程安全2")
    public void runTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                number++;
            }
            System.out.println("final x from 1: " + number);
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                number++;
            }
            System.out.println("final x from 2: " + number);
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
    }

}
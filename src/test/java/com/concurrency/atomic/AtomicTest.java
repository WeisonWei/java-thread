package com.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AtomicTest {
    public static Integer ii = new Integer(0);
    public volatile static Integer iv = new Integer(0);
    public static AtomicLong al = new AtomicLong();
    public static AtomicInteger ai = new AtomicInteger();
    public static AtomicBoolean ab = new AtomicBoolean();

    @Test
    public void volatileIntegerTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", al);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                iv++;
                log.info("=====" + finalI + "=====" + iv);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", iv);
    }

    @Test
    public void integerTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", al);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                ii++;
                log.info("=====" + finalI + "=====" + ii);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", ii);
    }

    /**
     * https://blog.csdn.net/lhn1234321/article/details/82193289
     * https://www.jianshu.com/p/4c4979cc97ca
     *
     * @throws InterruptedException
     */
    @Test
    public void atomicLongTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", al);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                al.incrementAndGet();
                log.info("=====" + finalI + "=====" + al);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", al);
    }

    @Test
    public void atomicIntegerTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", ai);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                ai.incrementAndGet();
                log.info("=====" + finalI + "=====" + ai);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", ai);
    }
}

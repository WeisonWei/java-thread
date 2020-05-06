package com.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AtomicTest {
    public static AtomicLong num = new AtomicLong();

    /**
     * https://blog.csdn.net/lhn1234321/article/details/82193289
     *
     * @throws InterruptedException
     */
    @Test
    public void atomic() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====start=====" + num);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                num.incrementAndGet();
                log.info("=====" + finalI + "=====" + num);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====" + num);
    }
}

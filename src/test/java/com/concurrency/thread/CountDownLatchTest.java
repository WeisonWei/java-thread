package com.concurrency.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CountDownLatchTest {

    @Test
    public void countDownLatchTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(20);
        log.info(Thread.currentThread().getName() + "主线程开始执行==>" + countDownLatch.getCount());
        ExecutorService executorPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 20; i++) {
            executorPool.submit(() -> {
                countDownLatch.countDown();
                log.info(Thread.currentThread().getName() + "====>" + countDownLatch.getCount());
            });
        }

        countDownLatch.await();
        log.info(Thread.currentThread().getName() + "主线程执行结束!==>" + countDownLatch.getCount());
    }

    @Test
    public void noCountDownLatchTest() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "主线程开始执行==>" );
        ExecutorService executorPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 20; i++) {
            executorPool.submit(() -> {
                log.info(Thread.currentThread().getName() + "====>" );
            });
        }
        log.info(Thread.currentThread().getName() + "主线程执行结束!==>" );
    }
}

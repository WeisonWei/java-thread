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
public class SemaphoreTest {

    @Test
    public void semaphoreAddTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        log.info(Thread.currentThread().getName() + "主线程开始执行!");

        ExecutorService executorPool = Executors.newFixedThreadPool(20);

        for (int i = 1; i < 20; i++) {
            executorPool.submit(() -> {
                calculate.add();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }

    @Test
    public void semaphoreSafeAddTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        log.info(Thread.currentThread().getName() + "主线程开始执行!");

        ExecutorService executorPool = Executors.newFixedThreadPool(20);

        for (int i = 1; i < 20; i++) {
            executorPool.submit(() -> {
                calculate.safeAdd();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }
}

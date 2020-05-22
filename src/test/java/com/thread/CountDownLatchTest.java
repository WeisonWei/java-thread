package com.thread;

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

    /**
     * CountDownLatch -- > 计数器闭锁
     * CountDownLatch是通过一个计数器来实现的，计数器的初始化值为线程的数量。
     * 每当一个线程完成了自己的任务后，计数器的值就相应得减1。当计数器到达0时，表示所有的线程都已完成任务，然后在闭锁上等待的线程就可以恢复执行任务。
     * 链接：https://www.jianshu.com/p/4b6fbdf5a08f
     * 链接：https://www.jianshu.com/p/bb5105303d85
     *
     * @throws InterruptedException
     */

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
        log.info(Thread.currentThread().getName() + "主线程开始执行==>");
        ExecutorService executorPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 20; i++) {
            executorPool.submit(() -> {
                log.info(Thread.currentThread().getName() + "====>");
            });
        }
        log.info(Thread.currentThread().getName() + "主线程执行结束!==>");
    }
}

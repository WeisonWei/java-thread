package com.concurrency.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierTest {
    private static CyclicBarrier barrier = new CyclicBarrier(5);
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    private static CyclicBarrier barrierCallback = new CyclicBarrier(5, () -> log.info("callback@@@@@"));

    @Test
    public void cyclicBarrier() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            TimeUnit.SECONDS.sleep(1);
            executor.execute(() -> {
                try {
                    run(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        countDownLatch.await();
        executor.shutdown();
    }

    @Test
    public void cyclicBarrierCallBack() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            TimeUnit.SECONDS.sleep(1);
            executor.execute(() -> {
                try {
                    runCallback(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        countDownLatch.await();
        executor.shutdown();
    }

    public static void run(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        barrier.await();
        countDownLatch.countDown();
        log.info("{} continue", threadNum);
    }

    public static void runCallback(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        barrierCallback.await();
        countDownLatch.countDown();
        log.info("{} continue", threadNum);
    }
}

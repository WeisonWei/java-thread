package com.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierTest {
    private static CyclicBarrier barrier = new CyclicBarrier(5);
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    private static CyclicBarrier barrierCallback = new CyclicBarrier(5, () -> log.info("callback@@@@@"));

    /**
     * 从字面上的意思可以知道，这个类的中文意思是“循环屏障”。大概的意思就是一个可循环利用的屏障。
     * 它的作用就是会让所有线程都等待完成后才会继续下一步行动。
     *
     * https://www.jianshu.com/p/333fd8faa56e
     * @throws InterruptedException
     */
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

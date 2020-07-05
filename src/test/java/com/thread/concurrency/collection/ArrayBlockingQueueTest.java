package com.thread.concurrency.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.System.nanoTime;

@Slf4j
public class ArrayBlockingQueueTest {
    /**
     * 通过　ReentrantLock　Condition.await()实现线程间等待
     */
    @Test
    public void blockingQueueTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(5);

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                put(blockingQueue, i);
                log.info(nanoTime() + "-producer-" + i + "-queue:" + blockingQueue);
            }
            countDownLatch.countDown();
        }, "producer").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Integer take = take(blockingQueue);
                log.info(nanoTime() + "-consumer-" + i + "-take:" + take + "-queue:" + blockingQueue);
            }
            countDownLatch.countDown();
        }, "consumer").start();

        countDownLatch.await();
    }

    private Integer take(BlockingQueue<Integer> blockingQueue) {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void put(BlockingQueue<Integer> blockingQueue, int i) {
        try {
            blockingQueue.put(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleepOneMilliSecond() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

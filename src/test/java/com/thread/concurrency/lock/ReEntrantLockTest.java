package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReEntrantLockTest {

    public void lockTest() {
        ReentrantLock lock = new ReentrantLock();
    }

    @Test
    public void fairLockTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ReentrantLock fairLock = new ReentrantLock(true);
        AtomicInteger index = new AtomicInteger(0);
        new Thread(() -> {

            try {
                fairLock.lock();
                for (int i = 0; i < 10; i++) {
                    log.info("---1--->" + i);
                }
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fairLock.isLocked() && fairLock.isHeldByCurrentThread()) {
                    fairLock.unlock();
                    countDownLatch.countDown();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                fairLock.lock();
                for (int i = 0; i < 10; i++) {
                    log.info("---2--->" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fairLock.isLocked() && fairLock.isHeldByCurrentThread()) {
                    fairLock.unlock();
                    countDownLatch.countDown();
                }
            }
        }).start();
        countDownLatch.await();
    }
}

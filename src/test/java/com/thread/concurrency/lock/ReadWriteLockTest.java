package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ReadWriteLockTest {

    @Test
    public void readWriteLockTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final Lock readLock = lock.readLock();
        final Lock writeLock = lock.writeLock();

        new Thread(() -> {
            try {
                writeLock.lock();
                TimeUnit.SECONDS.sleep(3);
                log.info("--writeLock--");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //不释放则read一直等待
                writeLock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                readLock.lock();
                log.info("--readLock--");
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await();
    }
}

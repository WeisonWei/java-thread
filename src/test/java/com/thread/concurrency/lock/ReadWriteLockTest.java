package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 允许多个线程同时读，但只要有一个线程在写，其他线程就必须等待
 * 只允许一个线程写入（其他线程既不能写入也不能读取）
 * 没有写入时，多个线程允许同时读（提高性能）
 */
@Slf4j
public class ReadWriteLockTest {

    /**
     * 读锁会一直等待写锁
     *
     * @throws InterruptedException
     */
    @Test
    public void writeReadLockTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final Lock readLock = lock.readLock();
        final Lock writeLock = lock.writeLock();

        new Thread(() -> {
            try {
                writeLock.lock();
                log.info("--writeLock-1-lock" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(3);
                log.info("--writeLock-2-sleep" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //不释放则read一直等待
                writeLock.unlock();
                log.info("--writeLock-3-unlock" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                readLock.lock();
                log.info("--readLock-1-lock" + System.currentTimeMillis());
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                log.info("--readLock-2-unlock" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await();
    }


    @Test
    public void readWriteLockTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final Lock readLock = lock.readLock();
        final Lock writeLock = lock.writeLock();

        new Thread(() -> {
            try {
                readLock.lock();
                log.info("--readLock-1-lock" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(3);
                log.info("--readLock-2-sleep" + System.currentTimeMillis());
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                log.info("--readLock-3-unlock" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                writeLock.lock();
                log.info("--writeLock-1-lock" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //不释放则read一直等待
                writeLock.unlock();
                log.info("--writeLock-2-unlock" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
    }

    @Test
    public void readReadLockTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final Lock readLock = lock.readLock();

        new Thread(() -> {
            try {
                readLock.lock();
                log.info("--readLock-11-lock" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(3);
                log.info("--readLock-12-sleep" + System.currentTimeMillis());
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                log.info("--readLock-13-unlock" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                readLock.lock();
                log.info("--readLock-21-lock" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //不释放则read一直等待
                readLock.unlock();
                log.info("--readLock-22-unlock" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
    }
}

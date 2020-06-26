package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;

@Slf4j
public class StampedLockTest {

    /**
     * https://www.liaoxuefeng.com/wiki/1252599548343744/1309138673991714
     */
    @Test
    public void lockTest() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);
        StampedLock stampedLock = new StampedLock();
        AtomicInteger version = new AtomicInteger(0);

        //一直读
        new Thread(() -> {
            //查询完要更新
            long stamp = stampedLock.readLock();
            try {
                TimeUnit.SECONDS.sleep(1);
                AtomicInteger currentVersion = version;
                log.info("readLock->" + currentVersion.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockRead(stamp);
                countDownLatch.countDown();
                log.info("currentVersion-unlock->");
            }
        }).start();


        new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                TimeUnit.SECONDS.sleep(1);
                version.getAndIncrement();
                log.info("-writeLock->" + version.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlock(stamp);
                countDownLatch.countDown();
                log.info("-writeLock-unlock");
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1000000000; i++) {
                log.info("tryOptimisticRead-readLock->" + i);
                long stamp = stampedLock.tryOptimisticRead();
                AtomicInteger currentVersion = version;
                if (!stampedLock.validate(stamp)) {
                    stamp = stampedLock.readLock();
                    try {
                        currentVersion = version;
                        log.info("tryOptimisticRead-readLock->" + i + " " + currentVersion.get());
                    } finally {
                        stampedLock.unlockRead(stamp);
                        log.info("tryOptimisticRead-unlock-> " + i);
                    }
                }
                log.info("tryOptimisticRead->" + currentVersion.get());
            }
            countDownLatch.countDown();
        }).start();

        countDownLatch.await(15, TimeUnit.SECONDS);
    }
}

package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConditionTest {

    @Test
    public void conditionTest() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            try {
                lock.lock();
                log.info("==begin sleep==");
                TimeUnit.SECONDS.sleep(4);
                condition.signal();//condition.signalAll();
                log.info("==after signal==");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                log.info("==begin await==");
                condition.await();
                log.info("==have been single==");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await();

    }
}

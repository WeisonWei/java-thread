package com.thread.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class LongAdderTest {

    @Test
    public void adderTest() throws InterruptedException {

        AtomicLong atomicLong = new AtomicLong();
        atomicLongAddTimes(10, 100);

    }

    private void atomicLongAddTimes(final Integer threadNumber, final Integer times) throws InterruptedException {
        long star = System.currentTimeMillis();
        atomicLong(threadNumber, times);
        long end = System.currentTimeMillis();
        log.info("threadNumber: " + threadNumber + " times: " + times);
    }

    private void longAdderAddTimes(final Integer threadNumber, final Integer times) throws InterruptedException {
        long star = System.currentTimeMillis();
        longAdder(threadNumber, times);
        long end = System.currentTimeMillis();
        log.info(threadNumber + "");
    }

    private void atomicLong(final Integer threadNumber, final Integer times) throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    atomicLong.incrementAndGet();
                }
            });
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }
    }

    private void longAdder(final Integer threadNumber, final Integer times) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    longAdder.add(1);
                }
            });
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }
    }
}

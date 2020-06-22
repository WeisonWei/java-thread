package com.thread.pool;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SingleThreadScheduledPoolTest {
    private static long start = new Date().getTime();

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    /**
     * 当线程2被阻塞时，其它的线程也被阻塞不能运行。
     */
    @Test
    public void singleThreadScheduled() {
        Thread thread1 = new Thread(() -> {
            long end = new Date().getTime();
            System.out.println("time wait:" + (end - start) + ",this is 线程1");
        }, "线程1");

        Thread thread2 = new Thread(() -> {
            long end = new Date().getTime();
            System.out.println("time wait:" + (end - start) + ",this is 线程2");
        }, "线程2");

        Thread thread3 = new Thread(() -> {
            long end = new Date().getTime();
            System.out.println("time wait:" + (end - start) + ",this is 线程3");
        }, "线程3");
        executor.scheduleWithFixedDelay(thread1, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(thread2, 0, 2, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(thread3, 0, 3, TimeUnit.SECONDS);
    }
}

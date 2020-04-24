package com.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class calculate {

    private static Semaphore semaphore = new Semaphore(1);
    private static int NUM = 0;
    private static volatile int NUM_V = 0;

    public static int safeAdd() {
        log.info(Thread.currentThread().getName() + " safeAdd执行!");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NUM++;

        log.info(Thread.currentThread().getName() + "====>" + NUM);
        semaphore.release();
        log.info(Thread.currentThread().getName() + "===release=>" + NUM);
        return NUM;
    }

    public static int add() {
        log.info(Thread.currentThread().getName() + " add执行!");
        NUM++;
        log.info(Thread.currentThread().getName() + "====>" + NUM);
        return NUM;
    }
}

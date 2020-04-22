package com.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class calculate {

    private static Semaphore semaphore = new Semaphore(1);
    private static int num = 0;

    public static int add() {
        log.info(Thread.currentThread().getName() + " add执行!");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num++;
        semaphore.release();
        return num;
    }

    public static int safeAdd() {
        log.info(Thread.currentThread().getName() + " add执行!");
        num++;
        return num;
    }
}

package com.thread.state;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateRunnable implements Runnable {
    private CountDownLatch countDownLatch;
    private String threadName;
    private final Object object = new Object();

    @Override
    public void run() {
        log.info("[ " + threadName + " ]" + "开始执行");
        try {
            log.debug("Thread -run- status:{}", Thread.currentThread().getState());
            TimeUnit.SECONDS.sleep(3);
            printThreadName();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printThreadName() {
        log.debug("==Thread:{}==", Thread.currentThread().getName());
    }
}

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
    private final Object mutex = new Object();
    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        log.debug("{}-[{}]" + " 开始执行", getTime(), getName());
        printThreadName();
        countDownLatch.countDown();
    }

    private long getTime() {
        return System.currentTimeMillis();
    }

    private String getName() {
        return Thread.currentThread().getName();
    }

    public synchronized void printThreadName() {
        try {
            log.debug("{} -*[{}]*- before sleep:{}", getTime(), getName(), getState());
            if (getName().contains("1")) {
                wait();
            }
            TimeUnit.SECONDS.sleep(6);
            log.debug("{}-[{}]==do some thing==", getTime(), getName());
            log.debug("{} -*[{}]*- after sleep:{}", getTime(), getName(), getState());
            if (getName().contains("2")) {
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Thread.State getState() {
        return Thread.currentThread().getState();
    }
}

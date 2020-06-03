package com.commom;


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
public class StateRunnable extends Thread {
    private CountDownLatch countDownLatch;
    private String threadName;

    @Override
    public void run() {
        log.info("[ " + threadName + " ]" + "开始执行");
        try {
            log.debug("Thread -run- status:{}", Thread.currentThread().getState());
            TimeUnit.SECONDS.sleep(10);
            long time = time();
            log.debug("Thread -run- time:{}", time);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized long time() {
        return System.currentTimeMillis();
    }
}
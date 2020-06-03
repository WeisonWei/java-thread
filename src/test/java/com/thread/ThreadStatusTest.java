package com.thread;

import com.thread.state.StateRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ThreadStatusTest {

    private CountDownLatch countDownLatch = new CountDownLatch(3);

    @Test
    @Order(1)
    @DisplayName("Thread线程状态")
    public synchronized void createThread() throws InterruptedException {
        StateRunnable stateRunnable = new StateRunnable(countDownLatch);
        Thread thread = new Thread(stateRunnable);
        log.debug("{}- {}-new- status:{}", getTime(), getName(thread), getState(thread));
        Thread thread1 = new Thread(stateRunnable);
        log.debug("{}- {}-new- status:{}", getTime(), getName(thread1), getState(thread1));
        Thread thread2 = new Thread(stateRunnable);
        log.debug("{}- {}-new- status:{}", getTime(), getName(thread2), getState(thread2));

        thread.start();
        log.debug("{}- {}-star- status:{}", getTime(), getName(thread), getState(thread));
        thread1.start();
        log.debug("{}- {}-star- status:{}", getTime(), getName(thread1), getState(thread1));
        thread2.start();
        log.debug("{}- {}-star- status:{}", getTime(), getName(thread2), getState(thread2));

        TimeUnit.SECONDS.sleep(6);
        thread.interrupt();
        log.debug("{}- {}-interrupt- status:{}", getTime(), getName(thread), getState(thread));
        log.debug("{}- {}-interrupt- status:{}", getTime(), getName(thread1), getState(thread1));
        log.debug("{}- {}-interrupt- status:{}", getTime(), getName(thread2), getState(thread2));

        countDownLatch.await();
        log.debug("{}- {}-await- status:{}", getTime(), getName(thread), getState(thread));
        log.debug("{}- {}-await- status:{}", getTime(), getName(thread1), getState(thread1));
        log.debug("{}- {}-await- status:{}", getTime(), getName(thread2), getState(thread2));
    }

    private long getTime() {
        return System.currentTimeMillis();
    }

    private String getName(Thread thread) {
        return thread.getName();
    }

    private Thread.State getState(Thread thread) {
        return thread.getState();
    }
}
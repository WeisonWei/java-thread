package com.concurrency.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SemaphoreTest {

    /**
     * Semaphore --> 类似阀门的功能
     * Semaphore也叫信号量，在JDK1.5被引入，可以用来控制同时访问特定资源的线程数量，通过协调各个线程，以保证合理的使用资源。
     * Semaphore内部维护了一组虚拟的许可，许可的数量可以通过构造函数的参数指定。
     * 访问特定资源前，必须使用acquire方法获得许可，如果许可数量为0，该线程则一直阻塞，直到有可用许可。
     * 访问资源后，使用release释放许可。
     * Semaphore和ReentrantLock类似，获取许可有公平策略和非公平许可策略，默认情况下使用非公平策略。
     * 链接：https://www.jianshu.com/p/0090341c6b80
     * Semaphore可以用来做流量分流，特别是对公共资源有限的场景，比如数据库连接。
     *
     * @throws InterruptedException
     */

    @Test
    public void semaphoreAddTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        log.info(Thread.currentThread().getName() + "主线程开始执行!");

        ExecutorService executorPool = Executors.newFixedThreadPool(20);

        for (int i = 1; i < 20; i++) {
            executorPool.submit(() -> {
                calculate.add();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }

    @Test
    public void semaphoreSafeAddTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        log.info(Thread.currentThread().getName() + "主线程开始执行!");

        ExecutorService executorPool = Executors.newFixedThreadPool(20);

        for (int i = 1; i < 20; i++) {
            executorPool.submit(() -> {
                calculate.safeAdd();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }
}

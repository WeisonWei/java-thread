package com.thread;

import com.commom.RunnablePractice;
import com.commom.ThreadPractice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.yield;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ThreadTest {

    //状态锁
    private Object lock;
    //条件变量
    private int now, need;

    @Test
    @Order(1)
    @DisplayName("创建Thread线程")
    public void createThread() {
        Thread thread = new ThreadPractice();
        thread.start();
    }

    @Test
    @Order(2)
    @DisplayName("创建Runnable线程")
    public void createRunnableThread() {
        Runnable runnable = () -> log.info("Runnable线程执行中...");
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Test
    @Order(3)
    @DisplayName("线程调度")
    public void ThreadSchedule() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "主线程开始执行!");
        Runnable runnable1 = new RunnablePractice("thread1");
        Runnable runnable2 = new RunnablePractice("thread2");
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }

    @Test
    @Order(4)
    @DisplayName("线程调度Join")
    public void ThreadScheduleJoin() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "主线程开始执行!");
        Runnable runnable1 = new RunnablePractice("thread1");
        Runnable runnable2 = new RunnablePractice("thread2");
        Runnable runnable3 = new RunnablePractice("thread1");
        Runnable runnable4 = new RunnablePractice("thread2");
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);
        thread1.start();
        thread1.join(); //等待join所属线程运行结束后再继续运行
        thread2.start();
        TimeUnit.SECONDS.sleep(10);
        log.info("-----");
        thread3.start();
        thread4.start();
        thread4.join();
        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }

    @Test
    @Order(5)
    @DisplayName("线程调度Join")
    public void ThreadScheduleYield() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "主线程开始执行!");
        Runnable runnable1 = new RunnablePractice("thread1");
        Runnable runnable2 = new RunnablePractice("thread2");
        Runnable runnable3 = new RunnablePractice("thread1");
        Runnable runnable4 = new RunnablePractice("thread2");
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);
        thread1.start();
        yield(); //放弃一下 这个代码执行完以后马上抢
        thread2.start();

        TimeUnit.SECONDS.sleep(10);
        log.info("-----");
        thread3.start();
        thread4.start();
        yield();
        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }

    @Test
    @Order(6)
    @DisplayName("线程调度Join")
    public void ThreadScheduleWait() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "主线程开始执行!");
        Runnable runnable1 = new RunnablePractice("thread1");
        Runnable runnable2 = new RunnablePractice("thread2");
        Runnable runnable3 = new RunnablePractice("thread1");
        Runnable runnable4 = new RunnablePractice("thread2");
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);
        thread1.start();
        thread1.wait(); //放弃一下 这个代码执行完以后马上抢
        thread2.start();
        thread1.notify();
        TimeUnit.SECONDS.sleep(10);
        log.info("-----");
        thread3.start();
        thread4.start();
        yield();
        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getName() + "主线程执行结束!");
    }

    @Test
    @Order(7)
    @DisplayName("线程wait")
    public void produce() {
        //同步
        synchronized (lock) {
            //当前有的不满足需要，进行等待，直到满足条件
            while (now < need) {
                try {
                    //等待阻塞
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我被唤醒了！");
            }
            // 做其他的事情
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
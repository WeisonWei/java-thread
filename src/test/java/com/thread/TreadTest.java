package com.thread;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

public class TreadTest {


    @Test
    @Ignore
    public void threadTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.currentThread().setName("child-111111");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.currentThread().setName("parent-111111");
        TimeUnit.SECONDS.sleep(30);
    }


    @Test
    @Ignore
    public void newCachedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "," + index);
                }
            });
        }
        TimeUnit.HOURS.sleep(1);

    }

    @Test
    @Ignore
    public void newFixedThreadPool() throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + ", " + index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    @Ignore
    public void newScheduledThreadPool() throws InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        System.out.println("before:" + System.currentTimeMillis() / 1000);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟3秒执行的哦 :" + System.currentTimeMillis() / 1000);
            }
        }, 3, TimeUnit.SECONDS);
        System.out.println("after :" + System.currentTimeMillis() / 1000);
        System.out.println("before:" + System.currentTimeMillis() / 1000);

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟1秒之后，3秒执行一次:" + System.currentTimeMillis() / 1000);
            }
        }, 1, 3, TimeUnit.SECONDS);
        System.out.println("after :" + System.currentTimeMillis() / 1000);

        TimeUnit.HOURS.sleep(1);
    }


    @Test
    @Ignore
    public void newSingleThreadExecutor() throws InterruptedException {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "," + index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    @Ignore
    public void countDownLatchTest() throws InterruptedException {
        // 改为3 查看阻塞效果
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread thread1 = new Thread(() -> {
            System.out.println("------1-------");
            long count = countDownLatch.getCount();
            countDownLatch.countDown();
            long count1 = countDownLatch.getCount();
        });

        Thread thread2 = new Thread(() -> {

            System.out.println("------2-------");
            long count = countDownLatch.getCount();
            countDownLatch.countDown();
            long count1 = countDownLatch.getCount();

        });

        thread1.start();
        thread2.start();
        countDownLatch.await();
        long count1 = countDownLatch.getCount();
        System.out.println("---------------------");
    }

    @Test
    @Ignore
    public void cyclicBarrierTest() throws InterruptedException {
        //起跑线
        CyclicBarrier cyclicBarrier1 = new CyclicBarrier(3);
        CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        //放开thread3看效果
        //thread3.start();
        TimeUnit.HOURS.sleep(1);
        System.out.println("---------------------");
    }

    @Test
    @Ignore
    public void semaphoreTest() throws InterruptedException {
        //synchronized像是一个容量为1的Semaphore
        //semaphore1.acquire(); 获取许可
        //semaphore1.release(); 释放
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);
        Thread thread1 = new Thread(() -> {
            try {
                semaphore1.acquire();
                semaphore2.acquire();
                TimeUnit.SECONDS.sleep(5);
                semaphore2.release();
            } catch (InterruptedException e) {

            }
            System.out.println("------1-------");
        });

        Thread thread2 = new Thread(() -> {
            try {
                semaphore1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------2-------");

        });

        Thread thread3 = new Thread(() -> {

            try {
                semaphore2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        TimeUnit.HOURS.sleep(1);
    }
}

package com.thread.apis;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        //1 创建2个线程
        List<Thread> threadList = IntStream.range(1, 3)
                .mapToObj(ThreadJoin::createThread).collect(Collectors.toList());
        //2 启动线程
        threadList.forEach(Thread::start);

        //3 main方法线程调用这两个线程的join方法
        for (Thread thread : threadList) {
            thread.join();
        }
        //4 mian方法线程会等上边两个线程执行完后才进行打印
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "-->" + i);
            sleepForMoment();
        }
    }

    /**
     * 创建线程 并休眠5次 2s
     *
     * @param index
     * @return
     */
    private static Thread createThread(int index) {
        return new Thread(() -> {
            Thread.currentThread().setName("weixx" + index);
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
                sleepForMoment();
            }

        });
    }

    /**
     * 休眠2秒
     */
    private static void sleepForMoment() {
        try {
            TimeUnit.SECONDS.sleep(2l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

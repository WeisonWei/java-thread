package com.thread.apis;

import java.util.concurrent.TimeUnit;

public class ThreadSleep {
    public static void main(String[] args) {
        //案例1 用以说明sleep方法只会导致当前线程进行指定时间的休眠
        //1 启一个线程
        new Thread(() -> {
            //2 记录匿名线程的休眠时间
            long startTime = System.currentTimeMillis();
            //3 休眠2000毫秒
            sleep(2_000l);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Thread -> Total spend %d ms", (endTime - startTime)));
        }).start();
        //4 记录main方法线程的休眠时间
        long startTime = System.currentTimeMillis();
        //5 休眠3000毫秒
        sleep(3_000l);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Main -> Total spend %d ms", (endTime - startTime)));

        //案例2 使用TimeUnit代替Thread.sleep
        new Thread(() -> {
            try {
                //使用Thread.sleep 休眠1秒
                Thread.sleep(1000l);
                //使用TimeUnit纳秒 休眠1微秒
                TimeUnit.NANOSECONDS.sleep(1000l);
                //使用TimeUnit微秒 休眠1毫秒
                TimeUnit.MICROSECONDS.sleep(1000l);
                //使用TimeUnit毫秒 休眠1秒
                TimeUnit.MILLISECONDS.sleep(1000l);
                //使用TimeUnit秒 休眠1分钟
                TimeUnit.SECONDS.sleep(60l);
                //使用TimeUnit分钟 休眠1分钟
                TimeUnit.MINUTES.sleep(1l);
                //使用TimeUnit小时 休眠1天
                TimeUnit.HOURS.sleep(24l);
                //使用TimeUnit天 休眠1天
                TimeUnit.DAYS.sleep(1l);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * 调用Thread的静态方法sleep
     * 使调用的线程休眠相应的毫秒数
     *
     * @param millis
     */
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

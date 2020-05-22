package com.thread.base;

import java.util.concurrent.TimeUnit;

public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(
                () -> {
                    int i = 1;
                    try {
                        while (true) {
                            Thread.currentThread().setName("weixx");
                            //1当前线程sleep 10s
                            TimeUnit.SECONDS.sleep(10l);
                            System.out.println("thread 第[" + i + "]sleep结束~");
                            i++;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("thread 挂了~");
                    }
                }
        );
        //2 设置为守护线程
        thread.setDaemon(true);
        //3 启动线程
        thread.start();
        //4 main方法线程sleep 5s
        TimeUnit.SECONDS.sleep(5l);
        System.out.println("main方法生命周期结束~");
    }
}

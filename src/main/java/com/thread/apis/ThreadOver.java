package com.thread.apis;

import java.util.concurrent.TimeUnit;

public class ThreadOver {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("我正在运行...");
            while (!Thread.currentThread().isInterrupted()) {
                //空循环
            }
            System.out.println("我还在运行...");
        });

        thread.start();
        TimeUnit.SECONDS.sleep(8l);
        System.out.println("线程即将被关闭...");
        thread.interrupt();
    }
}

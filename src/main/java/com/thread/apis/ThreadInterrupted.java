package com.thread.apis;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupted {
    public static void main(String[] args) throws InterruptedException {
        //new一个线程
        Thread thread = new Thread(() -> {
            while (true) {
                //第一次调用 返回true 并檫除interrput flag
                //第二次以后只返回false了
                System.out.println(Thread.interrupted());
            }
        });
        //设置为daemon,让jvm自动退出
        thread.setDaemon(true);
        //启动线程
        thread.start();
        //主线程休眠3s
        TimeUnit.SECONDS.sleep(1l);
        //调用thread的interrupt方法 打断thread,并将interrupt flag置为true
        thread.interrupt();
    }
}

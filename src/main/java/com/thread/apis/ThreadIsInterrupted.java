package com.thread.apis;

import java.util.concurrent.TimeUnit;

public class ThreadIsInterrupted {
    public static void main(String[] args) throws InterruptedException {
        //new一个线程
        Thread thread = new Thread(() -> {
            try {
                //休眠5分钟
                TimeUnit.MINUTES.sleep(5l);
            } catch (InterruptedException e) {
                //被打断时打印
                //可中断方法捕捉到中断信号后，会将其檫除
                System.out.println("fuck! i am be interrupted.");
            }
        });
        //设置为daemon,让jvm自动退出
        thread.setDaemon(true);
        //启动线程
        thread.start();
        //主线程休眠3s
        TimeUnit.SECONDS.sleep(3l);
        //调用thread的interrupt方法 打断thread
        System.out.printf("Thread is interrupted ? %s\n", thread.isInterrupted());
        //调用thread的interrupt方法 打断thread,并将interrupt flag置为true
        thread.interrupt();
        //休眠3秒等待sleep被打断捕获异常后檫除interrupt flag
        TimeUnit.SECONDS.sleep(3l);
        System.out.printf("Thread is interrupted ? %s\n", thread.isInterrupted());
    }
}

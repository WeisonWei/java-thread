package com.thread.apis;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        //new一个线程
        Thread thread = new Thread(() -> {
            //控循环
            while (true) {
                //不进行sleep 因为可中断方法捕捉到中断信号后，会将其檫除
            }
        });

        //启动线程
        thread.start();
        //主线程休眠2ms
        TimeUnit.MILLISECONDS.sleep(2l);
        System.out.printf("Thread is interrupted ? %s\n", thread.isInterrupted());
        //调用thread的interrupt方法 打断thread,并将interrupt flag置为true
        thread.interrupt();
        System.out.printf("Thread is interrupted ? %s\n", thread.isInterrupted());
    }
}

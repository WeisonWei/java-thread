package com.thread.base;

import java.util.concurrent.TimeUnit;

/**
 * Thread的命名
 */
public class ThreadName {
    public static void main(String[] args) {
        new Thread(() ->
        {
            //为其命名
            Thread.currentThread().setName("weixx");
            try {
                TimeUnit.SECONDS.sleep(100l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

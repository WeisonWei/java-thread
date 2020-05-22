package com.thread.apis;

import java.util.concurrent.TimeUnit;

public class ThreadId {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                System.out.println("Thread's id is " + Thread.currentThread().getId());
                TimeUnit.SECONDS.sleep(3l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

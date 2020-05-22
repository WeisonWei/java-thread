package com.thread.impl;

public class RunnableImpl implements Runnable {
    public void run() {
        Thread.currentThread().setName("Runnable's implemention");
        for (int i = 0; i < 10; i++)
            System.out.println(Thread.currentThread().getName() + "------------");
    }
}

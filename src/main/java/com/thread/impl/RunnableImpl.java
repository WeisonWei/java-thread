package com.thread.impl;

public class RunnableImpl implements Runnable {
    public void run() {
        for (int i = 0; i < 2; i++)
            System.out.println(Thread.currentThread().getName() + "--" + i + "--");
    }
}

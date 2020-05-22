package com.thread.impl;

public class ThreadChild extends Thread {
    @Override
    public void run() {
        Thread.currentThread().setName("Thread's child");
        for (int i = 0; i < 10; i++)
            System.out.println(Thread.currentThread().getName() + "------------");
    }
}

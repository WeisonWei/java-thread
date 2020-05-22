package com.thread.runnable;

public class TenYearsOldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":我在三年级学习,数学好难~");
    }
}

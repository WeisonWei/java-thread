package com.thread.runnable;

public class ThirtyYearsOldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+": 我有了孩子，才知道我的爸妈多么不容易~");
    }
}

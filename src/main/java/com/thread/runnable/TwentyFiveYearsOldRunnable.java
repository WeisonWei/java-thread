package com.thread.runnable;

public class TwentyFiveYearsOldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":告别大学，初入职场处处碰壁~");
    }
}

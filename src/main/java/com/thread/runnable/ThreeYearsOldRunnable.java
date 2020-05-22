package com.thread.runnable;

public class ThreeYearsOldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":叔叔好~我还有很多玩具要玩~");
    }
}

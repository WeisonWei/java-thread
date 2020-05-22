package com.thread.waitnotify;

import java.util.concurrent.TimeUnit;

public class SynchronizedDefect {

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect synchronizedDefect = new SynchronizedDefect();
        Thread thread1 = new Thread(synchronizedDefect::syncMethod, "Thread1");
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(2);

        Thread thread2 = new Thread(synchronizedDefect::syncMethod, "Thread2");
        thread2.start();
        TimeUnit.MILLISECONDS.sleep(2);
        thread2.interrupt();
        System.out.println("Thread2 isInterrupted: "+thread2.isInterrupted());
        System.out.println("Thread2's state: "+thread2.getState());

    }

    public synchronized void syncMethod() {
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

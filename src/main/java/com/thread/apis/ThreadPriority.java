package com.thread.apis;

public class ThreadPriority {
    public static void main(String[] args) {
        Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("-" + Thread.currentThread().getName() + "-");
                }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("-" + Thread.currentThread().getName() + "-");
                }
            }
        });
        thread0.setPriority(2);
        thread1.setPriority(10);
        thread0.start();
        thread1.start();
    }
}

package com.thread.sync;

import java.util.concurrent.TimeUnit;

public class ClassMonitor {
    public static synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + "enter to method1");
        try {
            TimeUnit.SECONDS.sleep(20l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + "enter to method2");
        try {
            TimeUnit.SECONDS.sleep(20l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void method3() {
        synchronized (ClassMonitor.class) {
            System.out.println(Thread.currentThread().getName() + "enter to method3");
            try {
                TimeUnit.SECONDS.sleep(20l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(ClassMonitor::method1).start();
        new Thread(ClassMonitor::method2).start();
        new Thread(ClassMonitor::method3).start();
    }
}

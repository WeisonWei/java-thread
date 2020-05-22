package com.thread.sync;

import java.util.concurrent.TimeUnit;

public class ThisMonitor {
    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + "enter to method1");
        try {
            TimeUnit.SECONDS.sleep(20l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + "enter to method2");
        try {
            TimeUnit.SECONDS.sleep(20l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method3() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "enter to method3");
            try {
                TimeUnit.SECONDS.sleep(20l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThisMonitor thisMonitor = new ThisMonitor();
        new Thread(thisMonitor::method1).start();
        new Thread(thisMonitor::method2).start();
        new Thread(thisMonitor::method3).start();
    }
}

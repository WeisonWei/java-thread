package com.thread.runnable;

import java.util.concurrent.TimeUnit;

public class TicketRunnable implements Runnable {

    private int index = 1;//不用static修改
    private final static int MAX = 10;

    public void run() {
        while (index <= MAX) {
            System.out.println(Thread.currentThread().getName() + "号码是：" + (index++));

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

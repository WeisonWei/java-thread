package com.thread.sync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketWinRunnable implements Runnable {
    private int index = 1;
    private final static int MAX = 500;

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println(System.nanoTime() + "--" + Thread.currentThread().getName() + "的号码是：" + (index++));
        }
    }

    public static void main(String[] args) {
        final TicketWinRunnable ticketWinRunnable = new TicketWinRunnable();
        Thread windoWThread1 = new Thread(ticketWinRunnable, "窗口1");
        Thread windoWThread2 = new Thread(ticketWinRunnable, "窗口2");
        Thread windoWThread3 = new Thread(ticketWinRunnable, "窗口3");
        Thread windoWThread4 = new Thread(ticketWinRunnable, "窗口4");

        windoWThread1.start();
        windoWThread2.start();
        windoWThread3.start();
        windoWThread4.start();
    }
}

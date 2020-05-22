package com.thread.sync;

public class TicketWinSyncRunnable implements Runnable {
    private int index = 1;
    private final static int MAX = 500;
    //定义与synchronized关联的Object
    private final Object MUTEX = new Object();

    @Override
    public void run() {
        //将公共的Runnable 逻辑单元部分加锁
        synchronized (MUTEX) {
            while (index <= MAX) {
                System.out.println(Thread.currentThread().getName() + "的号码是：" + (index++));
            }
        }
    }

    public static void main(String[] args) {
        final TicketWinSyncRunnable ticketWinRunnable = new TicketWinSyncRunnable();
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

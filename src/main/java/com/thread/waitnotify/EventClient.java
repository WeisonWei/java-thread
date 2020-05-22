package com.thread.waitnotify;

import java.util.concurrent.TimeUnit;

/**
 * 事件队列客户端
 */
public class EventClient {
    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();

        /**
         * 不停地往eventQueue中塞event
         */
        new Thread(() -> {
            while (true) {
                eventQueue.offer(new EventQueue.Event());
            }
        }, "Producer").start();


        /**
         * 每5s从eventQueue取出一个Event
         */
        new Thread(() -> {
            while (true) {
                eventQueue.take();

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }, "Consumer").start();

    }
}

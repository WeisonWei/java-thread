package com.thread.waitnotify;

import java.util.LinkedList;

/**
 * 事件队列
 */
public class EventQueue {
    private final int max;

    //内部类 只是用作队列中存储的对象
    static class Event {

    }

    //定义一个事件链表List
    private final LinkedList<Event> eventQueue = new LinkedList<>();
    //最大10个时间
    private final static int DEFAULT_MAX_EVENT = 10;

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    /**
     * 往队列中添加Event
     *
     * @param event
     */
    public void offer(Event event) {
        //同步块
        synchronized (eventQueue) {
            //如果超过最大数量 则使当前线程陷入阻塞
            if (eventQueue.size() >= max) {
                try {
                    console("the queue is full");
                    //调用wait方法使当前线程陷入阻塞 并放置到eventQueue's waitSet
                    eventQueue.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            console("the new event is submmit");
            //未超过最大数量 在添加至最后
            eventQueue.addLast(event);
            //调用notify方法唤醒eventQueue's waitSet中的线程
            eventQueue.notify();
        }
    }

    /**
     * 从队列中取Event
     *
     * @return
     */
    public Event take() {
        synchronized (eventQueue) {
            //当队列中为空时 则将线程至为阻塞状态
            if (eventQueue.isEmpty()) {
                try {
                    console("the queue is empty");
                    //调用wait方法使当前线程陷入阻塞 并放置到eventQueue's waitSet
                    eventQueue.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Event event = eventQueue.removeFirst();
            //调用notify方法唤醒eventQueue's waitSet中的线程
            eventQueue.notify();
            console("the  event " + event + "is handle");
            return event;
        }
    }

    private void console(String str) {
        System.out.printf("%s:%s\n", Thread.currentThread().getName(), str);
    }
}

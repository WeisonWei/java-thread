package com.thread.sync;

import java.util.concurrent.TimeUnit;

public class Mutex {
    //定义与synchronized关联的Object
    private final static Object MUTEX = new Object();
    //将公共逻辑单元部分加锁
    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final Mutex mutex = new Mutex();
        //生产5个线程执行accessResource方法
        for (int i = 0; i < 5; i++) {
            new Thread(mutex::accessResource).start();
        }
    }
}

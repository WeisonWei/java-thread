package com.thread.base;

import java.util.concurrent.TimeUnit;

public class ThreadGroupExcs {
    public static void main(String[] args) {
        //构造时不指定ThreadGroup,其默认的group为父进程main的ThreadGroup
        Thread thread1 = new Thread(ThreadGroupExcs::run, "thread1");
        //构造时指定ThreadGroup,其group为指定的ThreadGroup
        ThreadGroup threadGroup = new ThreadGroup("weixxGroup");
        Thread thread2 = new Thread(threadGroup, ThreadGroupExcs::run, "thread2");

        thread1.start();
        thread2.start();
    }

    private static void run() {
        try {
            TimeUnit.SECONDS.sleep(100l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

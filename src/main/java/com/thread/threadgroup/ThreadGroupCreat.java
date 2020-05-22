package com.thread.threadgroup;

import static java.lang.Thread.currentThread;

public class ThreadGroupCreat {
    public static void main(String[] args) {
        //1 获取当前线程的threadGroup
        ThreadGroup threadGroup = currentThread().getThreadGroup();
        //2 给当前线程定义一个新的threadGroup


    }
}

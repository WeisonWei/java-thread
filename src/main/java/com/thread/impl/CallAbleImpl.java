package com.thread.impl;

import java.util.concurrent.Callable;

public class CallAbleImpl implements Callable {
    private String name;

    public CallAbleImpl(String name) {
        this.name = name;
    }

    public Object call() throws Exception {
        Thread.currentThread().setName(name + "'s implemention");
        for (int i = 0; i < 10; i++)
            System.out.println(Thread.currentThread().getName() + "------------");
        return Thread.currentThread().getName() + "executed over~";
    }
}

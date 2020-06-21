package com.thread.impl;

import java.util.concurrent.Callable;

public class CallAbleImpl implements Callable {
    private String name;

    public CallAbleImpl(String name) {
        this.name = name;
    }

    public Object call() {
        for (int i = 0; i < 2; i++)
            System.out.println(Thread.currentThread().getName() + "--" + i + "--");
        return Thread.currentThread().getName() + "executed over~";
    }
}

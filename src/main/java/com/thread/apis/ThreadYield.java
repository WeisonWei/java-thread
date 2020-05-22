package com.thread.apis;

import java.util.stream.IntStream;

public class ThreadYield {
    public static void main(String[] args) {
        IntStream.range(0, 10).mapToObj(ThreadYield::create).forEach(Thread::start);

    }

    private static Thread create(int index) {
        return new Thread(() -> {
            //注释if判断 总是正常的顺序
            if (index == 0)
                Thread.yield();
            System.out.println(Thread.currentThread().getName() + ":" + index);
        });
    }
}

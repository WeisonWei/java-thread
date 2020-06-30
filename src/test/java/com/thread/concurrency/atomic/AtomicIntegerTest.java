package com.thread.concurrency.atomic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;


public class AtomicIntegerTest {

    @Test
    public void atomicIntegerTest() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int i1 = atomicInteger.addAndGet(1);
                System.out.println(i1);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int i1 = atomicInteger.addAndGet(1);
                System.out.println(i1);
            }
        }).start();

        Assertions.assertEquals(200L, atomicInteger.get());
    }
}

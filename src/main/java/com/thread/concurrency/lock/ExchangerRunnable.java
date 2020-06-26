package com.thread.concurrency.lock;

import lombok.Data;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

@Data
public class ExchangerRunnable implements Runnable {

    private Exchanger<Integer> exchanger = new Exchanger<>();
    private volatile static int data = 0;
    private Boolean isConsumer = false;

    public ExchangerRunnable(Boolean isConsumer, Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
        this.isConsumer = isConsumer;
    }

    @Override
    public void run() {
        if (isConsumer)
            pollingExchange();
        else
            cycleExchange();
    }

    private void pollingExchange() {
        for (int i = 1; i < 3; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(getName() + " producer赋值前:" + data + " time:" + System.currentTimeMillis());
                data = i;
                System.out.println(getName() + " producer赋值后交换前:" + data + " time:" + System.currentTimeMillis());
                data = exchanger.exchange(data);
                System.out.println(getName() + " producer交换后:" + data + " time:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void cycleExchange() {
        while (true) {
            try {
                System.out.println(getName() + " consumer交换前:" + data + " time:" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(1);
                data = exchanger.exchange(data);
                System.out.println(getName() + " consumer交换后:" + data + " time:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getName() {
        return Thread.currentThread().getName();
    }
}

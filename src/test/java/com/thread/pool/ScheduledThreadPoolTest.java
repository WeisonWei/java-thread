package com.thread.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {

    @Test
    public void fixed() {

        ScheduledExecutorService scheduledThreadPool1 = Executors.newScheduledThreadPool(5);
        scheduledThreadPool1.schedule(() -> System.out.println("delay 3 seconds"), 3, TimeUnit.SECONDS);

        ScheduledExecutorService scheduledThreadPool2 = Executors.newScheduledThreadPool(5);
        scheduledThreadPool2.scheduleAtFixedRate(() -> System.out.println("delay 1 seconds, and execute every 3 seconds"), 1, 3, TimeUnit.SECONDS);
    }
}

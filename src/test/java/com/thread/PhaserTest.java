package com.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PhaserTest {

    @Test
    public void phaserCyclicBarrier() throws InterruptedException {
        int parties = 10;
        Phaser phaser = new Phaser(parties);
        phaser.register();
        ExecutorService executor = Executors.newFixedThreadPool(parties);
        for (int i = 0; i < parties; i++) {
            final int threadNum = i;
            executor.execute(() -> {
                try {
                    log.info("Thread{}--到达屏障", Thread.currentThread().getName());
                    phaser.awaitAdvance(1);
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Thread{}--冲破屏障", Thread.currentThread().getName());
            });
        }
        executor.shutdown();
    }
}

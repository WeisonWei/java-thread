package com.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

@Slf4j
public class PhaserTest {

    @Test
    public void phaserTest() throws InterruptedException {
        Phaser phaser = new Phaser(5);
        getInfo(phaser);
    }

    /**
     * https://www.bilibili.com/video/BV1RJ411a7Tp?from=search&seid=16454305471171556776
     * @throws InterruptedException
     */
    @Test
    public void phaserCyclicBarrier() throws InterruptedException {
        Phaser phaser = new Phaser(1);
        getInfo(phaser);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(new Double(Math.random() * 3000).longValue());
                    log.info("Thread{}--到达屏障", currentThread().getName() + " Time:" + System.currentTimeMillis());
                    phaser.arriveAndAwaitAdvance();
                    getInfo(phaser);
                    log.info("Thread{}--就绪完成", currentThread().getName() + " Time:" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void phaserCountDown() throws InterruptedException {
        Phaser phaser = new Phaser();
        phaser.bulkRegister(5);
        getInfo(phaser);
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("Thread{}--到达屏障", currentThread().getName() + " Time:" + System.currentTimeMillis());
                    phaser.arrive();
                    getInfo(phaser);
                    log.info("Thread{}--就绪完成", currentThread().getName() + " Time:" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("Thread{}--冲破屏障", currentThread().getName() + " Time:" + System.currentTimeMillis());
            }).start();
        }
        phaser.awaitAdvance(0);
        log.info("Thread{}--0号房间-游戏开始", currentThread().getName());
        phaser.awaitAdvance(1);
        log.info("Thread{}--1号房间-游戏开始", currentThread().getName());

    }

    private void getInfo(Phaser phaser) {
        log.info("Thread{}--阶段:{},到达数:{},未到达数:{},总数:{},Time:{}", currentThread().getName(), phaser.getPhase()
                , phaser.getArrivedParties(), phaser.getUnarrivedParties(), phaser.getRegisteredParties(), System.currentTimeMillis());
    }
}

package com.thread.concurrency.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CopyOnWriteArrayListTest {

    /**
     * 用于:读多写少的并发场景 --> 白名单 黑名单 商品类目的访问和更新场景
     * https://www.cnblogs.com/java-zzl/p/9783431.html
     */
    @Test
    public void copyOnWriteTest() {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                copyOnWriteArrayList.add(i);
                log.info("write-" + i + "-->" + copyOnWriteArrayList.toString());
                sleepOneMilliSecond();
            }
        }, "write").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                log.info("read-" + i + "-->" + copyOnWriteArrayList.toString());
            }
        }, "read").start();

    }

    private void sleepOneMilliSecond() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

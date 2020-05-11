package com.concurrency.blocking.queue;

import lombok.val;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {

    @Test
    public void test() {
        BlockingQueue linkedBlockingQueue1 = new LinkedBlockingQueue(1);

        val t1 = new Thread(() -> {
            linkedBlockingQueue1.add(1);
            System.out.println("===============");
        }, "t1");

        t1.start();
    }
}

/**
 * ArrayBlockingQueue和LinkedBlockingQueue的区别：
 * 1. 队列中锁的实现不同
 * ArrayBlockingQueue实现的队列中的锁是没有分离的，即生产和消费用的是同一个锁；
 * LinkedBlockingQueue实现的队列中的锁是分离的，即生产用的是putLock，消费是takeLock
 * 2. 在生产或消费时操作不同
 * ArrayBlockingQueue实现的队列中在生产和消费的时候，是直接将枚举对象插入或移除的；
 * LinkedBlockingQueue实现的队列中在生产和消费的时候，需要把枚举对象转换为Node<E>进行插入或移除，会影响性能
 * 3. 队列大小初始化方式不同
 * ArrayBlockingQueue实现的队列中必须指定队列的大小；
 * LinkedBlockingQueue实现的队列中可以不指定队列的大小，但是默认是Integer.MAX_VALUE
 */
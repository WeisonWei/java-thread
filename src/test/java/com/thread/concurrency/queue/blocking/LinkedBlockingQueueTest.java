package com.thread.concurrency.queue.blocking;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
@Slf4j
public class LinkedBlockingQueueTest {

    @Test
    public void test() {
        BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1);
        val t1 = new Thread(() -> {
            linkedBlockingQueue.add(1);
            System.out.println("===============");
        }, "t1");

        t1.start();
    }

    /**
     * Collection : add remove
     * ================================
     * Queue : add offer remove poll element peek
     * ================================
     * BlockingQueue : add offer(e) PUT offer(e,t,u) TAKE remove poll element peek DRAINTO CONTAINS
     * ================================
     *
     * 尾加非阻塞 add[e]
     * 尾加阻塞 put offer
     * 取头非阻塞 take
     * 取头阻塞
     * 取尾非阻塞
     * 取尾阻塞
     *
     * @throws InterruptedException
     */

    @Test
    public void testApi() throws InterruptedException {
        Collection collection = new LinkedBlockingQueue(3);
        Queue queue = new LinkedBlockingQueue(3);
        BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(3);

        linkedBlockingQueue.add(1);
        linkedBlockingQueue.add(2);
        linkedBlockingQueue.add(3);
        linkedBlockingQueue.add(4);

        Object remove = linkedBlockingQueue.remove();
        log.info("=={}==", "I love U!");

        //Collection
        linkedBlockingQueue.add(1);
        linkedBlockingQueue.remove();
        linkedBlockingQueue.remove(1);
        //Queue
        linkedBlockingQueue.offer(2);
        linkedBlockingQueue.poll(3, TimeUnit.SECONDS); //不取走会阻塞
        linkedBlockingQueue.put(3);
        Object element = linkedBlockingQueue.element(); //轮询拿到头部元素
        Object poll1 = linkedBlockingQueue.poll(1, TimeUnit.SECONDS);
        Object poll = linkedBlockingQueue.poll();//为空时再取则阻塞
        Object peek = linkedBlockingQueue.peek();
        log.info("=={}==", "I love U!");
    }
}


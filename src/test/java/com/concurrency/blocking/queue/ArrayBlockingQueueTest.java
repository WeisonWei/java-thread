package com.concurrency.blocking.queue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ArrayBlockingQueueTest {

    /**
     * ArrayBlockingQueue 中的几个重要的方法。
     * <p>
     * add(E e)：把 e 加到 BlockingQueue 里，即如果 BlockingQueue 可以容纳，则返回 true，否则报异常
     * offer(E e)：表示如果可能的话，将 e 加到 BlockingQueue 里，即如果 BlockingQueue 可以容纳，则返回 true，否则返回 false
     * put(E e)：把 e 加到 BlockingQueue 里，如果 BlockQueue 没有空间，则调用此方法的线程被阻断直到 BlockingQueue 里面有空间再继续
     * poll(time)：取走 BlockingQueue 里排在首位的对象，若不能立即取出，则可以等 time 参数规定的时间,取不到时返回 null
     * take()：取走 BlockingQueue 里排在首位的对象，若 BlockingQueue 为空，阻断进入等待状态直到 Blocking 有新的对象被加入为止
     * remainingCapacity()：剩余可用的大小。等于初始容量减去当前的 size。
     * <p>
     * https://zhuanlan.zhihu.com/p/92576297
     * https://www.jianshu.com/p/8503349b27f4
     *
     * @throws InterruptedException
     */

    /**
     *  通过源码分析，我们可以发现下面的规律：
     *
     *     阻塞调用方式 put(e)或 offer(e, timeout, unit)
     *     阻塞调用时，唤醒条件为超时或者队列非满（因此，要求在出队时，要发起一个唤醒操作）
     *     进队成功之后，执行notEmpty.signal()唤起被阻塞的出队线程
     * @throws InterruptedException
     */

    /**
     * 总结 : 尽量使用阻塞调用方式put 和 offer
     *
     * @throws InterruptedException
     */

    @Test
    public void test() throws InterruptedException {

        BlockingQueue arrayBlockingQueue1 = new ArrayBlockingQueue(1);
        BlockingQueue arrayBlockingQueue2 = new ArrayBlockingQueue(1);
        BlockingQueue arrayBlockingQueue3 = new ArrayBlockingQueue(1);
        BlockingQueue arrayBlockingQueue4 = new ArrayBlockingQueue(1, true);

        // java.lang.IllegalStateException: Queue full
        Thread thread1 = new Thread(() -> arrayBlockingQueue1.add(1), "t1");
        Thread thread2 = new Thread(() -> arrayBlockingQueue1.add(2), "t2");

        Thread thread3 = new Thread(() -> {
            try {
                arrayBlockingQueue2.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");

        Thread thread4 = new Thread(() -> {
            try {
                arrayBlockingQueue2.put(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4");

        Thread thread5 = new Thread(() -> {
            try {
                Object take = arrayBlockingQueue2.take();
                log.info("---->" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");

        Thread thread6 = new Thread(() -> {
            arrayBlockingQueue3.add(6);
        }, "t6");

        Thread thread7 = new Thread(() -> {
            try {
                Object take = arrayBlockingQueue3.offer(7, 5, TimeUnit.SECONDS);
                log.info("---->" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t7");

        Thread thread8 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                Object take = arrayBlockingQueue3.take();
                log.info("---->" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t8");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();

        TimeUnit.SECONDS.sleep(20);
    }
}

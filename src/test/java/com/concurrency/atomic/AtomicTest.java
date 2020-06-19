package com.concurrency.atomic;

import com.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;

@Slf4j
public class AtomicTest {
    public static Integer ii = new Integer(0);
    public volatile static Integer iv = new Integer(0);
    public static AtomicLong al = new AtomicLong();
    public static AtomicInteger ai = new AtomicInteger();
    public static AtomicBoolean ab = new AtomicBoolean();
    public static AtomicIntegerArray aia = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});
    public static AtomicLongArray ala = new AtomicLongArray(new long[]{1, 2, 3, 4, 5});
    public static AtomicReferenceArray ara = new AtomicReferenceArray(new User[]{null});
    /*public static AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater
            .newUpdater(User.class, "age");*/


    /**
     * https://www.jianshu.com/p/cda24891f9e4
     * https://www.jianshu.com/p/96ccc5dbd8c5
     * 1 操纵对象属性
     * 2 操纵数组元素
     * 3 线程挂起与恢复、CAS
     */
    @Test
    public void unsafeTest() throws NoSuchFieldException {
        User user = new User("Even",10);
        Unsafe unsafe = Unsafe.getUnsafe();
        log.info("=====end====={}", al);
        Field name = user.getClass().getDeclaredField("name");
        long nameOffset = unsafe.objectFieldOffset(name);
        unsafe.putObject(user, nameOffset, "jim");
        log.info("=====end=====%d", iv);
    }

    @Test
    public void volatileIntegerTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", al);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                iv++;
                log.info("=====" + finalI + "=====" + iv);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", iv);
    }

    @Test
    public void integerTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", al);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                ii++;
                log.info("=====" + finalI + "=====" + ii);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", ii);
    }

    /**
     * https://blog.csdn.net/lhn1234321/article/details/82193289
     * https://www.jianshu.com/p/4c4979cc97ca
     *
     * @throws InterruptedException
     */
    @Test
    public void atomicLongTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", al);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                al.incrementAndGet();
                log.info("=====" + finalI + "=====" + al);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", al);
    }

    @Test
    public void atomicIntegerTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====end====={}", ai);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                ai.incrementAndGet();
                log.info("=====" + finalI + "=====" + ai);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====%d", ai);
    }

    @Test
    public void atomicUpdaterTest() throws InterruptedException {
        //可用于原子地更新User类的old字段。old字段必须是public volatile修饰的。
        AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User conan = new User("Even", 10);
        a.getAndIncrement(conan);
        System.out.println();
    }
}

package com.thread.obviouslock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class LockClient {
    private final Lock lock = new BooleanLock();

    /**
     * 可中断同步方法
     */
    public void syncMethod() {
        try {
            lock.lock();
            int randomInt = current().nextInt(10);
            System.out.println(currentThread() + "get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 可超时同步方法
     */
    public void syncMethodTimeoutable() {
        try {
            lock.lock(1000);
            System.out.println(currentThread() + "get the lock.");
            int randomInt = current().nextInt(10);
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //1 lock()方法使BooleanLock具有synchronized同步的效果
        LockClient lockClient = new LockClient();
        IntStream.range(0, 10).mapToObj(i -> new Thread(lockClient::syncMethod)).forEach(Thread::start);

        //2 syncMethod方法捕获了中断异常 使线程可获得中断信息
        LockClient lockClientInterrupt = new LockClient();
        new Thread(lockClientInterrupt::syncMethod, "T1").start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(lockClientInterrupt::syncMethod, "T2");
        t2.start();
        t2.interrupt();

        //3 syncMethodTimeoutable方法调用lock(long mills) 使线程竞争monitor锁可超时
        LockClient lockClientTimeoutable = new LockClient();
        new Thread(lockClientTimeoutable::syncMethod, "T3").start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t4 = new Thread(lockClientTimeoutable::syncMethodTimeoutable, "T4");
        t4.start();
        TimeUnit.MILLISECONDS.sleep(10);

    }


}

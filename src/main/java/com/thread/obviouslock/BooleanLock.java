package com.thread.obviouslock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;

public class BooleanLock implements Lock {


    private Thread currentThread;

    private boolean locked = false;
    private final List<Thread> blockedList = new ArrayList<>();

    /**
     * lock方法
     * 1.使BooleanLock具有synchronized同步的特效
     * 2.使线程可获取中断信息
     *
     * @throws InterruptedException
     */
    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {
                //暂存当前线程
                final Thread tempThread = currentThread();

                try {
                    //防止线程被中断 该线程还在blockedList中
                    if (blockedList.contains(tempThread))
                        blockedList.add(tempThread);
                    this.wait();
                } catch (InterruptedException e) {
                    //如果当前线程在wait时被中断，则从blockedList中将其删除，避免内存泄漏
                    blockedList.remove(tempThread);
                    //继续抛出中断异常
                    throw e;
                }
            }
            blockedList.remove(currentThread());
            this.locked = true;
            this.currentThread = currentThread();
        }
    }

    /**
     * @param mills
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (mills < 0) {
                this.lock();
            } else {
                long remainingMills = mills;
                long endMills = currentTimeMillis() + remainingMills;
                while (locked) {
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the lock during" + mills);
                    }
                    if (!blockedList.contains(currentThread)) {
                        blockedList.add(currentThread);
                    }
                    this.wait(remainingMills);

                    remainingMills = endMills - currentTimeMillis();
                }
                blockedList.remove(currentThread());
                this.locked = true;
                this.currentThread = currentThread();
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == currentThread()) {
                this.locked = false;
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return null;
    }
}

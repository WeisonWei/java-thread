package com.thread.sync;

public class DeadLock {

    private final Object MUTEX_READ = new Object();
    private final Object MUTEX_WRITE = new Object();

    private void read() {
        synchronized (MUTEX_READ) {
            System.out.println(Thread.currentThread().getName() + "get READ lock");
            synchronized (MUTEX_WRITE) {
                System.out.println(Thread.currentThread().getName() + "get WRITE lock");
            }
            System.out.println(Thread.currentThread().getName() + "ralease WRITE lock");
        }
        System.out.println(Thread.currentThread().getName() + "ralease READ lock");
    }

    private void write() {
        synchronized (MUTEX_WRITE) {
            System.out.println(Thread.currentThread().getName() + "get WRITE lock");
            synchronized (MUTEX_READ) {
                System.out.println(Thread.currentThread().getName() + "get READ lock");
            }
            System.out.println(Thread.currentThread().getName() + "ralease READ lock");
        }
        System.out.println(Thread.currentThread().getName() + "ralease WRITE lock");
    }

    public static void main(String[] args) {
        final DeadLock deadLock = new DeadLock();
        //Thread-Read 线程
        new Thread(() -> {
            while (true) {
                deadLock.read();
            }
        }, "Thread-Read").start();
        //Thread-Write 线程
        new Thread(() -> {
            while (true) {
                deadLock.write();
            }
        }, "Thread-Write").start();

    }
}

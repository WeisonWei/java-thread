package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class BankAccountReentrantReadWriteLock {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private long balance;

    public BankAccountReentrantReadWriteLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.writeLock().lock();
        try {
            //log.info(Thread.currentThread().getName() + " 获取写锁 " + System.nanoTime());
            balance += amount;
        } finally {
            lock.writeLock().unlock();
            //log.info(Thread.currentThread().getName() + " 释放写锁 " + System.nanoTime());
        }
    }

    public void withdraw(long amount) {
        lock.writeLock().lock();
        try {
            //log.info(Thread.currentThread().getName() + " 获取写锁 " + System.nanoTime());
            balance -= amount;
        } finally {
            lock.writeLock().unlock();
            //log.info(Thread.currentThread().getName() + " 释放写锁 " + System.nanoTime());
        }
    }

    public long getBalance() {
        lock.readLock().lock();
        try {
            //log.info(Thread.currentThread().getName() + " 获取读锁 " + System.nanoTime());
            return balance;
        } finally {
            lock.readLock().unlock();
            //log.info(Thread.currentThread().getName() + " 释放读锁 " + System.nanoTime());
        }
    }
}

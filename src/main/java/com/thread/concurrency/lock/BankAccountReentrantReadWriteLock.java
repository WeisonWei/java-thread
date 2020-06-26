package com.thread.concurrency.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccountReentrantReadWriteLock {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private long balance;

    public BankAccountReentrantReadWriteLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.writeLock().lock();
        try {
            balance += amount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void withdraw(long amount) {
        lock.writeLock().lock();
        try {
            balance -= amount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public long getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }
}

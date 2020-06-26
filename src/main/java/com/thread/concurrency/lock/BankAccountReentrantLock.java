package com.thread.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountReentrantLock {
    private final Lock lock = new ReentrantLock();
    private long balance;

    public BankAccountReentrantLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(long amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    public long getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}

package com.thread.concurrency.lock;

import java.util.concurrent.atomic.AtomicLong;

public class BankAccountAtomic {

    private final AtomicLong balance;

    public BankAccountAtomic(long balance) {
        this.balance = new AtomicLong(balance);
    }

    public void deposit(long amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(long amount) {
        balance.addAndGet(-amount);
    }

    public long getBalance() {
        return balance.get();
    }
}

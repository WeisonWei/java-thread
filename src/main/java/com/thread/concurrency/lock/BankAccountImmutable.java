package com.thread.concurrency.lock;

public class BankAccountImmutable {

    private final long balance;

    public BankAccountImmutable(long balance) {
        this.balance = balance;
    }

    public BankAccountImmutable deposit(long amount) {
        return new BankAccountImmutable(balance + amount);
    }

    public BankAccountImmutable withdraw(long amount) {
        return new BankAccountImmutable(balance - amount);
    }

    public long getBalance() {
        return balance;
    }
}

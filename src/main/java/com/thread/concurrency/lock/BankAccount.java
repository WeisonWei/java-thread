package com.thread.concurrency.lock;

/**
 * https://www.javaspecialists.eu/archive/Issue215.html
 */
public class BankAccount {

    private long balance;

    public BankAccount(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public void withdraw(long amount) {
        balance -= amount;
    }

    public long getBalance() {
        return balance;
    }
}

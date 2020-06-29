package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * https://www.javaspecialists.eu/archive/Issue215.html
 */
@Slf4j
public class BankAccount {

    private long balance;

    public BankAccount(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        balance += amount;
        //log(balance);
    }

    public void withdraw(long amount) {
        balance -= amount;
        //log(balance);
    }

    public long getBalance() {
        return balance;
    }

    private void log(Long balance) {
        log.info("thread:{},time:{},balance:{}", getName(), getTime(), balance);
    }

    private long getTime() {
        return System.nanoTime();
    }

    private String getName() {
        return Thread.currentThread().getName();
    }
}

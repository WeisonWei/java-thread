package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class LockTest {

    /*
     * @throws InterruptedException
     */
    @Test
    public void bankAccountTest() throws InterruptedException {
        BankAccount bankAccount = new BankAccount(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.deposit(1);
                log(i, bankAccount.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.withdraw(1);
                log(i, bankAccount.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccount.getBalance());
    }

    @Test
    public void bankAccountAtomicTest() throws InterruptedException {
        BankAccountAtomic bankAccountAtomic = new BankAccountAtomic(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountAtomicTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountAtomic.deposit(1);
                log(i, bankAccountAtomic.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountAtomic.withdraw(1);
                log(i, bankAccountAtomic.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountAtomic.getBalance());
    }

    @Test
    public void bankAccountImmutableTest() throws InterruptedException {
        BankAccountImmutable bankAccountImmutable = new BankAccountImmutable(0);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountImmutableTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountImmutable.deposit(1);
                log(i, bankAccountImmutable.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountImmutable.withdraw(1);
                log(i, bankAccountImmutable.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountImmutable.getBalance());
    }

    @Test
    public void bankAccountReentrantLockTest() throws InterruptedException {
        BankAccountReentrantLock bankAccountReentrantLock = new BankAccountReentrantLock(0);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountReentrantLockTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountReentrantLock.deposit(1);
                log(i, bankAccountReentrantLock.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountReentrantLock.withdraw(1);
                log(i, bankAccountReentrantLock.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountReentrantLock.getBalance());

    }

    @Test
    public void bankAccountReentrantReadWriteLockTest() throws InterruptedException {
        BankAccountReentrantReadWriteLock bankAccountReentrantReadWriteLock = new BankAccountReentrantReadWriteLock(0);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountReentrantReadWriteLockTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountReentrantReadWriteLock.deposit(1);
                log(i, bankAccountReentrantReadWriteLock.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountReentrantReadWriteLock.withdraw(1);
                log(i, bankAccountReentrantReadWriteLock.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountReentrantReadWriteLock.getBalance());
    }

    @Test
    public void bankAccountStampedLockTest() throws InterruptedException {
        BankAccountStampedLock bankAccountStampedLock = new BankAccountStampedLock(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountStampedLockTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountStampedLock.deposit(1);
                log(i, bankAccountStampedLock.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountStampedLock.withdraw(1);
                log(i, bankAccountStampedLock.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountStampedLock.getBalance());

    }

    @Test
    public void BankAccountSynchronizedTest() throws InterruptedException {
        BankAccountSynchronized bankAccountSynchronized = new BankAccountSynchronized(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        log.info("========BankAccountSynchronizedTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountSynchronized.deposit(1);
                log(i, bankAccountSynchronized.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountSynchronized.withdraw(1);
                log(i, bankAccountSynchronized.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountSynchronized.getBalance());
    }

    @Test
    public void BankAccountSynchronizedVolatileTest() throws InterruptedException {
        BankAccountSynchronizedVolatile bankAccountSynchronizedVolatile = new BankAccountSynchronizedVolatile(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        log.info("========BankAccountSynchronizedVolatileTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountSynchronizedVolatile.deposit(1);
                log(i, bankAccountSynchronizedVolatile.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccountSynchronizedVolatile.withdraw(1);
                log(i, bankAccountSynchronizedVolatile.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(1000000, bankAccountSynchronizedVolatile.getBalance());
    }

    private void log(int i, Long balance) {
        log.info("thread:{},times:{},time:{},balance:{}", getName(), i, getTime(), balance);
    }

    private void log(int i, String point, Long balance) {
        log.info("thread:{},point:{},times:{},time:{},balance:{}", getName(), point, i, getTime(), balance);
    }

    private long getTime() {
        return System.currentTimeMillis();
    }

    private String getName() {
        return Thread.currentThread().getName();
    }
}

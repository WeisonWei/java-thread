package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LockTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Test
    public void bankAccountTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        BankAccount bankAccount = new BankAccount(0);

        log.info("========bankAccountTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 10000000; i++) {
                bankAccount.deposit(1);
            }
            log(1000000000, bankAccount.getBalance());
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 10000000; i++) {
                bankAccount.deposit(1);
            }
            log(1000000001, bankAccount.getBalance());
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        log(1000000002, bankAccount.getBalance());
        Assertions.assertEquals(20000000L, bankAccount.getBalance());

    }

    @Test
    public void bankAccountTest1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(200);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(200);
        BankAccount bankAccount = new BankAccount(0);

        log.info("========bankAccountTest1==========");
        ExecutorService pool1 = Executors.newCachedThreadPool();
        ExecutorService pool2 = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            pool1.execute(() -> {
                bankAccount.deposit(1);
                countDownLatch.countDown();
                cyclicBarrierAwait(cyclicBarrier);
            });
        }

        for (int i = 0; i < 100; i++) {
            pool2.execute(() -> {
                bankAccount.deposit(1);
                countDownLatch.countDown();
                cyclicBarrierAwait(cyclicBarrier);
            });
        }

        countDownLatch.await();
        pool1.shutdown();
        pool2.shutdown();
        Assertions.assertEquals(0, bankAccount.getBalance());
    }

    @Test
    public void bankAccountAtomicTest() throws InterruptedException {
        BankAccountAtomic bankAccountAtomic = new BankAccountAtomic(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        log.info("========bankAccountAtomicTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 10000; i++) {
                atomicInteger.getAndIncrement();
                //bankAccountAtomic.deposit(1);
                //log(i, bankAccountAtomic.getBalance());
            }
            log(1000000000, Long.valueOf(atomicInteger.get()));
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 10000; i++) {
                atomicInteger.getAndIncrement();
                //bankAccountAtomic.deposit(1);
                //log(i, bankAccountAtomic.getBalance());
            }
            log(1000000001, Long.valueOf(atomicInteger.get()));
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        log(1000000002, Long.valueOf(atomicInteger.get()));
        Assertions.assertEquals(20000, Long.valueOf(atomicInteger.get()));

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
        }, "deposit+").start();

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
        }, "deposit+").start();

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
        }, "deposit+").start();

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
        }, "deposit+").start();

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
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        log.info("========BankAccountSynchronizedTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                bankAccountSynchronized.deposit(1);
                log(i, bankAccountSynchronized.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 withdraw
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                bankAccountSynchronized.deposit(1);
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
        }, "deposit+").start();

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
        return System.nanoTime();
    }

    private String getName() {
        return Thread.currentThread().getName();
    }


    private void cyclicBarrierAwait(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

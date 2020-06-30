package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全
 * https://juejin.im/post/5d2c97bff265da1bc552954b
 */
@Slf4j
public class LockTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 结果:线程不安全
     * 打印日志会耗费额外的性能 可能会影响结果
     * org.opentest4j.AssertionFailedError:
     * Expected :20000
     * Actual   :13739
     *
     * @throws InterruptedException
     */
    @Order(1)
    @Test
    @RepeatedTest(10)
    public void bankAccountTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        BankAccount bankAccount = new BankAccount(0);

        log.info("========bankAccountTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 10000; i++) {
                bankAccount.deposit(1);
            }
            //log(1000000000, bankAccount.getBalance());
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 10000; i++) {
                bankAccount.deposit(1);
            }
            //log(1000000001, bankAccount.getBalance());
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        log(10086, bankAccount.getBalance());
        Assertions.assertEquals(20000L, bankAccount.getBalance());

    }

    /**
     * 结果:线程不安全
     * org.opentest4j.AssertionFailedError:
     * Expected :20000
     * Actual   :19998
     *
     * @throws InterruptedException
     */
    @Order(2)
    @Test
    @RepeatedTest(10)
    public void bankAccountTest1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(20000);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        BankAccount bankAccount = new BankAccount(0);

        log.info("========bankAccountTest1==========");
        //使用newCachedThreadPool 不限制线程数 可能会 OutOfMemoryError
        //ExecutorService pool1 = Executors.newCachedThreadPool();
        //Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
        ExecutorService pool1 = Executors.newFixedThreadPool(10);
        ExecutorService pool2 = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10000; i++) {
            pool1.execute(() -> {
                cyclicBarrierAwait(cyclicBarrier);
                bankAccount.deposit(1);
                countDownLatch.countDown();
            });
        }

        for (int i = 0; i < 10000; i++) {
            pool2.execute(() -> {
                cyclicBarrierAwait(cyclicBarrier);
                bankAccount.deposit(1);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        pool1.shutdown();
        pool2.shutdown();
        log(10086, bankAccount.getBalance());
        Assertions.assertEquals(20000L, bankAccount.getBalance());
    }

    /**
     * 结果:断言正确，但不一定是线程安全，可能存在ABA现象
     *
     * @throws InterruptedException
     */
    @Order(3)
    @Test
    @RepeatedTest(10)
    public void bankAccountAtomicTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        log.info("========bankAccountAtomicTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                atomicInteger.getAndIncrement();
                log(i, Long.valueOf(atomicInteger.get()));
            }
            log(1000000000, Long.valueOf(atomicInteger.get()));
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                atomicInteger.getAndIncrement();
                log(i, Long.valueOf(atomicInteger.get()));
            }
            log(1000000001, Long.valueOf(atomicInteger.get()));
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        log(10086, Long.valueOf(atomicInteger.get()));
        Assertions.assertEquals(200L, Long.valueOf(atomicInteger.get()));

    }

    /**
     * 结果:断言正确，但不一定是线程安全，可能存在ABA现象
     *
     * @throws InterruptedException
     */
    @Order(4)
    @Test
    @RepeatedTest(10)
    public void bankAccountAtomicTest1() throws InterruptedException {
        BankAccountAtomic bankAccountAtomic = new BankAccountAtomic(0);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        log.info("========bankAccountAtomicTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                bankAccountAtomic.deposit(1);
                //log(i, bankAccountAtomic.getBalance());
            }
            log(1000000000, Long.valueOf(bankAccountAtomic.getBalance()));
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                bankAccountAtomic.deposit(1);
                //log(i, bankAccountAtomic.getBalance());
            }
            log(1000000000, Long.valueOf(bankAccountAtomic.getBalance()));
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        log(10086, Long.valueOf(bankAccountAtomic.getBalance()));
        Assertions.assertEquals(200L, Long.valueOf(bankAccountAtomic.getBalance()));

    }


    /**
     * 结果:本案例思想为copyOnWrite,无法实现线程安全
     *
     * @throws InterruptedException
     */
    @Order(5)
    @Test
    @RepeatedTest(10)
    public void bankAccountImmutableTest() throws InterruptedException {
        BankAccountImmutable bankAccountImmutable = new BankAccountImmutable(0);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        log.info("========bankAccountImmutableTest==========");
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                BankAccountImmutable deposit = bankAccountImmutable.deposit(1);
                log(i, deposit.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                BankAccountImmutable deposit = bankAccountImmutable.deposit(1);
                log(i, deposit.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        //log(10086, bankAccountImmutable.getBalance());
    }

    /**
     * 结果:线程安全，执行顺序和结果均正确
     *
     * @throws InterruptedException
     */
    @Test
    @Order(6)
    @RepeatedTest(10)
    public void bankAccountReentrantLockTest() throws InterruptedException {
        BankAccountReentrantLock bankAccountReentrantLock = new BankAccountReentrantLock(0);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        log.info("========bankAccountReentrantLockTest==========");
        //1 deposit
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                bankAccountReentrantLock.deposit(1);
                log(i, bankAccountReentrantLock.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit+").start();

        //2 withdraw
        new Thread(() -> {
            cyclicBarrierAwait(cyclicBarrier);
            for (int i = 0; i < 100; i++) {
                bankAccountReentrantLock.deposit(1);
                log(i, bankAccountReentrantLock.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(10086, bankAccountReentrantLock.getBalance());
        Assertions.assertEquals(200L, bankAccountReentrantLock.getBalance());

    }

    /**
     * 结论:执行顺序和结果正确，线程安全
     *
     * @throws InterruptedException
     */
    @Test
    @Order(7)
    @RepeatedTest(10)
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
                bankAccountReentrantReadWriteLock.deposit(1);
                log(i, bankAccountReentrantReadWriteLock.getBalance());
            }
            countDownLatch.countDown();
        }, "deposit*").start();

        countDownLatch.await();
        log(10086, bankAccountReentrantReadWriteLock.getBalance());
        Assertions.assertEquals(200L, bankAccountReentrantReadWriteLock.getBalance());
    }

    /**
     * 结果:执行顺序和结果正确，线程安全，时间戳锁，有乐观锁特性
     * @throws InterruptedException
     */
    @Test
    @Order(8)
    @RepeatedTest(10)
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
                bankAccountStampedLock.deposit(1);
                log(i, bankAccountStampedLock.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(10086, bankAccountStampedLock.getBalance());
        Assertions.assertEquals(200L, bankAccountStampedLock.getBalance());
    }

    /**
     * 结果：悲观锁，线程安全
     * @throws InterruptedException
     */
    @Test
    @Order(9)
    @RepeatedTest(10)
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
        Assertions.assertEquals(200L, bankAccountSynchronized.getBalance());
    }

    /**
     * 结果：悲观锁+Volatile，双重保证，线程安全
     * @throws InterruptedException
     */
    @Test
    @Order(10)
    @RepeatedTest(10)
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
                bankAccountSynchronizedVolatile.deposit(1);
                log(i, bankAccountSynchronizedVolatile.getBalance());
            }
            countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
        log(10086, bankAccountSynchronizedVolatile.getBalance());
        Assertions.assertEquals(200L, bankAccountSynchronizedVolatile.getBalance());
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

package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class LockTest {
    private final BankAccount bankAccount = new BankAccount(0);

    /**
     *  INFO - thread:deposit,times:90,time:1593187877441,balance:90
     *  INFO - thread:withdraw,times:90,time:1593187877441,balance:90
     *  INFO - thread:deposit,times:91,time:1593187877441,balance:91
     *  INFO - thread:withdraw,times:91,time:1593187877441,balance:91
     *  INFO - thread:deposit,times:92,time:1593187877441,balance:92
     *  INFO - thread:withdraw,times:92,time:1593187877441,balance:92
     *  INFO - thread:deposit,times:93,time:1593187877441,balance:93
     *  INFO - thread:deposit,times:94,time:1593187877441,balance:94
     *  INFO - thread:deposit,times:95,time:1593187877441,balance:95
     *  INFO - thread:deposit,times:96,time:1593187877442,balance:96
     *  INFO - thread:deposit,times:97,time:1593187877442,balance:97
     *  INFO - thread:deposit,times:98,time:1593187877442,balance:98
     *  INFO - thread:deposit,times:99,time:1593187877442,balance:99
     *  INFO - thread:withdraw,times:93,time:1593187877443,balance:93
     *  INFO - thread:withdraw,times:94,time:1593187877443,balance:94
     *  INFO - thread:withdraw,times:95,time:1593187877444,balance:95
     *  INFO - thread:withdraw,times:96,time:1593187877444,balance:96
     *  INFO - thread:withdraw,times:97,time:1593187877444,balance:97
     *  INFO - thread:withdraw,times:98,time:1593187877444,balance:98
     *  INFO - thread:withdraw,times:99,time:1593187877444,balance:99
     * @throws InterruptedException
     */
    @Test
    public void bankAccountTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        //1 deposit
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.deposit(1);
                log(i);
            }
            //countDownLatch.countDown();
        }, "deposit").start();

        //1 withdraw
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.withdraw(1);
                log(i);
            }
            //countDownLatch.countDown();
        }, "withdraw").start();

        countDownLatch.await();
    }

    private void log(int i) {
        log.info("thread:{},times:{},time:{},balance:{}", getName(), i, getTime(), i, bankAccount.getBalance());
    }

    private long getTime() {
        return System.currentTimeMillis();
    }

    private String getName() {
        return Thread.currentThread().getName();
    }
}

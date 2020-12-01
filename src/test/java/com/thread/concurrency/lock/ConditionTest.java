package com.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConditionTest {


    /**
     * 在synchronized中我们可以使用wait(),notify()来让线程等待、唤醒
     * 在ReEntrantLock中我们可以用Condition中的await(),signal()来实现
     * <p>
     * Condition 是 Java 提供用来实现等待/通知的类
     * 它由lock对象创建,调用await()使当前线程进入阻塞,调用signal()唤醒该线程继续执行
     *
     * @throws InterruptedException
     */
    @Test
    public void conditionTest() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            try {
                lock.lock();
                log.info("==begin sleep==");
                TimeUnit.SECONDS.sleep(4);
                condition.signal();//condition.signalAll();
                log.info("==after signal==");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                log.info("==begin await==");
                condition.await();
                log.info("==have been single==");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await();

    }

    /**
     * Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。
     * 在Condition中，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()，传统线程的通信方式，Condition都可以实现，这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
     * 这样看来，Condition和传统的线程通信没什么区别，Condition的强大之处在于它可以为多个线程间建立不同的Condition
     */


    /**
     * Condition与Object中的wait,notify,notifyAll区别：
     *
     * 1.Condition中的await()方法相当于Object的wait()方法，Condition中的signal()方法相当于Object的notify()方法，Condition中的signalAll()相当于Object的notifyAll()方法。
     * 不同的是，Object中的这些方法是和同步锁捆绑使用的；而Condition是需要与互斥锁/共享锁捆绑使用的。
     *
     * 2.Condition它更强大的地方在于：能够更加精细的控制多线程的休眠与唤醒。对于同一个锁，我们可以创建多个Condition，在不同的情况下使用不同的Condition。
     * 例如，假如多线程读/写同一个缓冲区：当向缓冲区中写入数据之后，唤醒"读线程"；当从缓冲区读出数据之后，唤醒"写线程"；并且当缓冲区满的时候，"写线程"需要等待；当缓冲区为空时，"读线程"需要等待。
     * 如果采用Object类中的wait(),notify(),notifyAll()实现该缓冲区，当向缓冲区写入数据之后需要唤醒"读线程"时，不可能通过notify()或notifyAll()明确的指定唤醒"读线程"，而只能通过notifyAll唤醒所有线程(但是notifyAll无法区分唤醒的线程是读线程，还是写线程)。
     * 但是，通过Condition，就能明确的指定唤醒读线程。
     */
}

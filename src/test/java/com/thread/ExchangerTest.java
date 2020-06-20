package com.thread;

import com.concurrency.ExchangerRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ExchangerTest {

    /**
     * https://www.jianshu.com/p/990ae2ab1ae0
     */
    @Test
    public void test() throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerRunnable producer = new ExchangerRunnable(false, exchanger);
        ExchangerRunnable consumer = new ExchangerRunnable(true, exchanger);

        new Thread(producer).start();
        new Thread(consumer).start();
        TimeUnit.SECONDS.sleep(7);
        System.exit(-1);
    }
}

package com.concurrency.asyc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    /**
     * https://www.cnblogs.com/dennyzhangdd/p/7010972.html#_label3
     */
    @Test
    public void test() {

        CompletableFuture.supplyAsync(() -> {
            for (;;) {
                System.out.println("------");
            }
        });
    }

}

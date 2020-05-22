package com.concurrency.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class complatbleFutureTest {

    @Test
    public void cached() {
        CompletableFuture.supplyAsync(() -> {
            Thread.currentThread().setName("99889989898998");
            while (true){
                System.out.println("--------");
            }
        });
    }
}

package com.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPractice extends Thread {

    @Override
    public void run() {
        log.info("线程执行中...");
    }
}
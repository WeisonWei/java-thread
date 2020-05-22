package com.commom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunnablePractice extends Thread {

    private String threadName;

    @Override
    public void run() {
        log.info("[ " + threadName + " ]" + "开始执行");
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("[ " + threadName + " ]" + "-->" + i);
        }
        log.info("[ " + threadName + " ]" + "结束");
    }
}
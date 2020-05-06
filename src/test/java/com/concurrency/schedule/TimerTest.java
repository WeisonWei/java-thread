package com.concurrency.schedule;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimerTest {

    @Test
    public void timer1() throws InterruptedException {
        Timer timer = new Timer(); // 1. 创建Timer实例，关联线程不能是daemon(守护/后台)线程
        FooTimerTask fooTimerTask = new FooTimerTask(); // 2. 创建任务对象
        //timer.schedule(fooTimerTask, 1000L); // 1. 1s后执行fooTimerTask一次
        timer.schedule(fooTimerTask, 2000L, 1000L); //  1s后 执行fooTimerTask一次 以后每隔1s执行一次
        //timer.schedule(fooTimerTask, new Date()); // 在这个时间执行fooTimerTask一次
        //timer.schedule(fooTimerTask, new Date(), 1000L); // 在这个时间执行fooTimerTask一次 以后每隔1s执行一次

        TimeUnit.SECONDS.sleep(30l);
    }

}

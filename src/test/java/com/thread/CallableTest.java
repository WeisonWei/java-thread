package com.thread;

import com.thread.impl.CallAbleImpl;
import com.thread.impl.RunnableImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

@Slf4j
public class CallableTest {

    /**
     * https://www.cnblogs.com/dolphin0520/p/3949310.html
     *
     *能返回关键还是ExecutorService 的 submit()
     */
    @Test
    public void callTest() {
        //2 通过实现Runnable接口　
        Runnable runnable = new RunnableImpl();
        Thread thread = new Thread(runnable);
        //启用一个线程执行RunnableImpl　重写的run方法
        thread.start();

        //3 通过实现Callable接口 相较于实现 Callable 接口的方式，方法可以有返回值且可以抛出异常
        Callable callable = new CallAbleImpl("callable0");
        FutureTask oneTask = new FutureTask(callable);
        //注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。
        //由FutureTask<Integer>创建一个Thread对象：
        Thread oneThread = new Thread(oneTask);
        oneThread.start();

        //4 使用ExecutorService、Callable、Future实现有返回结果的线程
        Callable callable1 = new CallAbleImpl("callable1");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Integer> future = executor.submit(callable1);
        executor.shutdown();
    }
}

package com.thread.base;

import com.thread.impl.CallAbleImpl;
import com.thread.impl.RunnableImpl;
import com.thread.impl.ThreadChild;
import com.thread.runnable.*;

import java.util.concurrent.*;

/**
 * Thread基础
 * １ 创建线程的方式
 * 2 Thread 生命周期
 */
public class ThreadBase {
    public static void main(String[] a) {
        //使线程进入阻塞状态的方法
        /*
        Object的wait方法
        Thread的sleep方法
        Thread的join方法
        InterruptibleChannel的io操作
        Selector的wakeup方法
        */


        //1 通过继承Thread　
        ThreadChild threadChild = new ThreadChild();
        //启用一个线程执行threadChild　重写的run方法
        threadChild.start();

        //2 通过实现Runnable接口　
        Runnable runnable = new RunnableImpl();
        Thread thread = new Thread(runnable);
        //启用一个线程执行RunnableImpl　重写的run方法
        thread.start();

        //3 通过实现Callable接口 相较于实现 Callable 接口的方式，方法可以有返回值且可以抛出异常
        Callable callable = new CallAbleImpl("callable");
        FutureTask oneTask = new FutureTask(callable);
        //注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。
        //由FutureTask<Integer>创建一个Thread对象：
        Thread oneThread = new Thread(oneTask);
        oneThread.start();

        //4 使用ExecutorService、Callable、Future实现有返回结果的线程
        Callable callable1 = new CallAbleImpl("callable1");
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> future = executor.submit(callable1);
        executor.shutdown();


        //5策略模式的应用
        //策略角色：不同的Runnable实现类
        ThreeYearsOldRunnable threeYearsOldRunnable = new ThreeYearsOldRunnable();
        TenYearsOldRunnable tenYearsOldRunnable = new TenYearsOldRunnable();
        EighteenYearsOldRunnable eighteenYearsOldRunnable = new EighteenYearsOldRunnable();
        TwentyFiveYearsOldRunnable twentyFiveYearsOldRunnable = new TwentyFiveYearsOldRunnable();
        ThirtyYearsOldRunnable thirtyYearsOldRunnable = new ThirtyYearsOldRunnable();

        //上下文角色：Thread实例
        Thread threeYearsOld = new Thread(threeYearsOldRunnable, "三岁");
        Thread tenYearsOld = new Thread(tenYearsOldRunnable, "十岁");
        Thread eighteenYearsOld = new Thread(eighteenYearsOldRunnable, "十八岁");
        Thread wentyFiveYearsOld = new Thread(twentyFiveYearsOldRunnable, "二十五岁");
        Thread thirtyYearsOld = new Thread(thirtyYearsOldRunnable, "三十岁");

        //不同的上下文下
        for (int age = 0; age <= 100; age++) {
            if (3 == age)
                threeYearsOld.start();
            if (10 == age)
                tenYearsOld.start();
            if (18 == age)
                eighteenYearsOld.start();
            if (25 == age)
                wentyFiveYearsOld.start();
            if (30 == age)
                thirtyYearsOld.start();
        }
    }
}

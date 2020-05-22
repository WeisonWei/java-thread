package com.thread.pool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadPool extends Thread implements ThreadPool {
    //初始化线程数量
    private final int initSize;
    //线程池最大线程数量
    private final int maxSize;
    //线程池核心线程数量
    private final int coreSize;
    //当前活跃的线程数量
    private int activeCount;
    //创建线程所需的工程
    private final ThreadFactory threadFactory;
    //任务队列
    private final RunnableQueue runnableQueue;
    //线程池是否已经被shutDown
    private volatile boolean isShutDown;
    //工作线程队列
    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepAliveTime;
    private final TimeUnit timeUnit;

    //构造时需要传入的参数：初始线程数量，最大线程数量，核心线程数量，任务队列的最大数量
    public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {
        this(initSize, maxSize, queueSize, coreSize, DEFAULT_THREAD_FACTORY, DEFAULT_DENY_POLICY,
                10, TimeUnit.SECONDS);
    }

    //构造时需要传入的参数：该构造函数需要的参数比较多
    public BasicThreadPool(int initSize, int maxSize, int queueSize, int coreSize,
                           ThreadFactory threadFactory, DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }

    //初始化时先创建initSize个线程
    private void init() {
        start();
        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    //提交任务 只是将Runnable 插入runnableQueue
    @Override
    public void execute(Runnable runnable) {
        if (this.isShutDown)
            throw new IllegalStateException("the thread pook is destory");
        //提交任务只是简单的往任务队列中插入Runnable
        this.runnableQueue.offer(runnable);
    }

    //线程池自动维护
    private void newThread() {
        //创建任务线程，并启动
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }

    private void removeThread() {
        //冲线程池中移除某个线程
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }


    @Override
    public void run() {
        //继承自Thread 主要用于维护线程数量如扩容，回收等工作
        while (!isShutDown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutDown = true;
                break;
            }

            synchronized (this) {
                if (isShutDown)
                    break;
                //当前的队列中有任务尚未处理 并且activeCount<coreSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    //不让线程的扩容直接打到最大macSize
                    continue;
                }

                //当前的队列中有任务尚未处理 并且activeCount<maxSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    for (int i = coreSize; i < maxSize; i++) {
                        newThread();
                    }

                }

                //如果队伍中没有任务 则需要回收 回收至coreSize即可
                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    for (int i = coreSize; i < activeCount; i++) {
                        removeThread();
                    }

                }
            }


        }
    }

    //ThreadTask 只是InternalTask 和 Thread的一个组合
    private static class ThreadTask {
        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }

        Thread thread;
        InternalTask internalTask;
    }

    @Override
    public void shutdown() {
        //线程池的销毁
        synchronized (this) {
            if (isShutDown) return;
            isShutDown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if (isShutDown) throw new IllegalStateException("Thr thread pool is destory");
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutDown) throw new IllegalStateException("Thr thread pool is destory");
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutDown) throw new IllegalStateException("Thr thread pool is destory");
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutDown) throw new IllegalStateException("Thr thread pool is destory");
        return runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {
        return this.isShutDown;
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
        private static final ThreadGroup group = new ThreadGroup("MyThreadPool-" + GROUP_COUNTER.getAndDecrement());
        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group, runnable, "thread-pool-" + COUNTER.getAndDecrement());
        }
    }
}

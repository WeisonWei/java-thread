package com.thread.pool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {
    private final int limit;
    private final DenyPolicy denuPlicy;
    private final LinkedList<Runnable> runnableList = new LinkedList<>();
    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denuPlicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denuPlicy = denuPlicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableList) {
            //如果无法容纳新的任务执行拒绝策略
            if (runnableList.size() >= limit)
                denuPlicy.reject(runnable, threadPool);
            else {
                //将任务加入到队尾 并且唤醒阻塞中的线程
                runnableList.addLast(runnable);
                runnableList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableList) {
            //如果任务队列中没有可执行任务，则当前线程将会挂起，
            //进入runnableList关联的monitor waitSet中等待唤醒(新的任务加入)
            while (runnableList.isEmpty()) {
                try {
                    runnableList.wait();
                } catch (InterruptedException e) {
                    //被中断时需要将异常抛出
                    throw e;
                }
            }
            //从任务队列头部移除一个任务
            return runnableList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (runnableList)
        {
            //返回当前任务队列中的任务数
            return runnableList.size();
        }
    }
}

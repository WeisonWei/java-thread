package com.thread.pool;

/**
 * 线程提交拒绝策略接口
 */
@FunctionalInterface
public interface DenyPolicy {
    void reject(Runnable runnable, ThreadPool threadPool);

    /**
     * 直接将任务丢弃
     */
    class DiscardDenyPolicy implements DenyPolicy {

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            System.out.println("我拒绝了你");
        }
    }

    /**
     * 向任务提交者抛出异常
     */
    class AbortDenyPolicy implements DenyPolicy {

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            throw new RunnableDenyException("该线程" + runnable + "将被中断");
        }
    }

    /**
     * 使任务在提交者所在的线程中执行
     */
    class RunnerDenyPolicy implements DenyPolicy {

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            while (!threadPool.isShutdown()) {
                runnable.run();
            }
        }
    }
}

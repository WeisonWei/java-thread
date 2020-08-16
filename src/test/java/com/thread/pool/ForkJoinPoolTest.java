package com.thread.pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * https://www.cnblogs.com/cjsblog/p/9078341.html
 * 主要方法：
 * <p>
 * fork()    在当前线程运行的线程池中安排一个异步执行。简单的理解就是再创建一个子任务。
 * join()    当任务完成的时候返回计算结果。
 * invoke()    开始执行任务，如果必要，等待计算完成。
 * <p>
 * 子类：
 * <p>
 * RecursiveAction    一个递归无结果的ForkJoinTask（没有返回值）
 * RecursiveTask    一个递归有结果的ForkJoinTask（有返回值）
 */
public class ForkJoinPoolTest {


    /**
     * forkJoin这个框架针对的是大任务执行,效率才会明显的看出来有提升;
     * 通过设置不同的临界点值，会有不同的结果;
     * 逐渐的加大临界点值，效率会进一步提升;
     * 结论：
     * 所以不是用了forkJoin效率就会提升
     * JDK8提供的Stream流计算速度是三种方式中最快的
     */

    @Test
    public void forkJoin() {
        // 创建2亿个随机数组成的数组:
        long[] array = new long[200000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
        }

        //foreach //119
        long expectedSum = 0;
        long foreachStartTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            expectedSum += array[i];
        }
        long foreachEndTime = System.currentTimeMillis();
        System.out.println("foreach sum: " + expectedSum + " in " +
                (foreachEndTime - foreachStartTime) + " ms.");

        //stream //125
        long sStartTime = System.currentTimeMillis();
        long sum0 = Arrays.stream(array).sum();
        long sEndTime = System.currentTimeMillis();
        System.out.println("Stream sum: " + sum0 + " in " +
                (sEndTime - sStartTime) + " ms.");

        //parallelStream //84
        long expectedSum1 = 0;
        long psStartTime = System.currentTimeMillis();
        long sum = Arrays.stream(array).parallel().sum();
        long psEndTime = System.currentTimeMillis();
        System.out.println("ParallelStream sum: " + sum + " in " +
                (psEndTime - psStartTime) + " ms.");

        //fork/join //93 --临界值3千万
        ForkJoinTask<Long> task = new ChildTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    private void add(long expectedSum1, long value) {
        expectedSum1 = expectedSum1 + value;
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}

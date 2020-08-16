package com.thread.pool;

import java.util.concurrent.RecursiveTask;

public class ChildTask extends RecursiveTask<Long> {

    //设置不同的临界点 会影响执行时间
    static final int THRESHOLD = 30000000;
    long[] array;
    int start;
    int end;

    ChildTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // 如果任务足够小,直接计算:
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += this.array[i];
                // debug查看线程 放慢计算速度:
                /*try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                }*/
            }
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        ChildTask leftChild = new ChildTask(this.array, start, middle);
        ChildTask rightChild = new ChildTask(this.array, middle, end);
        invokeAll(leftChild, rightChild);
        Long leftResult = leftChild.join();
        Long rightResult = rightChild.join();
        Long result = leftResult + rightResult;
        System.out.println("result = " + leftResult + " + " + rightResult + " ==> " + result);
        return result;
    }
}

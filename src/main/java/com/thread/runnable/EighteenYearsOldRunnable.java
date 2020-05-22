package com.thread.runnable;

public class EighteenYearsOldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":告别难忘的高中时代，期待我的大学生活~");
    }
}

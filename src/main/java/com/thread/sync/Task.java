package com.thread.sync;

public class Task {


    //1 2 作用域太大
    private final Object MUTEX = new Object();

    public void syncMethod() {
        synchronized (MUTEX) {
            //do someThing...
        }
    }

    public static void main(String[] args) {

        //3 获取不同对象的锁
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                //定义一个关联对象 该对象不是公共资源
                final Object MUTEX = new Object();
                //synchronized获取与MUTEX关联的monitor锁
                synchronized (MUTEX) {
                    //do someThing...
                }
            }).start();
        }
    }

    //4 多个锁交叉导致死锁
    private final Object MUTEX_READ = new Object();
    private final Object MUTEX_WRITE = new Object();

    private void read() {
        synchronized (MUTEX_READ) {
            synchronized (MUTEX_WRITE) {
                //do someThing...
            }
        }
    }
    private void write() {
        synchronized (MUTEX_WRITE) {
            synchronized (MUTEX_READ) {
                //do someThing...
            }
        }
    }

}

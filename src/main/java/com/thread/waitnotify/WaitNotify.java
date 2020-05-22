package com.thread.waitnotify;

public class WaitNotify {
    public static void main(String[] args) {

        WaitNotify waitNotify = new WaitNotify();
        //在未获取关联对象monitor锁的情况下执行wait()和notify()会报IllegalMonitorStateException
        waitNotify.thisNotify();
        waitNotify.thisWait();
    }

    /**
     * 执行对象的wait方法需要获取对象的monitor锁
     */
    private void thisWait() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行对象的notify方法需要获取对象的monitor锁
     */
    private void thisNotify() {
        this.notify();
    }
}

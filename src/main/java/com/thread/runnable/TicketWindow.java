package com.thread.runnable;

public class TicketWindow extends Thread {
    //柜台名称
    private final String name;
    //最多受理的业务数
    private static final int MAX = 10;

    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;

    }

    @Override
    public void run() {
        while (index < MAX)
            System.out.println("柜台:" + name + "当前号码为:" + (index++));
    }
}

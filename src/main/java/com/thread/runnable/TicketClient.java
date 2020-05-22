package com.thread.runnable;

public class TicketClient {

    public static void main(String[] args) {
        TicketWindow ticketWindow1 = new TicketWindow("一号取号机");
        TicketWindow ticketWindow2 = new TicketWindow("二号取号机");
        TicketWindow ticketWindow3 = new TicketWindow("三号取号机");

        //三个TicketWindow线程公用静态MAX[10]
        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();

        //三个线程公用一个runnable的私有属性MAX[10]
        TicketRunnable ticketRunnable = new TicketRunnable();
        Thread ticketWindow4 = new Thread(ticketRunnable,"四号取号机");
        Thread ticketWindow5 = new Thread(ticketRunnable,"五号取号机");
        Thread ticketWindow6 = new Thread(ticketRunnable,"六号取号机");

        ticketWindow4.start();
        ticketWindow5.start();
        ticketWindow6.start();
    }
}

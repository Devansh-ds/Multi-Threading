package com.sadds.ConcurrentCollection;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        Thread one = new Thread(new FirstThread(exchanger));
        Thread two = new Thread(new SecondThread(exchanger));

        one.start();
        two.start();
    }

}

class FirstThread implements Runnable {

    private final Exchanger<Integer> exchanger;

    public FirstThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        int dataToSend = 10;
        System.out.println("First thread is Sending: " + dataToSend);

        /*
           due to exchange method called below, the other thread must call the
           exchange() method also otherwise this thread will be blocked and wait
           someone to exchange the object (data)
           Note: Same exchanger object must be passed to both threads who are calling
           the exchange() method
        */

        try {
            Integer receivedData = exchanger.exchange(dataToSend);
            System.out.println("First thread received: " + receivedData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SecondThread implements Runnable {

    private final Exchanger<Integer> exchanger;

    public SecondThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);

            int dataToSend = 20;
            System.out.println("Second thread is Sending: " + dataToSend);

            Integer receivedData = exchanger.exchange(dataToSend);
            System.out.println("Second thread received: " + receivedData);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}











package com.sadds.ConcurrentCollection;

import java.util.concurrent.CountDownLatch;

public class CountdownLatch {

    public static void main(String[] args) throws InterruptedException {
        int numChefs = 3;

        CountDownLatch latch = new CountDownLatch(numChefs);

        /*
            A synchronization aid that allows one or more threads to wait until
            a set of operations being performed in other threads completes.
            A CountDownLatch is initialized with a given count. The await methods
            block until the current count reaches zero due to invocations of the
            countDown method, after which all waiting threads are released and any
            subsequent invocations of await return immediately
        */

        new Thread(new Chef("Chef A", "Pizza", latch)).start();
        new Thread(new Chef("Chef B", "Dosa", latch)).start();
        new Thread(new Chef("Chef C", "Bhature", latch)).start();

        latch.await();

        System.out.println("All the dishes are ready!");
    }

}

class Chef implements Runnable {

    private final String name;
    private final String dish;
    private final CountDownLatch latch;

    public Chef(String name, String dish, CountDownLatch latch) {
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is preparing " + dish);
            Thread.sleep(2000);
            System.out.println(name + " has finished preparing " + dish);
            latch.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*
            If we remove the countDown() method then main thread below
            await() will never run
        */
    }

}

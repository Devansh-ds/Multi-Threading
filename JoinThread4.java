package com.sadds;

public class JoinThread4 {
    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 1 : " + i);
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                System.out.println("Thread 2 : " + i);
            }
        });

        System.out.println("Before executing threads");

        one.start();
        two.start();

        /*
        join(long millis): It will put the current thread on
        wait until the thread on which it is called is dead or
        wait for the specified time (milliseconds)
        join() is same as join(0)
        */

        one.join();
        two.join();

        // it won't be printed at the last.
        /* main method is run be the main thread
        * main method creates definition of other two threads
        * it make these threads in runnable state
        * Now main method still has access to cpu (highest priority is main thread)
        * it prints the below output */
        System.out.println("Done Executing Threads");

    }
}

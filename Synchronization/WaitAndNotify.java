package com.sadds.Synchronization;

public class WaitAndNotify {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        Thread one = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread two = new Thread(() -> {
            try {
                two();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        one.start();
        two.start();
    }

    public static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello in method 1 (one)");
            LOCK.wait();
            /*
               This wait method will stop the execution of this block
               and free the lock, which can be used to enter in the
               two() method.
               Else first this block will completely executed then the
               two() method
            */
            System.out.println("Back again in method 1 (one)");
        }
    }

    public static void two() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello in method 2 (two)");
            LOCK.notify();
            /*
              even calling the notify method, it will still execute
              the current block and then the 'notify' will come into
              the effect.
            */
            System.out.println("Back again in method 2 (two) even after notifying");
        }
    }
}














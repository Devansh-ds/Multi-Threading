package com.sadds.Synchronization;

public class LockWithCustomObjectsSync {

    private static int counter1 = 0;
    private static int counter2 = 0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment1();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment1();
            }
        });

        one.start();
        two.start();

        try {
            one.join();
            System.out.println(two.getState());
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter 1: " + counter1);
        System.out.println("Counter 2: " + counter2);
    }

    private static void increment1() {
        // it is block level sync... argument passed here is an object. It could be 'this' object.
        synchronized (lock1) {
            counter1++;
        }
    }

    private static void increment2() {
        synchronized (lock2) {
            counter2++;
        }
    }
}

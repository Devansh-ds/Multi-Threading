package com.sadds.Basics;

public class ThreadPriority {
    public static void main(String[] args) {

        System.out.println("Thread name: " + Thread.currentThread().getName());
        System.out.println("Priority: " + Thread.currentThread().getPriority());

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        System.out.println("New Priority: " + Thread.currentThread().getPriority());

        Thread one = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("thread 1: " + i);
            }
        });
        one.setPriority(Thread.MAX_PRIORITY);

        Thread two = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Thread two: " + i);
            }
        });
        two.setPriority(Thread.MIN_PRIORITY);

        one.start();
        two.start();

        /*
            The default priority is set to 5 as excepted.
            Minimum priority is set to 1.
            Maximum priority is set to 10.
        */

    }
}






















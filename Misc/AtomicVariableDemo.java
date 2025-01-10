package com.sadds.Misc;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariableDemo {
    private static int count = 0;
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread one = new Thread(() ->{
            for (int i = 0; i < 10000; i++) {
                count++;
                atomicInteger.incrementAndGet();
            }
        });

        Thread two = new Thread(() ->{
            for (int i = 0; i < 10000; i++) {
                count++;
                atomicInteger.incrementAndGet();
            }
        });

        one.start();
        two.start();

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Count : " + count);
        System.out.println("AtomicInteger : " + atomicInteger.get());
    }
}

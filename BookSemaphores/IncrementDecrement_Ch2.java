package com.sadds.BookSemaphores;

public class IncrementDecrement_Ch2 {

    private static Integer counter = 0;
    private static Object LOCK = new Object();
    private static final String str  = "*";

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            try {
                increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                decrement();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();

    }

    private static void increment() throws InterruptedException {
        while (true) {
            synchronized (LOCK) {
                LOCK.notify();
                if (++counter  > 30) {
                    LOCK.wait();
                }
                System.out.println(str.repeat(counter));
                Thread.sleep(100);
            }
        }
    }

    private static void decrement() throws InterruptedException {
        while (true) {
            synchronized (LOCK) {
                LOCK.notify();
                if (--counter < 1) {
                    LOCK.wait();
                } else {
                    System.out.println(str.repeat(counter));
                }
                Thread.sleep(100);
            }
        }
    }

}

package com.sadds.BookSemaphores;

import java.util.concurrent.Semaphore;

public class RendezvousCh3 {

    private static final Semaphore semaphoreOne = new Semaphore(0);
    private static final Semaphore semaphoreTwo = new Semaphore(0);

    public static void main(String[] args) {

        Thread one = new Thread(() -> {
            try {
                System.out.println("a1 statement");
                semaphoreOne.release();
                semaphoreTwo.acquire();
                System.out.println("a2 statement");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two = new Thread(() -> {
            try {
                System.out.println("b1 statement");
                semaphoreTwo.release();
                semaphoreOne.acquire();
                System.out.println("b2 statement");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();
    }

}


package com.sadds.BookSemaphores;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        final SemaphoreDemo obj = new SemaphoreDemo();
        final Semaphore semaphore = new Semaphore(3);
        var numThreads = 99;

        /*
           Here we have created 100 threads in a thread pool and all of them trying to
           acquire permit but at a time only 3 threads can acquire it until then other
           threads are in wait state in hoping those 3 threads release their permit

           Also, we are printing the queue length of threads in wait state every 1 second
        */

        try (final var executor = Executors.newFixedThreadPool(numThreads + 1)) {
            for (int i = 0; i < numThreads; i++) {
                executor.submit(() -> {
                    obj.tryAcquire(semaphore);
                });
            }
            executor.submit(() -> {
                while (true) {
                    Thread.sleep(random.nextInt(1000));
                    System.out.println("Current queue length: " + semaphore.getQueueLength());
                }
            });
        }
    }

    private void tryAcquire(Semaphore semaphore) {
        try {
            semaphore.acquire();
            Thread.sleep(random.nextInt(1000));
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void demo() throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        // permits means no. of thread currently that can access the shared resource simultaneously
        System.out.println(semaphore.availablePermits());

        semaphore.acquire();
        System.out.println(semaphore.availablePermits()); // one thread is acquiring the permit so total permit reduces by 1

        semaphore.release();
        System.out.println(semaphore.availablePermits());
    }

}

















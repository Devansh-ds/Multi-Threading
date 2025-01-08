package com.sadds.BookSemaphores;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

public class SemaphoreServer {

    private static final int numRequests = 1000;

    public static void main(String[] args) {
        final Server server = new Server();
        final Random random = new Random();
        try (var executor = Executors.newFixedThreadPool(numRequests)) {
            for (int i = 0; i < numRequests; i++) {
                executor.submit(() -> {
                    try {
                        while (!server.tryLogin()) {
                            Thread.sleep(random.nextInt(1000));
                        }
                        Thread.sleep(random.nextInt(1000));
                        server.logout();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

    }


}

class Server {

    private static final int numUsers = 100;
    private final Semaphore semaphore;
    private final LongAdder loginAttempts;

    public Server() {
        semaphore = new Semaphore(numUsers);
        new Thread(this::printStatus).start();
        loginAttempts = new LongAdder();
    }

    private void printStatus() {
        while (true) {
            try {
                Thread.sleep(1000);
                int currentUsers = numUsers - semaphore.availablePermits();
                if (currentUsers == 0) {
                    break;
                }
                var result = "Current users: " + currentUsers +
                        ", login attempts: " + loginAttempts;
                System.out.println(result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
        }
        System.out.println("Server has serviced all requests +" +
                "The server handled " + loginAttempts.longValue() + " login attempts.");
    }

    public boolean tryLogin() {
        loginAttempts.increment();
        return semaphore.tryAcquire();
    }

    public void logout() {
        semaphore.release();
    }

}

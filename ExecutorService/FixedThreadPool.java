package com.sadds.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 10; i++) {
                executorService.execute(new Work(i));
            }
        }
        /*
           There is a thread pool which has n number of tasks
           each thread will fetch a task to run from the task queue
           So higher number of threads means faster all the tasks
           will be executed.
        */
    }

}

class Work implements Runnable {
    private final int workId;

    public Work(int workId) {
        this.workId = workId;
    }

    @Override
    public void run() {
        System.out.println("Task id: " + workId + " being executed by " + Thread.currentThread().getName());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

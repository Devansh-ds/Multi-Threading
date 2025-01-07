package com.sadds.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 1000; i++) {
                executor.execute(new TaskOne(i));
            }
        }
         /*
            One task at a time is stored in 'synchronous queue' and if a thread
            is available then it is assigned the task else new Thread is created.
            If a thread is idle for 60sec then it is killed.
         */
    }

}

class TaskOne implements Runnable {

    private final int taskId;

    public TaskOne(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task : " + taskId + " is executed by " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

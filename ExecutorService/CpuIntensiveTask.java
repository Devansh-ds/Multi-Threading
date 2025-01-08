package com.sadds.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CpuIntensiveTask {

    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("No. of Cores: " + cores);
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        System.out.println("Create thread pool with " + cores + " cores");

        /*
           Using more threads than the number of cores can cause wastage of time,
           as each thread will try to get access the cpu cores and changing and
           assigning threads takes some time.
        */

        for (int i = 0; i < 20; i++) {
            executor.execute(new CpuTask());
        }
    }


}

class CpuTask implements Runnable {

    @Override
    public void run() {
        System.out.println("CPU intensive task done by : " + Thread.currentThread().getName());
    }
}

package com.sadds.ExecutorService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorService {
    public static void main(String[] args) {
        try (ScheduledExecutorService service = Executors.newScheduledThreadPool(1)) {
            service.scheduleWithFixedDelay(new ProbeTask(), 1000, 2000, TimeUnit.MILLISECONDS);

            try {
                if (!service.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class ProbeTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Probing end point for updates...");
    }
}

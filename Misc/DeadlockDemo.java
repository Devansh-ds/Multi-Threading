package com.sadds.Misc;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockDemo {

    /*
        jps -l = It lists all the java program running with an id
        jstack <id> = It will show the exact error with the deadlock info
                      at the end.
    */

    private final Lock lockA = new ReentrantLock(true);
    private final Lock lockB = new ReentrantLock(true);

    public void workerOne() {
        lockA.lock();
        System.out.println("Worker 1 acquired lock A");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lockB.lock();
        System.out.println("Worker 1 acquired lock B");

        lockA.unlock();
        lockB.unlock();
    }

    public void workerTwo() {
        lockB.lock();
        System.out.println("Worker 2 acquired lock B");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lockA.lock();
        System.out.println("Worker 2 acquired lock A");

        lockA.unlock();
        lockB.unlock();
    }

    public static void main(String[] args) {
        DeadlockDemo demo = new DeadlockDemo();

        new Thread(demo::workerOne, "Worker 1").start();
        new Thread(demo::workerTwo, "Worker 2").start();

        /*
            Another method for checking deadlocks
        */

        new Thread(() -> {
            ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
            while (true) {
                long[] threadIds = mxBean.findDeadlockedThreads();

                if (threadIds != null) {
                    System.out.println("Deadlocked detected");

                    // ThreadInfo[] threadInfo = mxBean.getThreadInfo(threadIds);

                    for (long threadId : threadIds) {
                        System.out.println("Thread with ID: " + threadId + " is in deadlock");
                    }
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}











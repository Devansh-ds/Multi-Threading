package com.sadds.Locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocksDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    /*
        Here methodA() is acquiring the lock and calling the methodB() which also
        tries to acquire the lock and will cause a deadlock situation normally but

        in Reentrant Lock if the lock is already acquired by a thread and tries to
        acquire it again without unlocking it then it simply returns the lock instead
        of deadlock situation.
    */

    public void methodA() {
        lock.lock();
        try {
            sharedData++;
            System.out.println("Method A: sharedData = " + sharedData);
            methodB();
        } finally {
            lock.unlock();
        }
    }

    private void methodB() {
        lock.lock();
        try {
            sharedData--;
            System.out.println("Method B: sharedData = " + sharedData);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLocksDemo demo = new ReentrantLocksDemo();

        for (int i = 0; i < 5; i++) {
            new Thread(demo::methodA).start();
        }
    }

}












package com.sadds.Locks;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        for (int i = 0; i < 2; i++) {
            Thread readerThread = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    sharedResource.getValue();
                }
            });
            readerThread.setName("ReaderThread " + (i+1));
            readerThread.start();
        }

        Thread writerThread = new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                sharedResource.increment();
            }
        });
        writerThread.setName("WriterThread ");
        writerThread.start();
    }
}

/*
    ReadWriteLock has two types of lock one is readLock and other is writeLock
    multiple threads can acquire readLock simultaneously but only thread can acquire
    writeLock at a time.
    Also, both locks are not acquired together by the threads. So either read operation
    will be performed or the write operations
*/

class SharedResource {
    private int counter = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void increment() {
        lock.writeLock().lock();
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " writes : " + counter);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void getValue() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reads : " + counter);
        } finally {
            lock.readLock().unlock();
        }
    }
}
















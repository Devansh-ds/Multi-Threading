package com.sadds.Locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    /*
        Locks have same working but, it can be used with multiple condition
        Read documentation
    */

    private final Integer maxSize = 5;
    private final Lock lock = new ReentrantLock();
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Condition bufferNotFull = lock.newCondition();
    private final Condition bufferNotEmpty = lock.newCondition();

    private void produce(int item) throws InterruptedException {
        lock.lock();

        try {
            while (buffer.size() == maxSize) {
                bufferNotFull.await();
            }
            buffer.offer(item);
            System.out.println("Produced " + item + " by " + Thread.currentThread().getName());
            bufferNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private void consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                bufferNotEmpty.await();
            }
            System.out.println("Consumed " + buffer.poll() + " by " + Thread.currentThread().getName());
            bufferNotFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();

        Thread producerThread = new Thread(() -> {
            try {
                for(int i = 0; i < 10; i++) {
                    demo.produce(i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumerThread = new Thread(() -> {
           try {
               for(int i = 0; i < 10; i++) {
                   demo.consume();
                   Thread.sleep(2000);
               }
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
        });

        producerThread.start();
        consumerThread.start();
    }
}












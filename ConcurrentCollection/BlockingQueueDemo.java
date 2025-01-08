package com.sadds.ConcurrentCollection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {

    static final int QUEUE_CAPACITY = 10;
    static final BlockingQueue<Integer> taskQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    /*
       BlockingQueue is mainly implemented by:
         ArrayBlockingQueue
         LinkedBlockingQueue
         PriorityBlockingQueue
         DelayQueue
         SynchronousQueue
       Main operations are:
         put(E e)
         take()
         offer(E e)
         poll()
         peek()
    */

    public static void main(String[] args) {

        // Producer Thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    taskQueue.put(i);
                    System.out.println("Task produced : " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e){
                System.out.println(e);
            }
        });

        // Consumer Thread 1
        Thread conumerOne = new Thread(() -> {
            try {
                while (true) {
                    int task = taskQueue.take();
                    processTask(task, "ConsumerOne");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread conumerTwo = new Thread(() -> {
           try {
               int task = taskQueue.take();
               processTask(task, "ConsumerTwo");
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        });

        producer.start();
        conumerOne.start();
        conumerTwo.start();

    }

    private static void processTask(int task, String consumerName) throws InterruptedException {
        System.out.println("Task being processed by " + consumerName + " : " + task);
        Thread.sleep(1000);
        System.out.println("Task completed by " + consumerName + " : " + task);
    }

}


















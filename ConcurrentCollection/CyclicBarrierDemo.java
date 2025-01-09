package com.sadds.ConcurrentCollection;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    private static final int numTourists = 5;
    private static final int numStages = 3;
    /*
        Creates a new CyclicBarrier that will trip when the given number of parties
        (threads) are waiting upon it, and which will execute the given barrier
        action when the barrier is tripped, performed by the last thread entering the
        barrier
    */
    private static final CyclicBarrier barrier = new CyclicBarrier(numTourists, () -> {
                System.out.println("Tour guide starts speaking... as all the tourists(threads) arrived");
            });

    public static void main(String[] args) {
        for (int i = 0; i < numTourists; i++) {
            Thread touristThread = new Thread(new Tourist(i));
            touristThread.start();
        }
    }


    static class Tourist implements Runnable {

        private final int touristId;

        public Tourist(int touristId) {
            this.touristId = touristId;
        }

        @Override
        public void run() {
            for (int i = 0; i < numStages; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Tourist " + touristId + " arrives at stage " + (i + 1));
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

                /*
                    if called await method on the thread then it will wait till all
                    the parties (assigned in the cyclic barrier constructor) reaches
                    means also calls for await method
                    then the barrier action (also passed in the constructor) is performed
                    After that all threads resumes their action
                    if one of them calls await again then the cycle repeats
                */
            }
        }
    }
}











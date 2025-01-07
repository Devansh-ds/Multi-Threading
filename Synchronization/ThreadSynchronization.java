package com.sadds.Synchronization;

public class ThreadSynchronization {

    private static int counter = 0;
    private static int syncCounter1 = 0;
    private static int syncCounter2 = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter++;
                increment1();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter++;
                // increment1();
                increment2();

                /*
                   even though it is calling increment2, it is still blocked by
                   the first thread who is calling method level synchronization
                   method.
                */
            }
        });

        one.start();
        two.start();

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + counter);

        /*
            counter won't be 20000 as both threads will fetch counter from memory
            ,and they won't know that some changes are being made by another thread
            and place their values in memory. (Race Condition) occurs in shared resources
        */

        System.out.println(one.isAlive());
        System.out.println(two.isAlive());

        System.out.println("Sync counter 1 : " + syncCounter1);
        System.out.println("Sync counter 2 : " + syncCounter2);
    }

    /*
      We can use synchronized keyword (here on method level), so that resources is used
      only be one thread at a time.
      Each object in java has an intrinsic/monitored lock. If a thread acquires the lock
      it can use the object but in case of synchronization this lock can be acquired only
      by one thread at a time.
    */

    private synchronized static void increment1() {
        syncCounter1++;
    }

    private synchronized static void increment2() {
        syncCounter2++;
    }
}

package com.sadds.ConcurrentCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedCollection {
    public static void main(String[] args) throws InterruptedException {
        /*
            Normally Collection does not have synchronization
        */
//        List<Integer> list = new ArrayList<Integer>();

        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        /*
            It uses lock mechanism means even threads have different functionality
            they cannot access it together
            (not useful in most cases)
        */

        Thread one = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        one.start();
        two.start();

        one.join();
        two.join();

        System.out.println(list.size());
    }
}

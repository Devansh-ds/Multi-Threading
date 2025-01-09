package com.sadds.ConcurrentCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayDemo {

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.simulate();
    }
}

class Simulation {
    private final List<Integer> list;

    /*  Its like git branching
        when we want to perform read operation it takes a snapshot of the array
        and in case of write operation the array left after the write operation
        is considered as the latest snapshot (version) and further operations
        are performed on the latest version.
        Taking a snapshot means copying whole array and then performing operations on
        it and in case of write we merge the outcome
    */

    public Simulation() {
        list = new CopyOnWriteArrayList<Integer>();
        list.addAll(Arrays.asList(0,0,0,0,0,0,0,0,0));
    }

    public void simulate() {
        Thread one = new Thread(new WriteTask(list));
        Thread two = new Thread(new WriteTask(list));
        Thread three = new Thread(new WriteTask(list));
        Thread four = new Thread(new ReadTask(list));

        one.start();
        two.start();
        three.start();
        four.start();
    }
}

class ReadTask implements Runnable {

    private final List<Integer> list;

    public ReadTask(final List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(list);
        }
    }
}

class WriteTask implements Runnable {

    private List<Integer> list;
    private Random random;

    public WriteTask(final List<Integer> list) {
        this.list = list;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            list.set(random.nextInt(list.size()), random.nextInt(10));
        }
    }


}











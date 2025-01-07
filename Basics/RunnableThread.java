package com.sadds.Basics;

public class RunnableThread2 {
    public static void main(String[] args) {

        // 1. new Thread(class implements Runnable Interface)
        // 2. new class() which extends thread class
        // better approach is using runnable as by using 2nd approach we cannot extend other classes

        Thread one = new Thread(new ThreadOne());
        Thread two = new Thread(new ThreadTwo());
        Thread three = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Thread three : " + i);
                }
            }
        });

        one.start();
        two.start();
        three.start();
    }
}

class ThreadOne implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread One : " + i);
        }
    }
}

class ThreadTwo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread Two : " + i);
        }
    }
}
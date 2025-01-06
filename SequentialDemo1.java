package com.sadds;

public class SequentialDemo1 {
    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        for (int i = 0; i < 10; i++) {
            System.out.println("From demo 1 : " + i);
        }
    }
    private static void demo2() {
        for (int i = 0; i < 10; i++) {
            System.out.println("From demo 2 : " + i);
        }
    }
}
package com.sadds.ForkJoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskDemo {
    public static void main(String[] args) {
        int[] arr = new int[10000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10) + 1;
        }
        int searchElement = random.nextInt(10) + 1;

        try (ForkJoinPool pool =
                     new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            ForkJoinRecursiveTaskDemo task =  new ForkJoinRecursiveTaskDemo(arr, 0, arr.length - 1, searchElement);
            Integer occurence = pool.invoke(task);
            System.out.println("Array is : " + Arrays.toString(arr));
            System.out.printf("%d found %d times", searchElement, occurence);
        }
    }
}

class ForkJoinRecursiveTaskDemo extends RecursiveTask<Integer> {

    int[] arr;
    int start;
    int end;
    int searchElement;

    public ForkJoinRecursiveTaskDemo(int[] arr, int start, int end, int searchElement) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.searchElement = searchElement;
    }

    @Override
    protected Integer compute() {
        int size = (end - start) + 1;
        if (size > 50) {
            int mid = (start + end) / 2;

            /*
                Here we are calling the two tasks recursively using the fork method until
                the recursion reaches its base case which is of size > 50 then using
                join we retrieve all the values and return it by adding them
                its like binary operation but using threads
            */

            ForkJoinRecursiveTaskDemo task1 = new ForkJoinRecursiveTaskDemo(arr, start, mid, searchElement);
            ForkJoinRecursiveTaskDemo task2 = new ForkJoinRecursiveTaskDemo(arr, mid + 1, end, searchElement);

            task1.fork();
            task2.fork();

            return task1.join() + task2.join();

        } else {
            return search();
        }
    }

    public Integer search() {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (arr[i] == searchElement) {
                count++;
            }
        }
        return count;
    }
}

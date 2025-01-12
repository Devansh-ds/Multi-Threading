package com.sadds.PracticeProblems;

import java.util.Random;
import java.util.concurrent.*;

public class MatrixMultiplicationThread {

    private static final int size = 3000;
    private static final Random random = new Random();
    private static byte[][] first = new byte[size][size];
    private static byte[][] second = new byte[size][size];
    private static byte[][] result = new byte[size][size];

    public static void main(String[] args) {

        try (ForkJoinPool forkJoinPool = new ForkJoinPool(12)) {

            long startGenerating = System.currentTimeMillis();
            MatrixGenerator firstMatrix = new MatrixGenerator(0, size, random, size, first);
            MatrixGenerator secondMatrix = new MatrixGenerator(0, size, random, size, second);
            forkJoinPool.invoke(firstMatrix);
            forkJoinPool.invoke(secondMatrix);
            long endGenerating = System.currentTimeMillis();

            double genratingTime = (endGenerating - startGenerating)/1000.0;
            System.out.printf("Generating time: %.5f seconds\n", genratingTime);

            long startCalculating = System.currentTimeMillis();
            MatrixMultiplier resultMatrix = new MatrixMultiplier(first, second, result, 0, size, size);
            forkJoinPool.invoke(resultMatrix);
            long endCalculating = System.currentTimeMillis();

            double resultTime = (endCalculating - startCalculating)/1000.0;
            System.out.printf("Calculating time: %.5f seconds\n", resultTime);

        }

    }
}

class MatrixMultiplier extends RecursiveAction {

    private final byte[][] firstMatrix;
    private final byte[][] secondMatrix;
    private byte[][] resultMatrix;

    private final int start;
    private final int end;
    private final int size;

    public MatrixMultiplier(byte[][] firstMatrix, byte[][] secondMatrix, byte[][] resultMatrix, int start, int end, int size) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.start = start;
        this.end = end;
        this.size = size;
    }

    @Override
    protected void compute() {

        int width = (end - start);

        if (width > 100) {
            int middle = (start + end) / 2;
            MatrixMultiplier multiplier1 = new MatrixMultiplier(firstMatrix, secondMatrix, resultMatrix, start, middle, size);
            MatrixMultiplier multiplier2 = new MatrixMultiplier(firstMatrix, secondMatrix, resultMatrix, middle, end, size);

            invokeAll(multiplier1, multiplier2);
        } else {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        resultMatrix[i][j] += (byte) (firstMatrix[i][k] * secondMatrix[k][j]);
                    }
                }
            }
        }

    }
}



class MatrixGenerator extends RecursiveAction {

    private final int start;
    private final int end;
    private final Random random;
    private final int length;
    private byte[][] resultMatrix;

    public MatrixGenerator(int start, int end, Random random, int length, byte[][] resultMatrix) {
        this.start = start;
        this.end = end;
        this.random = random;
        this.length = length;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void compute() {

        int size = (end - start);

        if (size > 100) {
            int mid = start + (end - start) / 2;

            MatrixGenerator firstHalf = new MatrixGenerator(start, mid, random, length, resultMatrix);
            MatrixGenerator secondHalf = new MatrixGenerator(mid, end, random, length, resultMatrix);

            invokeAll(firstHalf, secondHalf);
        } else {
            generateMatrix(start, end, length);
        }
    }

    public void generateMatrix(int start, int end, int length) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < length; j++) {
                resultMatrix[i][j] = (byte) (ThreadLocalRandom.current().nextInt(3) + 1);

            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(resultMatrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

}



















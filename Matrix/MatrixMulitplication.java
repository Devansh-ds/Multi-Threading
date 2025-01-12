package com.sadds.PracticeProblems;

import java.util.Random;

public class MatrixMulitplication {

    private static int[][] firstMatrix;
    private static int[][] secondMatrix;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        int size = 3000;

        int[][] resultMatrix;
        firstMatrix = new int[size][size];
        secondMatrix = new int[size][size];

        long startGenerating = System.currentTimeMillis();
        generateMatrix(firstMatrix);
        generateMatrix(secondMatrix);
        long endGenerating = System.currentTimeMillis();

        double generatingTime = (endGenerating - startGenerating)/1000.0;
        System.out.printf("Generating time: %.4f seconds\n", generatingTime);

//        displayMatrix(firstMatrix);
//        displayMatrix(secondMatrix);

        long startGeneration = System.currentTimeMillis();
        resultMatrix = multiplyMatrix(firstMatrix, secondMatrix);
        long endGeneration = System.currentTimeMillis();
        double generationTime = (endGeneration - startGeneration)/1000.0;
        System.out.printf("Multiplication time: %.4f seconds\n", generationTime);


//        displayMatrix(resultMatrix);

//        long endTime = System.currentTimeMillis();
//        double timeElapsed = (endTime - startTime) / 1000.0;

//        System.out.printf("Time taken: %.3f seconds\n", timeElapsed);
    }

    private static int[][] multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][secondMatrix[0].length];

        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[0].length; j++) {
                for (int k = 0; k < secondMatrix.length; k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return resultMatrix;
    }

    private static void displayMatrix(int[][] firstMatrix) {
        System.out.println("Displaying matrix");
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[0].length; j++) {
                System.out.print(firstMatrix[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void generateMatrix(int[][] firstMatrix) {
        Random random = new Random();

        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                firstMatrix[i][j] = random.nextInt(3) + 1;
            }
        }

    }

}

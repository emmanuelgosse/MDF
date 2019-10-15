package com.goodclass;

// M = n (n^2 + 1) / 2.
public class CarreMagiquePair {


    // driver program
    public static void main(String[] args) {
        int n = 4;
        double logicMagicNumber = n * (n * n + 1) / 2;
        System.out.println("log " + logicMagicNumber);
        double wantedMagicNumber = 93;

        double add = (wantedMagicNumber - logicMagicNumber) / n;
        double reste = add - Math.floor(add);

        // Function call
        double[][] tab = doublyEven(n, add - reste, reste);

        print(tab);

        double magic = magicNumber(tab);
        System.out.println("magic :" + magic);
    }

    static double[][] doublyEven(int n, double add, double reste) {
        double[][] arr = new double[n][n];
        int i, j;

        // filling matrix with its count value
        // starting from 1;
        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                arr[i][j] = (n * i) + j + 1;

        // change value of Array elements  at fix location as per rule  (n*n+1)-arr[i][j]
        // Top Left corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 0; i < n / 4; i++)
            for (j = 0; j < n / 4; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];

        // Top Right corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 0; i < n / 4; i++)
            for (j = 3 * (n / 4); j < n; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];

        // Bottom Left corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 3 * n / 4; i < n; i++)
            for (j = 0; j < n / 4; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];

        // Bottom Right corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 3 * n / 4; i < n; i++)
            for (j = 3 * n / 4; j < n; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];

        // Centre of Matrix (order (n/2)*(n/2))
        for (i = n / 4; i < 3 * n / 4; i++)
            for (j = n / 4; j < 3 * n / 4; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];


        if (add > 0) {
            for (int k = 0; k < arr.length; k++) {
                for (int l = 0; l < arr.length; l++) {
                    arr[k][l] += add;
                }
            }
        }

        return arr;
    }

    static void print(double[][] arr) {
        // Printing the magic-square
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    static void leveler(double[][] arr) {
        // Printing the magic-square
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    static double magicNumber(double[][] arr) {
        int N = arr.length;
        int magicConst = (N * N * N + N) / 2;

        int rowsum = 0;
        int colsum = 0;
        int diag1 = 0;
        int diag2 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                rowsum += arr[i][j];
                colsum += arr[j][i];
            }
            diag1 += arr[i][i];
            diag2 += arr[i][N - i - 1];
            if (rowsum != magicConst) {
                return -1;
            }
            if (colsum != magicConst) {
                return -1   ;
            }
            rowsum = 0;
            colsum = 0;
        }
        if (diag1 != magicConst || diag2 != magicConst)
            return -1;

        return magicConst;
    }

}

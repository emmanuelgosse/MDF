package com.goodclass;

/**
 * rotate rotateLeft
 * print
 * fleche
 */
public class Matrix {

    public static void main(String[] args) {
        int taille = 8;
        int matrixSize = 13;
        String orientation = "E";

        String[][] fleche = fleche(taille, matrixSize);
        if (orientation.equals("O")) {
            rotateLeft(1, fleche);
        } else if (orientation.equals("S")) {
            rotateLeft(2, fleche);
        } else if (orientation.equals("E")) {
            rotateLeft(3, fleche);
        }

        printTab(fleche);
    }

    static String[][] fleche(int size, int matrixSize) {
        String[][] array = new String[matrixSize][matrixSize];

        if (size > (matrixSize / 2 + 1)) {
            return array;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = ".";
            }
        }

        int middle = matrixSize / 2;
        int length = 0;
        for (int i = 0; i < size; i++) {
            int min = middle - length;
            int max = middle + length;
            for (int j = min; j <= max; j++) {
                array[length][j] = "*";
            }
            length++;
        }

        return array;
    }

    static void rotateLeft(int nbTurn, String[][] mat) {
        int N = mat.length;
        for (int i = 0; i < nbTurn; i++) {
            for (int x = 0; x < N / 2; x++) {
                for (int y = x; y < N - x - 1; y++) {
                    String temp = mat[x][y];
                    mat[x][y] = mat[y][N - 1 - x];
                    mat[y][N - 1 - x] = mat[N - 1 - x][N - 1 - y];
                    mat[N - 1 - x][N - 1 - y] = mat[N - 1 - y][x];
                    mat[N - 1 - y][x] = temp;
                }
            }
        }
    }


    public static void printTab(String[][] tab) {
        for (String[] strings : tab) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.print(strings[j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
}

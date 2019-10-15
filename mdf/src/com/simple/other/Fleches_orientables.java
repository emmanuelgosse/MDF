package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Fleches_orientables {

    public static String NAME = "fleches_orientables";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 2;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
        if (names == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException, ParseException {
        List<String> inputs = getFiles("input");
        List<String> outputs = getFiles("output");

        for (int i = 0; i < inputs.size(); i++) {
            if (id > 0 && !inputs.get(i).contains("" + id)) {
                continue;
            }

            File in = new File(REP + NAME + "\\" + inputs.get(i));
            File out = new File(REP + NAME + "\\" + outputs.get(i));
            Scanner sc = new Scanner(out);
            StringBuilder anwser = new StringBuilder();
            while (sc.hasNextLine()) {
                anwser.append(sc.nextLine())
                        .append(" / ")
                ;
            }

            String computed = function(in);
            System.out.println(NAME + " -> " + in.getName() + " " + out.getName() + " ::: objectif: " + anwser + "  -->  result: " + computed);
            if (anwser.toString().equals(computed)) {
                System.out.println("========== OK");
            } else {
                System.out.println("KO");
            }

            System.out.println();
        }
    }

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        String or = sc.nextLine();

        String[][] aaray = fleche(total);

        if (or.equals("O")) {
            rotateLeft(1, aaray);
        }
        if (or.equals("S")) {
            rotateLeft(2, aaray);
        }
        if (or.equals("E")) {
            rotateLeft(3, aaray);
        }

        printTab(aaray);
        // ===================================================

        return "";

    }

    static void rotateLeft(int nb, String[][] mat) {
        int N = mat.length;
        for (int i = 0; i < nb; i++) {
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

    static String[][] fleche(int size) {
        String[][] array = new String[11][11];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = ".";
            }
        }
        int middle = 11 / 2;

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

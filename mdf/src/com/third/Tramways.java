package com.third;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Tramways {

    public static String NAME = "Tramways";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 1;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP.toLowerCase() + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
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
                anwser.append(sc.nextLine());
                //.append(" / ");
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


    static int[][] pay;
    static int[][] subRes;

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int n = Integer.parseInt(sc.nextLine());

        // Load Array
        pay = new int[n][n];
        subRes = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                pay[i][j] = sc.nextInt();
                subRes[i][j] = -1;
            }
        }
        int sol = getMax(pay, subRes, 0, n - 1);

        printTab(subRes);

        System.out.println("" + sol);


        // ===================================================


        return "" + sol;

    }

    private static int getMax(int[][] pay, int[][] subRes, int min, int max) {
        if (max <= min) {
            return 0;
        }

        if (subRes[min][max] != -1) {
            return subRes[min][max];
        }

        int res = -1;
        for (int i = min; i <= max; i++) {

            int low = getMax(pay, subRes, min + 1, i - 1);
            int high = getMax(pay, subRes, i + 1, max);

            res = Math.max(res, pay[i][min] + low + high);
        }
        subRes[min][max] = res;
        return res;
    }

    public static void printTab(int[][] tab) {
        for (int[] strings : tab) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.print(strings[j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

}

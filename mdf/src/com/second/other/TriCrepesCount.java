package com.second.other;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://fr.wikipedia.org/wiki/Tri_de_cr%C3%AApes
 */
public class TriCrepesCount {

    public static String NAME = "crepes";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

    public static int id = 0;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
        if (names == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException {
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

    public static String function(File input) throws IOException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int[] arr = new int[6];

        for (int i = 0; i < arr.length; i++) {
            arr[arr.length - 1 - i] = sc.nextInt();
        }

        int sol = findRes(arr, 0);

        System.out.println("" + sol);

        // ===================================================

        return "" + sol;
    }

    private static int findRes(int[] arr, int ind) {
        if (findMax(arr) == arr.length + 2) {
            return ind;
        }
        if (ind == arr.length + 1) {
            return ind;
        } else {
            int res = arr.length + 1;
            for (int i = 0; i < arr.length; i++) {
                turn(arr, i);
                res = Math.min(res, findRes(arr, ind + 1));
                turn(arr, i);
            }
            return res;
        }
    }

    private static void turn(int[] arr, int ind) {
        int[] ar2 = new int[arr.length];

        for (int i = ind; i < arr.length; i++) {
            ar2[i] = arr[i];
        }

        for (int i = ind; i < arr.length; i++) {
            arr[i] = ar2[arr.length - 1 - i + ind];
        }
    }

    private static int findMax(int[] arr) {
        int last = 100;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > last) {
                return i;
            }
            last = arr[i];
        }
        return arr.length + 2;
    }
}
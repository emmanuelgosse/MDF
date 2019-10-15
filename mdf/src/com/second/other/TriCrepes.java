package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://fr.wikipedia.org/wiki/Tri_de_cr%C3%AApes
 */
public class TriCrepes {

    public static String NAME = "crepes";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 0;

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

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        // Load Integer list
        int[] arr = new int[6];

        for (int i = 0; i < 6; i++) {
            arr[5 - i] = sc.nextInt();
        }

        pancakeSort(arr, arr.length);

        System.out.println("" + Arrays.stream(arr).mapToObj(x -> ""+ x).collect(Collectors.joining(" ")));

        // ===================================================

        return "" + arr;
    }

    static void pancakeSort(int[] arr, int n) {
        // Start from the complete array and one by one  reduce current size by one
        for (int curr_size = n; curr_size > 1; --curr_size) {
            // Find index of the maximum element in arr[0..curr_size-1]
            int mi = findMax(arr, curr_size);

            // Move the maximum element to end of current array if it's not already at the end
            if (mi != curr_size - 1) {
                // To move at the end, first move maximum  number to beginning
                flip(arr, mi);
                flip(arr, curr_size - 1);

            }
        }
    }

    /* Reverses arr[0..i] */
    static void flip(int[] arr, int i) {
        int temp, start = 0;
        while (start < i) {
            temp = arr[start];
            arr[start] = arr[i];
            arr[i] = temp;
            start++;
            i--;
        }
    }

    /* Returns index of the maximum element in arr[0..n-1] */
    static int findMax(int[] arr, int n) {
        int mi, i;
        for (mi = 0, i = 0; i < n; ++i)
            if (arr[i] > arr[mi])
                mi = i;
        return mi;
    }
}
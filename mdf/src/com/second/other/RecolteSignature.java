package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecolteSignature {

    public static String NAME = "recolte_signature";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 1;

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

        // Load Array
        int row = 0;
        int[][] array = new int[total][total];
        boolean[] choices = new boolean[total];

        while (sc.hasNextLine()) {
            String[] x = sc.nextLine().split(" ");
            choices[row] = false;
            array[row] = Stream.of(x).mapToInt(Integer::parseInt).toArray();
            row++;
        }

        int result = count(array, choices, 0);

        System.out.println("" + result);

        // ===================================================

        return "" + result;

    }

    private static int count(int[][] array, boolean[] choices, int current) {
        boolean updated = false;
        int count = Integer.MIN_VALUE;

        for (int i = 1; i < choices.length; i++) {

            if (!choices[i]) {
                boolean[] newchoices = Arrays.copyOf(choices, choices.length);
                newchoices[i] = true;

                // envoi + delai de i
                int val = array[current][i] + array[i][i];
                // next
                val += count(array, newchoices, i);

                count = Math.max(count, val);
                updated = true;
            }
        }

        if (!updated) {
            count = array[current][0];
        }
        return count;
    }
}

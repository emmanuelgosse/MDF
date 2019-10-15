package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AmisDamis {

    public static String NAME = "amisdamis";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

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
        String[][] tab = new String[total * 2][2];
        while (sc.hasNextLine()) {
            String[] values = sc.nextLine().split(" ");

            tab[row][0] = values[0];
            tab[row][1] = values[1];
            row++;

            tab[row][0] = values[1];
            tab[row][1] = values[0];
            row++;
        }

        int max = -1;
        for (int i = 0; i < tab.length; i++) {
            int local = count(tab, tab[i][0]);
            if (local > max) {
                max = local;
            }
        }

        System.out.println("" + max);

        // ===================================================
        return "" + max;
    }


    static int count(String[][] tab, String key) {
        return Arrays.stream(tab).filter(x -> x[0].equals(key)).map(x -> x[1]).collect(Collectors.toList()).size();
    }
}

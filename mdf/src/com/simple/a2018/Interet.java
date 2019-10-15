package com.simple.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Interet {

    public static String REP = "C:\\tfs\\code\\mdf\\interet\\";

    public static int id = 0;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP).list((dir, name) -> name.toLowerCase().startsWith(key));
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

            File in = new File(REP + inputs.get(i));
            File out = new File(REP + outputs.get(i));
            Scanner sc = new Scanner(out);
            String anwser = sc.nextLine();

            String computed = function(in);
            System.out.println(in.getName() + " " + out.getName() + " ::: objectif: " + anwser + "  -->  result: " + computed);
            if (anwser.equals(computed)) {
                System.out.println("========== OK");
            } else {
                System.out.println("KO");
            }

            System.out.println();
        }
    }

    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int nbjours = Integer.parseInt(sc.nextLine());
        int solde = Integer.parseInt(sc.nextLine());

        int dayNegatif = 0;

        float interet1 = 0;
        float interet2 = 0;

        int countDay = 0;
        while (sc.hasNextLine() && countDay < nbjours) {
            line = sc.nextLine();
            countDay++;

            int current = Integer.parseInt(line);
            solde += current;

            if (solde < 0) {
                dayNegatif++;
            } else {
                dayNegatif = 0;
            }

            if (dayNegatif > 2) {
                interet1 += solde * 0.1f;
            }

            if (dayNegatif > 0) {
                interet2 += solde * 0.2f;
            }
            if (dayNegatif > 3) {
                interet2 += solde * 0.1f;
            }

        }

        int value = Math.round(Math.abs(interet1 - interet2));
        System.out.println(value);

        // ===================================================


        return "" + value;

    }
}

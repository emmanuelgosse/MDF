package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VerouillageMAJ2 {

    public static String NAME = "verouillageMAJ";

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

        int shift_cost = 7;
        int capslock_cost = 13;
        int letter_cost = 10;

        int total = Integer.parseInt(sc.nextLine());

        String mot = sc.nextLine();

        String[] eachLetter = mot.split("");

        int[] MAJ = new int[total + 1];
        Arrays.fill(MAJ, 0);
        MAJ[0] = capslock_cost;

        int[] MIN = new int[total + 1];
        Arrays.fill(MIN, 0);

        for (int i = 0; i < total; i++) {

            if (isLowerCase(eachLetter[i])) {
                MIN[i + 1] += letter_cost + Math.min(MIN[i], MAJ[i] + capslock_cost);
                MAJ[i + 1] += letter_cost + Math.min(MAJ[i] + shift_cost, MIN[i] + capslock_cost);
            } else {
                MIN[i + 1] += letter_cost + Math.min(MIN[i] + shift_cost, MAJ[i] + capslock_cost);
                MAJ[i + 1] += letter_cost + Math.min(MAJ[i], MIN[i] + capslock_cost);
            }
        }

        int result = Math.min(MAJ[total], MIN[total]);

        System.out.println(result);

        // ===================================================
        return "" + result;

    }


    static boolean isLowerCase(String v) {
        return v.equals(v.toLowerCase());
    }
}
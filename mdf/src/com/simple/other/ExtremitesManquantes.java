package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExtremitesManquantes {

    public static String NAME = "extremite_manquante";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 3;

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
            String anwser = sc.nextLine();

            String computed = function(in);
            System.out.println(NAME + " -> " + in.getName() + " " + out.getName() + " ::: objectif: " + anwser + "  -->  result: " + computed);
            if (anwser.equals(computed)) {
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

        long st = System.currentTimeMillis();

        int minLength = Integer.parseInt(sc.nextLine());
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();

        int start = minLength / 2;
        int step = start;
        int last = 0;

        do {
            boolean isFound = generateAllSubsequencesWithSpecificSize(line1, start, line2);

            step = Math.max(1, step / 2);
            System.out.println(start + " step:" + step + " found:" + isFound);

            last = isFound ? start : last;
            start = isFound ? start + step : start - step;

        } while (last != start);

        System.out.println(last);

        long end = System.currentTimeMillis();
        System.out.println((end-st) + " millis");
        // ===================================================
        return "" + start;
    }

    private static boolean generateAllSubsequencesWithSpecificSize(String text, int size, String compare) {
        int text_length = text.length();
        boolean answer = false;
        for (int i = 0; i < text_length; i++) {
            if (i + size > text_length) {
                break;
            }
           if (compare.contains(text.substring(i, i + size))) {
               answer = true;
               break;
           }
        }
        return answer;
    }
}
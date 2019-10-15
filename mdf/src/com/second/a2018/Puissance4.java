package com.second.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Puissance4 {

    public static String REP = "C:\\tfs\\code\\mdf\\puissance4\\";

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

        List<String> list = new ArrayList<>();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            list.add(line);
        }

        for (int i = 0; i < 8; i++) {
            String column = "";
            for (int j = 0; j < 8; j++) {
                column += list.get(j).substring(i, i + 1);
            }
            list.add(column);
        }

        List<String> sequences = new ArrayList<>();
        for (String elt : list) {
            String newline = elt.replace("RJ", "R#J").replace("JR", "J#R");
            String[] ef = newline.split("#");
            sequences.addAll(Arrays.asList(ef));
        }

        int jaune = sequences.stream().filter(x->x.length()>=4 && x.contains("J")).map(x -> x.length()-3)
                .reduce(0, (x,y) -> x+y);

        int rouge = sequences.stream().filter(x->x.length()>=4 && x.contains("R")).map(x -> x.length()-3)
                .reduce(0, (x,y) -> x+y);

//        int jaune = 0;
//        int rouge = 0;
//        for (String ee : sequences) {
//            if (ee.contains("J")) {
//                if (ee.length() >= 4) {
//                    jaune += ee.length() - 3;
//                }
//            }
//
//            if (ee.contains("R")) {
//                if (ee.length() >= 4) {
//                    rouge += ee.length() - 3;
//                }
//            }
//        }

        if (jaune > rouge)
            System.out.println("J");
        else if (jaune < rouge)
            System.out.println("R");
        else
            System.out.println("NOBODY");

        // ===================================================


        if (jaune > rouge)
            return "J";
        else if (jaune < rouge)
            return "R";
        else
            return "NOBODY";

    }
}

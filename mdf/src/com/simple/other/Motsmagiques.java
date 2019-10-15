package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class    Motsmagiques {

    public static String NAME = "motsmagiques";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 4;

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

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);


        // Load Integer list
        Set<String> listInteger = new HashSet<>();
        while (sc.hasNextLine()) {
            String mot = sc.nextLine();

            if (mot.length() < 5 || mot.length() > 7) {
                continue;
            }

            if (mot.charAt(1) != mot.charAt(0) + 1) {
                continue;
            }

            if (! Pattern.matches("[aeiuoy]",  mot.substring(mot.length() - 1))) {
                continue;
            }

            listInteger.add(mot);
        }


        System.out.println(listInteger.size());


        // ===================================================


        return "" + listInteger.size();

    }
}

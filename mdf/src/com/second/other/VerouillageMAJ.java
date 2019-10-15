package com.second.other;

import org.ietf.jgss.GSSContext;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class VerouillageMAJ {

    public static String NAME = "verouillageMAJ";

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

        int total = Integer.parseInt(sc.nextLine());

        String mot = sc.nextLine();

        String[] tab = mot.split("");
        List<String> suite = new ArrayList<>();
        String current = tab[0];
        for (int i = 1; i < tab.length; i++) {
            if (isLowerCase(current) == isLowerCase(tab[i])) {
                current += tab[i];
            } else {
                suite.add(current);
                current = tab[i];
            }
        }
        if (current.length() > 0) {
            suite.add(current);
        }

        int count = 0;
        boolean LOW = true;
        for (String v : suite) {
            if (LOW != isLowerCase(v)) {
                if (v.length() > 3) {
                    count += 13;
                    LOW = !LOW;
                } else {
                    count += v.length() * 7;
                }
            }
            count += v.length() * 10;

        }

        System.out.println(count);

        // ===================================================
        return "" + count;
    }



    static boolean isLowerCase(String v) {
        return v.equals(v.toLowerCase());
    }
}
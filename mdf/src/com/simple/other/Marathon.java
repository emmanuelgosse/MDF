package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Marathon {

    public static String REP = "C:\\tfs\\code\\mdf\\marathon\\";

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

        int start = Integer.parseInt(sc.nextLine());


        int finalplace = start;
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
            finalplace += Integer.parseInt(st.nextToken());
            finalplace -= Integer.parseInt(st.nextToken());

        }

        String montant = "KO";
        if (finalplace <= 100) {
            montant = "" + 1000;
        } else if (finalplace <= 10000) {
            montant = "" + 100;
        }

        System.out.println("" + montant);


        // ===================================================


        return "" + montant;

    }
}

package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Buffet {

    public static String NAME = "buffet";

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

        int montant = Integer.parseInt(sc.nextLine());
        int nbTable = Integer.parseInt(sc.nextLine());


        // Load Integer list
        float vente = 0f;
        while (sc.hasNextLine()) {
            int val = Integer.parseInt(sc.nextLine());

            vente += montant * val;
            if (val >= 4) {
                vente -= 0.1 * val * montant;
            }
            if (val >= 6) {
                vente -= 0.1 * val * montant;
            }
            if (val >= 10) {
                vente -= 0.1 * val * montant;
            }
        }

        System.err.println(vente);
        System.out.println("" + (int) Math.ceil(vente));

        // ===================================================

        return "" + (int) Math.ceil(vente);
    }
}

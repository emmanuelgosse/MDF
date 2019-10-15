package com.simple.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Casino {

    public static String REP = "C:\\tfs\\code\\mdf\\casino\\";

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

        int tours_joues = Integer.parseInt(sc.nextLine());
        int init_jetons = Integer.parseInt(sc.nextLine());
        int jetons = init_jetons;

        int reassort = 0;
        while (sc.hasNextLine()) {

            line = sc.nextLine();

            StringTokenizer st = new StringTokenizer(line, " ");
            int pari =  Integer.parseInt(st.nextToken());

            if (jetons < pari) {
                jetons += init_jetons;
                reassort++;
            }

            String casejouee = st.nextToken();
            String casesortie = st.nextToken();

            if (casejouee.equals("P") && !casesortie.equals("0") &&  (Integer.parseInt(casesortie)%2)==0) {
                jetons += 2 * pari;
            } else if (casejouee.equals("I") &&  (Integer.parseInt(casesortie)%2)==1) {
                jetons += 2 * pari;
            }

            if (casejouee.equals(casesortie)) {
                jetons += 35 * pari;
            } else {
                jetons -= pari;
            }

        }
        if (jetons == 0) {
            jetons += init_jetons;
            reassort++;
        }

        System.out.println(reassort);


        // ===================================================


        return "" + reassort;

    }
}

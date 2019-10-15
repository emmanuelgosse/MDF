package com;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Template {

    public static String NAME = "demineur";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 0;

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
            StringBuilder anwser = new StringBuilder();
            while(sc.hasNextLine()) {
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

    public static String function(File input) throws IOException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);
        int nb_mn = Integer.parseInt(sc.nextLine());
        System.err.println(nb_mn);

        // Load Array
        int row = 0;
        String[][] tab = new String[total][];
        while (sc.hasNextLine()) {
            tab[row++] = sc.nextLine().split("");
        }

        // Load Integer list
        List<Integer> listInteger = new ArrayList<>();
        while (sc.hasNextLine()) {
            listInteger.add(Integer.parseInt(sc.nextLine()));
        }

        // Load StringTokenizer list
        List listObject = new ArrayList();
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            while (st.hasMoreTokens()) {
                Integer.parseInt(st.nextToken());
               // listObject.add();
            }
        }


        System.out.println("");


        // ===================================================


        return "";

    }
}

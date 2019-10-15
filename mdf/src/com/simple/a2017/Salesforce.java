package com.simple.a2017;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Salesforce {

    public static String REP = "C:\\tfs\\code\\mdf\\salesforce\\";

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

        int nbPlaces = Integer.parseInt(sc.nextLine());
        int nbTeams = Integer.parseInt(sc.nextLine());

        List<Integer> lists = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            lists.add(Integer.parseInt(line));
        }
    //    Collections.sort(lists, Collections.reverseOrder());

        int etages = 0;
        int size = lists.size();
        while (size > 0) {
            Integer current = lists.get(0);

            if (current == nbPlaces) {
                etages++;
            } else if (current < nbPlaces) {

                Integer rest = nbPlaces - current;
                int index = lists.lastIndexOf(rest);
                if (index > 0) {
                    lists.remove(index);
                    etages++;
                }
            }
            lists.remove(0);
            size = lists.size();
        }

        System.out.println("" + etages);


        // ===================================================


        return "" + etages;

    }
}

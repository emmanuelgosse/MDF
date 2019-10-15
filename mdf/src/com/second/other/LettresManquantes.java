package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class LettresManquantes {

    public static String NAME = "lettresmanquantes";

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


        // Load Integer list
        List<Set<String>> list = new ArrayList();
        while (sc.hasNextLine()) {
            list.add( generateAllSubstrings(sc.nextLine()));
        }

        Set<String> common = new HashSet<>(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            common.retainAll(list.get(i));
        }

        String longest = "";

        if (common.isEmpty()) {
            longest = "KO";
        } else {
            longest = common.stream().sorted((a, b) -> Integer.compare(b.length(), a.length())).findFirst().get();
        }

        System.out.println(longest);

        // ===================================================
        return longest;
    }

    private static Set<String> generateAllSubstrings(String word) {
        Set<String> set = new HashSet<>();
        generateAllSubstrings(set, word, "", 0);
        set.remove("");
        return set;
    }

    private static void generateAllSubstrings(Set<String> set, String init, String current, int pos) {
        if (pos == init.length()) {
            set.add(current);
            return;
        }

        // Taking the current letter.
        generateAllSubstrings(set, init, current + init.charAt(pos), pos + 1);
        // Or not.
        generateAllSubstrings(set, init, current, pos + 1);
    }

}

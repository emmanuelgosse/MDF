package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Inventaire {

    public static String NAME = "inventaire";

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

        // Load Integer list
        Map<String, Integer> map = new HashMap<>();
        while (sc.hasNextLine()) {
            String[] val = sc.nextLine().split(" ");
            if (map.containsKey(val[0])) {
                map.put(val[0], map.get(val[0]) + Integer.parseInt(val[1]));
            } else {
                map.put(val[0], Integer.parseInt(val[1]));
            }
        }

        int max = map.values().stream().sorted(Comparator.reverseOrder()).findFirst().get();
        List<String> best = map.entrySet().stream().filter(x -> x.getValue().equals(max)).map(x -> x.getKey()).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        String result = String.join(" ", best);

        System.out.println(result);

        // ===================================================

        return result;
    }
}

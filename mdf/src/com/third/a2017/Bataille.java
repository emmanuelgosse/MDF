package com.third.a2017;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Bataille {

    public static String REP = "C:\\tfs\\code\\mdf\\bataille\\";

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

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);


        int id = 0;
        List<Tribu> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            StringTokenizer st = new StringTokenizer(line, " ");

            Tribu tb = new Tribu();
            tb.id = id++;
            tb.combattants = Integer.parseInt(st.nextToken());

            while (st.hasMoreTokens()) {
                tb.tribusId.add(Integer.parseInt(st.nextToken()));
            }
            list.add(tb);
        }

        // Affect children
        for (Tribu tb : list) {
            tb.tribus = list.stream().filter(x -> tb.tribusId.contains(x.id)).collect(Collectors.toList());
        }

        // recursive getNeighbours
        for (Tribu tb : list) {
            tb.fullcombattants = tb.count(new ArrayList<>());
        }

        // find best
        int max = list.stream().map(x -> x.fullcombattants).sorted(Comparator.reverseOrder()).findFirst().get();

        System.out.println("" + max);

        // ===================================================


        return "" + max;

    }

    static class Tribu {
        int id;
        int combattants;
        int fullcombattants;
        List<Integer> tribusId = new ArrayList<>();
        List<Tribu> tribus = new ArrayList<>();


        int count(List<Tribu> tribs) {
            int fullcombatttants = combattants;
            for (Tribu tb : tribus) {
                if (!tribs.contains(tb)) {
                    tribs.add(tb);
                    fullcombatttants += tb.count(tribs);
                }
            }
            return fullcombatttants;
        }
    }
}

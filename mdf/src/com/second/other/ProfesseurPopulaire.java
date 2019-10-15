package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ProfesseurPopulaire {

    public static String NAME = "professeurpopulaire";

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

    static int max = 0;

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());

        // Load StringTokenizer list
        List<Creneau> creneaux = new ArrayList<>();
        int row = 0;
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");

            Creneau c = new Creneau();
            c.id = row;
            c.a = Integer.parseInt(st.nextToken());
            c.b = Integer.parseInt(st.nextToken());
            creneaux.add(c);

            row++;
        }

        max = 0;

        int value = maxCreneau(creneaux, new ArrayList<>(), 0);


        System.out.println("" + value);

        // ===================================================

        return "" + value;
    }

    private static boolean isPossible(List<Integer> list, Integer elt) {
        boolean ok = true;
        for (Integer each : list) {
            if (Math.abs(each - elt) <= 60) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    static int maxCreneau(List<Creneau> c, List<Integer> keep, int count) {
        if (count < c.size()) {
            Creneau ab = c.get(count);
            boolean a_pos = isPossible(keep, ab.a);
            boolean b_pos = isPossible(keep, ab.b);

            if (a_pos) {
                List<Integer> copy = new ArrayList<>(keep);
                copy.add(ab.a);
                maxCreneau(c, copy, count + 1);
            }
            if (b_pos) {
                List<Integer> copy = new ArrayList<>(keep);
                copy.add(ab.b);
                maxCreneau(c, copy, count + 1);
            }

            if (!a_pos && ! b_pos) {
                List<Integer> copy = new ArrayList<>(keep);
                maxCreneau(c, copy, count + 1);
            }
        } else {
            max = Math.max(max, keep.size());
        }

        return max;
    }


    static class Creneau {
        int id;
        int a;
        int b;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Creneau creneau1 = (Creneau) o;
            return id == creneau1.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }


}

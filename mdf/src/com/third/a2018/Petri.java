package com.third.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Petri {

    public static String REP = "C:\\tfs\\code\\mdf\\petri\\";

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

    static class Spot {
        int x;
        int y;

        List<Spot> children = new ArrayList<>();
        List<Spot> challenged = new ArrayList<>();
        List<Spot> nextSteps = new ArrayList<>();

        Spot(int x, int y) {
            this.x = x;
            this.y = y;
            this.children.add(this);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Spot spot = (Spot) o;
            return x == spot.x &&
                    y == spot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public void nextSteps(List<Spot> free) {
            List<Spot> allVoisin = new ArrayList<>();
            for (Spot child : children) {
                allVoisin.addAll(voisin(child));
            }
            for (Spot child : challenged) {
                allVoisin.addAll(voisin(child));
            }
            nextSteps = free.stream().filter(allVoisin::contains).collect(Collectors.toList());
        }


        public static List<Spot> voisin(Spot X) {
            List<Spot> vois = new ArrayList<>();
            vois.add(new Spot(X.x - 1, X.y));
            vois.add(new Spot(X.x, X.y - 1));
            vois.add(new Spot(X.x + 1, X.y));
            vois.add(new Spot(X.x, X.y + 1));
            return vois;
        }
    }

    public static String function(File input) throws IOException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);

        List<Spot> starter = new ArrayList<>();
        List<Spot> free = new ArrayList<>();
        int row = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            for (int i = 0; i < total; i++) {
                String elt = line.substring(i, i + 1);
                Spot s = new Spot(row, i);
                if (!elt.equals(".") && !elt.equals("#")) {
                    starter.add(s);
                } else if (elt.equals(".")) {
                    free.add(s);
                }
            }
            row++;
        }

        int sizeFree = free.size();
        while (true) {

            for (Spot start : starter) {
                start.nextSteps(free);
            }

            for (Spot start : starter) {
                List<Spot> allother = starter.stream().filter(x -> !x.equals(start)).map(x -> x.nextSteps).collect(ArrayList::new, List::addAll, List::addAll);

                List<Spot> goodOne = start.nextSteps.stream().filter(x -> !allother.contains(x)).collect(Collectors.toList());
                start.children.addAll(goodOne);

                List<Spot> eq = start.nextSteps.stream().filter(allother::contains).collect(Collectors.toList());
                start.challenged.addAll(eq);

                free.removeAll(goodOne);
                free.removeAll(eq);
            }

            if (free.isEmpty() || sizeFree == free.size()) {
                break;
            }
            sizeFree = free.size();
        }

        int max = 0;
        for (Spot sp : starter) {
            max = Math.max(max, sp.children.size());
        }

        System.out.println("" + max);
        // ===================================================

        return "" + max;
    }
}
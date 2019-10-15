package com.third.a2018;

import com.goodclass.Position;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Barrage {

    public static String REP = "C:\\tfs\\code\\mdf\\barrage\\";

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

        String[][] tab = new String[total][total];
        int row = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            tab[row++] = line.split("");
        }

        Set<Position> left = new HashSet<>();
        Position.findValuesAroundRecursive(new Position(0, total - 1, "#"), tab, left, List.of("#"), true, true, false);

        Set<Position> right = new HashSet<>();
        Position.findValuesAroundRecursive(new Position(total - 1, 0, "#"), tab, right, List.of("#"), true, true, false);


        double min = Double.MAX_VALUE;
        for (Position posg : left) {
            for (Position posd : right) {
                double carre = (Math.abs(posg.x - posd.x) * Math.abs(posg.x - posd.x)) + (Math.abs(posg.y - posd.y) * Math.abs(posg.y - posd.y));
                double current = Math.sqrt(carre);
                if (current < min) {
                    min = current;
                }
            }
        }


        System.out.println("" + (int) (Math.ceil(min)));


        // ===================================================


        return "" + (int) (Math.ceil(min));

    }

    public static class LocalPosition {
        int x;
        int y;

        public LocalPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocalPosition position = (LocalPosition) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public static Set<LocalPosition> getAllSands(int x, int y, String[][] tab) {
            Set<LocalPosition> sand = new HashSet<>(Set.of(new LocalPosition(x, y)));

            int size = 0;
            while (true) {
                Set<LocalPosition> local = isSand(tab, sand);
                if (size == local.size()) {
                    break;
                }
                size = local.size();
            }

            return sand;
        }

        public static void isSandOnPosition(String[][] tab, Set<LocalPosition> added, Set<LocalPosition> analyzed, LocalPosition current) {
            try {
                if (!analyzed.contains(current)) {
                    if (tab[current.x][current.y].equals("#")) {
                        added.add(current);
                    }
                    analyzed.add(current);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

        public static Set<LocalPosition> isSand(String[][] tab, Set<LocalPosition> sand) {
            LocalPosition current = null;
            Set<LocalPosition> analyzed = new HashSet<>();
            Set<LocalPosition> added = new HashSet<>();

            for (LocalPosition pos : sand) {

                current = new LocalPosition(pos.x - 1, pos.y);
                isSandOnPosition(tab, added, analyzed, current);

                current = new LocalPosition(pos.x, pos.y - 1);
                isSandOnPosition(tab, added, analyzed, current);

                current = new LocalPosition(pos.x + 1, pos.y);
                isSandOnPosition(tab, added, analyzed, current);

                current = new LocalPosition(pos.x, pos.y + 1);
                isSandOnPosition(tab, added, analyzed, current);
            }

            sand.addAll(added);
            return sand;
        }

        @Override
        public String toString() {
            return "LocalPosition{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}

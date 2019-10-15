package com.second.a2017;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Dodécagone {

    public static String REP = "C:\\tfs\\code\\mdf\\dodecagone\\";

    public static int id = 2;

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

        int size = Integer.parseInt(sc.nextLine());

        List<Position> pos = new ArrayList<>();

        pos.add(new Position(0, 0, "."));
        pos.add(new Position(0, size - 1, "."));
        pos.add(new Position(size - 1, 0, "."));
        pos.add(new Position(size - 1, size - 1, "."));


        String symb = "#";
        for (int i = 0; i < size; i++) {
            symb = "#".equals(symb) ? "*" : "#";

            pos.add(new Position(i + 1, size - i - 2, symb));
            pos.add(new Position(i + 1, i + 1, symb));
            pos.add(new Position(size - i - 2, size - i - 2, symb));
            pos.add(new Position(size - i - 2, i + 1, symb));

            for (int j = i; j < size - i; j++) {
                pos.add(new Position(i, j, symb));
                pos.add(new Position(j, i, symb));
                pos.add(new Position(size - i - 1, j, symb));
                pos.add(new Position(j, size - i - 1, symb));
            }
        }

        print(size, pos);

        // ===================================================


        return "";

    }

    static void print(int size, List<Position> pos) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int position = pos.indexOf((new Position(i, j, "")));
                if (position >= 0) {
                    Position po = pos.get(position);
                    System.out.printf(po.symb);
                } else {
                    System.out.printf("-");
                }

            }
            System.out.println(" ");
        }
        System.out.println("");
    }

    static class Position {
        int x;
        int y;
        String symb;

        public Position(int x, int y, String symb) {
            this.x = x;
            this.y = y;
            this.symb = symb;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}

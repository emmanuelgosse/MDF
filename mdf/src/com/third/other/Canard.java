package com.third.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Canard {

    public static String NAME = "canard";

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
        System.err.println(total);

        // Load Array
        int row = 0;
        String[][] tab = new String[total][];
        while (sc.hasNextLine()) {
            tab[row++] = sc.nextLine().split("");
        }

        List<Position> canards = position(tab, "c");
        List<Position> patients = position(tab, "p");
        List<Position> wall = position(tab, "#");
        List<Position> path = position(tab, ".");

        System.out.println("");

        // ===================================================

        return "";
    }


    static List<Position> position(String[][] tab, String sym) {
        List<Position> poss = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                if (tab[i][j].equals(sym)) {
                    poss.add(new Position(i, j, sym));
                }
            }
        }
        return poss;
    }



    public static List<Position> voisin(String[][] tab, int x, int y) {
        List<Position> vois = new ArrayList<>();
        int a = x - 1;
        int b = y;
        if (tab[a][b].equals(".") || tab[a][b].equals("?")) {
            vois.add(new Position(a, b, tab[a][b]));
        }
        a = x;
        b = y - 1;
        if (tab[a][b].equals(".") || tab[a][b].equals("?")) {
            vois.add(new Position(a, b, tab[a][b]));
        }
        a = x + 1;
        b = y;
        if (tab[a][b].equals(".") || tab[a][b].equals("?")) {
            vois.add(new Position(a, b, tab[a][b]));
        }
        a = x;
        b = y + 1;
        if (tab[a][b].equals(".") || tab[a][b].equals("?")) {
            vois.add(new Position(a, b, tab[a][b]));
        }

        return vois;
    }

    static class Position {
        int x, y;
        String value;

        public Position(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    static class Path {
        List<Position> p = new ArrayList<>();
    }
}

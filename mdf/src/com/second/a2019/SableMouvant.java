package com.second.a2019;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SableMouvant {

    public static String NAME = "SableMouvant";

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

    static int X;
    static int Y;

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        String[] size = sc.nextLine().split(" ");

        // Load Array
        int row = 0;
        X = Integer.parseInt(size[0]);
        Y = Integer.parseInt(size[1]);
        String[][] tab = new String[X][Y];
        while (sc.hasNextLine()) {
            tab[row++] = sc.nextLine().split("");
        }

        List<Position> start = positions(tab, ".");

        Set<Position> set = new HashSet<>();
        int level = 0;
        while (true) {
            for (Position p : start) {
                set.addAll(voisin(tab, p.x, p.y, "#", level));
            }

            if (set.isEmpty()) {
                break;
            }
            level++;
            start = new ArrayList<>(set);
            set.clear();
            ;
        }

        System.out.println("" + level);

        // ===================================================

        return "" + level;
    }

    static class Position {
        int x, y;
        String value;

        public Position(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y &&
                    Objects.equals(value, position.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, value);
        }
    }

    static List<Position> positions(String[][] tab, String sym) {
        List<Position> poss = new ArrayList<>();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (tab[i][j].equals(sym)) {
                    poss.add(new Position(i, j, sym));
                }
            }
        }
        return poss;
    }

    public static List<Position> voisin(String[][] tab, int x, int y, String sym, int level) {
        List<Position> vois = new ArrayList<>();
        int a = x - 1;
        int b = y;
        if (a >= 0 && tab[a][b].equals(sym)) {
            tab[a][b] = "" + level;
            vois.add(new Position(a, b, tab[a][b]));
        }
        a = x;
        b = y - 1;
        if (b >= 0 && tab[a][b].equals(sym)) {
            tab[a][b] = "" + level;
            vois.add(new Position(a, b, tab[a][b]));
        }
        a = x + 1;
        b = y;
        if (a < X && tab[a][b].equals(sym)) {
            tab[a][b] = "" + level;
            vois.add(new Position(a, b, tab[a][b]));
        }
        a = x;
        b = y + 1;
        if (b < Y && tab[a][b].equals(sym)) {
            tab[a][b] = "" + level;
            vois.add(new Position(a, b, tab[a][b]));
        }

        return vois;
    }

}

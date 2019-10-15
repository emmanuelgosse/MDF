package com.second.other;


import com.goodclass.SymbolFinder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Tresor {

    public static String NAME = "salleautresor";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 2;

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

        int size = Integer.parseInt(sc.nextLine());

        // Load Array
        int row = 0;
        String[][] tab = new String[size][size];
        while (sc.hasNextLine()) {
            tab[row++] = sc.nextLine().split("");
        }

        SymbolFinder.Position start = new SymbolFinder.Position(0, 0, ".");
        List<SymbolFinder.Position> positions = SymbolFinder.findPositions(tab, List.of("o"), start);

        positions = SymbolFinder.shortestOrderedPositions(positions);

        SymbolFinder.Position restart = positions.get(positions.size() - 1);
        List<SymbolFinder.Position> positionsMultiples = SymbolFinder.findPositions(tab, List.of("*"), restart);
        positionsMultiples = SymbolFinder.shortestOrderedPositions(positionsMultiples);

        positions.addAll(positionsMultiples);

        String msg = "";
        SymbolFinder.Position previous = null;
        for (SymbolFinder.Position one : positions) {
            String localMove = null;
            if (previous != null) {
                localMove = SymbolFinder.Position.move(previous, one);
                msg += localMove;
            }
            previous = one;
            if (localMove != null && !localMove.equals("")) {
                if (one.value.equals("o") || one.value.equals("*")) {
                    msg += "x";
                }
            }
        }

        System.out.println(msg);

        // ===================================================


        return msg;

    }


    public static class Position {
        public int x, y;
        public String value;

        public Position(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public static double dist(Position a, Position b) {
            return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        }

        public static String move(Position a, Position b) {
            String move = "";
            int diffX = a.x - b.x;
            if (diffX > 0) {
                for (int i = 0; i < diffX; i++) {
                    move += "^";
                }
            } else if (diffX < 0) {
                for (int i = 0; i < Math.abs(diffX); i++) {
                    move += "v";
                }
            }
            int diffY = a.y - b.y;
            if (diffY > 0) {
                for (int i = 0; i < diffY; i++) {
                    move += "<";
                }
            } else if (diffY < 0) {
                for (int i = 0; i < Math.abs(diffY); i++) {
                    move += ">";
                }
            }
            return move;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        public static ArrayList<Position> findPositions(String[][] tab, List<String> symbols, Position startPosition) {
            ArrayList<Position> result = new ArrayList<>();
            List<String> symbolsToLowerCase = symbols.stream().map(String::toLowerCase).collect(Collectors.toList());
            int maxI = tab.length;
            int maxY = tab[0].length;
            for (int i = 0; i < maxI; i++) {
                for (int j = 0; j < maxY; j++) {
                    if (symbolsToLowerCase.contains(tab[i][j].toLowerCase())) {
                        result.add(new Position(i, j, tab[i][j]));
                    }
                }
            }

            if (startPosition != null && !result.contains(startPosition)) {
                result.add(0, startPosition);
            }

            return result;
        }
    }


}

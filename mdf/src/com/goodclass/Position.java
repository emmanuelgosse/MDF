package com.goodclass;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Position {
    public int x;
    public int y;
    public String value;

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
                y == position.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static List<Position> findValuesInTab(List<String> values, String[][] tab) {
        List<Position> pos = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if (values.contains(tab[i][j].toLowerCase()) || values.contains(tab[i][j].toUpperCase())) {
                    pos.add(new Position(i, j, tab[i][j]));
                }
            }
        }
        return pos;
    }

    public static long countSymboleAround(Position p, String[][] tab, List<String> symboles, boolean include, boolean straight, boolean diag) {
        Set<Position> aroundX = allPositions(p, tab, include, straight, diag);
        return aroundX.stream().filter(z -> symboles.contains(z.value.toUpperCase()) || symboles.contains(z.value.toLowerCase())).count();
    }

    public static Set<Position> allPositions(Position p, String[][] tab, boolean include, boolean straight, boolean diag) {
        Set<Position> pos = new HashSet<>();
        if (straight) {
            pos.addAll(validStraightPositions(p, tab));
        }
        if (diag) {
            pos.addAll(validDiagPositions(p, tab));
        }

        if (include) {
            pos.add(one(p.x, p.y, tab));
        }
        return pos;
    }

    private static Position one(int x, int y, String[][] tab) {
        return (x >= 0 && x < tab.length && y >= 0 && y < tab[0].length) ? new Position(x, y, tab[x][y]) : null;
    }

    public static Set<Position> validDiagPositions(Position p, String[][] tab) {
        Set<Position> pos = new HashSet<>();
        pos.add(one(p.x - 1, p.y - 1, tab));
        pos.add(one(p.x + 1, p.y - 1, tab));
        pos.add(one(p.x - 1, p.y + 1, tab));
        pos.add(one(p.x + 1, p.y + 1, tab));
        pos.remove(null);
        return pos;
    }

    public static Set<Position> validStraightPositions(Position p, String[][] tab) {
        Set<Position> pos = new HashSet<>();
        pos.add(one(p.x, p.y - 1, tab));
        pos.add(one(p.x, p.y + 1, tab));
        pos.add(one(p.x - 1, p.y, tab));
        pos.add(one(p.x + 1, p.y, tab));
        pos.remove(null);
        return pos;
    }

    public static void findValuesAroundRecursive(Position p, String[][] tab, Set<Position> answers, List<String> symboles, boolean include, boolean straight, boolean diag) {
        if (symboles.contains(p.value.toLowerCase()) | symboles.contains(p.value.toUpperCase())) {
            answers.add(p);
        }
        Set<Position> positions = Position.allPositions(p, tab, include, straight, diag);
        List<Position> free = positions.stream()
                .filter(z -> symboles.contains(z.value.toLowerCase()) || symboles.contains(z.value.toUpperCase()))
                .filter(z -> !answers.contains(z))
                .collect(Collectors.toList());
        for (Position other : free) {
            answers.add(other);
            findValuesAroundRecursive(other, tab, answers, symboles, include, straight, diag);
        }
    }

    public static void getNeighbours(Set<Position> starters, String[][] tab, Set<Position> answers, List<String> goodSymboles, List<String> limitSymboles, boolean include, boolean straight, boolean diag) {
        answers.addAll(starters);

        for (Position p : starters) {
            Set<Position> positions = Position.allPositions(p, tab, include, straight, diag);
            Stream<Position> st = positions.stream();
            if (limitSymboles != null) {
                st = st.filter(z -> !limitSymboles.contains(z.value));
            }
            if (goodSymboles != null) {
                st = st.filter(z -> goodSymboles.contains(z.value));
            }
            answers.addAll(st.collect(Collectors.toSet()));
        }
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.printf(tab[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
}

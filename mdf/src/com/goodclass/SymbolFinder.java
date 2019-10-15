package com.goodclass;

import java.util.*;
import java.util.stream.Collectors;

public class SymbolFinder {

    public static List<Position> findPositions(String[][] tab, List<String> symbols, Position startPosition) {
        List<Position> result = new ArrayList<>();
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

    public static List<Position> shortestOrderedPositions(List<Position> positions) {
        int size = positions.size();
        MSTKruskalGraph k = new MSTKruskalGraph(size, size * (size - 1) / 2);

        int current = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                k.edge[current].src = i;
                k.edge[current].dest = j;
                k.edge[current].weight = Position.dist(positions.get(i), positions.get(j));
                current++;
            }
        }

        MSTKruskalGraph.Edge[] edges = k.kruskalMSTBuild();

        List<MSTKruskalGraph.Edge> listEdges = Arrays.asList(edges);
        Map<Integer, Integer> typeNode = getCross(listEdges);

        orderInternalEdges(0, listEdges);
        listEdges = orderEdges(0, listEdges, typeNode);

        List<Position> results = new ArrayList<>();
        for (MSTKruskalGraph.Edge edge : listEdges) {
            Position p = positions.get(edge.src);
            if (!results.contains(p)) {
                results.add(p);
            }
            p = positions.get(edge.dest);
            if (!results.contains(p)) {
                results.add(p);
            }
        }
        return results;
    }

    private static void orderInternalEdges(int parent, List<MSTKruskalGraph.Edge> all) {
        for (MSTKruskalGraph.Edge one : all) {
            if (one.dest == parent) {
                one.dest = one.src;
                one.src = parent;
            }
            orderInternalEdges(one.dest, all.subList(1, all.size()));
        }
    }


    private static List<MSTKruskalGraph.Edge> orderEdges(int start, List<MSTKruskalGraph.Edge> all, Map<Integer, Integer> typeNode) {
        List<MSTKruskalGraph.Edge> childrenOrdered = new ArrayList<>();
        List<MSTKruskalGraph.Edge> keep = new ArrayList<>();
        List<Integer> cross = new ArrayList<>();
        int next = start;
        boolean first = true;
        for (MSTKruskalGraph.Edge one : all) {
            // Root or leaf
            if ((one.src != 0 && typeNode.get(one.src) <= 2) || (one.src == 0 && typeNode.get(one.src) <= 1)) {
                if (one.src == next) {
                    childrenOrdered.add(one);
                    next = one.dest;
                } else {
                    keep.add(one);
                }
            } else {
                if (first) {
                    childrenOrdered.add(one);
                    next = one.dest;
                    first = false;
                } else {
                    cross.add(one.src);
                    keep.add(one);
                }
            }
        }
        if (!cross.isEmpty()) {
            for (Integer cro : cross) {
                childrenOrdered.addAll(orderEdges(cro, keep, typeNode));
            }

        }
        return childrenOrdered;
    }

    private static Map<Integer, Integer> getCross(List<MSTKruskalGraph.Edge> all) {
        Map<Integer, Integer> typeNode = new HashMap<>();
        for (MSTKruskalGraph.Edge edge : all) {
            Integer id = edge.src;
            Integer value = typeNode.get(id);
            if (value == null) {
                value = 1;
                typeNode.put(id, value);
            } else {
                typeNode.put(id, value + 1);
            }

            id = edge.dest;
            value = typeNode.get(id);
            if (value == null) {
                value = 1;
                typeNode.put(id, value);
            } else {
                typeNode.put(id, value + 1);
            }
        }
        return typeNode;
    }


    public static void main(String[] args) {
        String[][] array = {
                {".", "*", "*", "*", "*"},
                {".", "*", ".", ".", "*"},
                {".", "*", ".", ".", "*"},
                {".", "*", ".", ".", "."},
        };
        Position start = new Position(0, 0, ".");
        List<Position> positions = findPositions(array, List.of("*"), start);
        List<Position> answer = shortestOrderedPositions(positions);
        System.out.println(answer);
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

        public static void printTab(String[][] tab) {
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab[0].length; j++) {
                    System.err.print(tab[i][j]);
                }
                System.err.println("");
            }
            System.err.println("");
        }
    }
}

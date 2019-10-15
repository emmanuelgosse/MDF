package com.second.other;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Tangram {

    public static String NAME = "tangram";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

    public static int id = 0;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
        if (names == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException {
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
                anwser.append(sc.nextLine()).append("#");
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

    public static String function(File input) throws IOException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());

        int size = 10;

        // Load StringTokenizer list
        List<Carre> listObject = new ArrayList<>();
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
            while (st.hasMoreTokens()) {
                Carre c = new Carre();
                c.letter = st.nextToken();
                c.x = Integer.parseInt(st.nextToken());
                c.y = Integer.parseInt(st.nextToken());
                listObject.add(c);
            }
        }

        listObject.sort(Comparator.comparingInt(Carre::aire).reversed());

        int[][] aire = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                aire[i][j] = 0;
            }
        }

        List<Carre> pos = recursive(aire, listObject, new ArrayList<>(), listObject.size());
        String result = pos.stream().sorted(Comparator.comparing(a -> a.letter)).map(x -> x.letter + " " + x.x + " " + x.y).collect(Collectors.joining("#"));
        System.out.println(result);

        // ===================================================

        return result;

    }

    static List<Carre> recursive(int[][] aire, List<Carre> list, List<Carre> pos, int size) {
        if (pos.size() == size) {
            return pos;
        }
        if (list.isEmpty()) {
            return null;
        }
        Carre currentCarre = list.get(0);
        List<Carre> currentPos = findAllPlaces(aire, currentCarre);

        List<Carre> real = null;
        for (Carre position : currentPos) {
            int[][] air_copy = aire.clone();
            affect(air_copy, currentCarre, position);

            position.letter = currentCarre.letter;
            pos.add(position);

            List<Carre> local = recursive(air_copy, list.subList(1, list.size()), new ArrayList<>(pos), size);
            if (local != null) {
                real = local;
                break;
            }
        }
        return real;
    }

    static void affect(int[][] aire, Carre carre, Carre p) {
        for (int i = p.x; i < carre.x + p.x; i++) {
            for (int j = p.y; j < carre.y + p.y; j++) {
                aire[i][j] = 1;
            }
        }
    }

    static List<Carre> findAllPlaces(int[][] aire, Carre carre) {
        List<Carre> carres = new ArrayList<>();
        for (int i = 0; i < aire.length; i++) {
            for (int j = 0; j < aire.length; j++) {
                if (isFit(aire, i, j, carre.x, carre.y)) {
                    carres.add(new Carre(i, j));
                }
            }
        }
        return carres;
    }

    static boolean isFit(int[][] aire, int x, int y, int length, int higth) {
        if (x + length > aire.length || y + higth > aire.length) {
            return false;
        }

        boolean ok = true;
        for (int i = x; i < x + length; i++) {
            for (int j = y; j < y + higth; j++) {
                if (aire[i][j] == 1) {
                    ok = false;
                    break;
                }
            }
            if (!ok) {
                break;
            }
        }
        return ok;
    }


    static class Carre {
        String letter;
        int x;
        int y;

        public Carre() {
        }

        public Carre(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int aire() {
            return x * y;
        }
    }


}

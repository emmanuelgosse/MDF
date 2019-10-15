package com.second.a2017;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Spirale {

    public static String REP = "C:\\tfs\\code\\mdf\\spirale\\";

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

        int size = Integer.parseInt(sc.nextLine());

        int currentX = size / 2;
        int currentY = size / 2;

        List<Position> pos = new ArrayList<>();
        pos.add(new Position(currentX, currentY));
        int row = 1;
        int sens = -1;
        while (currentX >= 0 && currentX <= size+1 && currentY >= 0 && currentY <= size+1) {
            if (row % 2 == 1) {
                for (int i = 1; i <= row; i++) {
                    currentY += sens;
                    pos.add(new Position(currentX, currentY));
                }
                sens *= -1;
            } else {
                for (int i = 1; i <= row; i++) {
                    currentX += sens;
                    pos.add(new Position(currentX, currentY));
                }

            }
            row++;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Position x = new Position(i, j);
                if (pos.contains(x)) {
                    System.out.printf("#");
                } else {
                    System.out.printf("=");
                }

            }
            System.out.println(" ");
        }

        // ===================================================


        return "";

    }

    static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
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

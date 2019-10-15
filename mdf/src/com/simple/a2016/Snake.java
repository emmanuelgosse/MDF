package com.simple.a2016;

import com.goodclass.Position;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Snake {

    public static String NAME = "snake";

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
        int nb_mn = Integer.parseInt(sc.nextLine());

        // Load Integer list
        List<String> list = new ArrayList();
        for (int i = 0; i < nb_mn; i++) {
            list.add(sc.nextLine());
        }

        List<Position> pos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pos.add(new Position(i, 0, ""));
        }

        int x = size - 1;
        int y = 0;
        for (String move : list) {
            if (move.equals("D")) {
                x += 1;
            }
            if (move.equals("G")) {
                x -= 1;
            }
            if (move.equals("H")) {
                y -= 1;
            }
            if (move.equals("B")) {
                y += 1;
            }
            pos.add(new Position(x, y, move));
        }

        // pos.stream().forEach(item -> System.out.println(item));

        Position last = pos.get(pos.size() - size);

        System.out.println(last.x + " " + last.y);
        
        // ===================================================

        return last.x + " " + last.y;

    }
}

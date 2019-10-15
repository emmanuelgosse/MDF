package com.third.other;

import com.goodclass.SymbolFinder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Miroirs {

    public static String NAME = "miroirs";

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

        List<SymbolFinder.Position> pos = SymbolFinder.findPositions(tab, List.of("<"), null);

        SymbolFinder.Position result = pos.get(0);
        result.y += 1;
        StringClass c = new StringClass();
        c.sens = "D";
        while (next(tab, result, c)) {}

        result.x += 1;
        result.y += 1;

        System.out.println(result.x + " " + result.y);

        // ===================================================

        return result.x + " " + result.y;

    }

    static boolean next(String[][] tab, SymbolFinder.Position current, StringClass sens) {
        String value = tab[current.x][current.y];
      //  System.out.println("- " + current.x + " " + current.y + " " + value + " " + sens.sens);
        if (value.equals("#")) {
            return false;
        }

        if (value.equals("<") && sens.sens.equals("D")) {
            current.x = -1;
            current.y = -1;
            return false;
        }

        if (value.equals("/")) {
            if (sens.sens.equals("D")) {
                current.x -= 1;
                sens.sens = "H";
            } else if (sens.sens.equals("G")) {
                current.x += 1;
                sens.sens = "B";
            } else if (sens.sens.equals("H")) {
                current.y += 1;
                sens.sens = "D";
            } else {
                current.y -= 1;
                sens.sens = "G";
            }
        } else if (value.equals("\\")) {
            if (sens.sens.equals("D")) {
                current.x += 1;
                sens.sens = "B";
            } else if (sens.sens.equals("G")) {
                current.x -= 1;
                sens.sens = "H";
            } else if (sens.sens.equals("H")) {
                current.y -= 1;
                sens.sens = "G";
            } else {
                current.y += 1;
                sens.sens = "D";
            }
        } else {
            if (sens.sens.equals("D")) {
                current.y += 1;
            } else if (sens.sens.equals("G")) {
                current.y -= 1;
            } else if (sens.sens.equals("H")) {
                current.x -= 1;
            } else {
                current.x += 1;
            }
        }

        return true;
    }

    static class StringClass {
        String sens;
    }
}

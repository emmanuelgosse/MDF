package com.third.a2017;

import com.goodclass.Position;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * tableau
 */
public class Demineur {

    public static String NAME = "demineur";

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

        int nbLigne = Integer.parseInt(sc.nextLine());
        int nbColonne = Integer.parseInt(sc.nextLine());

        int row = 0;
        String[][] tab = new String[nbLigne][nbColonne];
        while (sc.hasNextLine()) {
            tab[row++] = sc.nextLine().split("");
        }

        // print(tab, nbLigne, nbColonne);
        tab = transformDemineur(tab, List.of("x", "*"));
        // print(tab, nbLigne, nbColonne);

        // Find X
        Position starter = Position.findValuesInTab(List.of("x"), tab).get(0);
        long count = Position.countSymboleAround(starter, tab, List.of("*"), true, true, true);
        if (count > 0) {
            System.out.println("1");
            return "1";

        } else {
            Set<Position> zeros = new HashSet<>(Set.of(starter));
            Position.findValuesAroundRecursive(starter, tab, zeros, List.of("x", "0"), true, true, true);

            Set<Position> allGoodCases = new HashSet<>();
            Position.getNeighbours(zeros, tab, allGoodCases, null, List.of("*"), true, true, true);

            System.out.println("" + allGoodCases.size());
            return "" + allGoodCases.size();
        }


        // ===================================================

    }

    static String[][] transformDemineur(String[][] tab, List<String> exceptions) {
        int nbLigne = tab.length;
        int nbColonne = tab[0].length;
        String[][] newtab = new String[nbLigne][nbColonne];
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                if (exceptions.contains(tab[i][j])) {
                    newtab[i][j] = tab[i][j];
                } else {
                    long count = Position.countSymboleAround(new Position(i, j, ""), tab, List.of("*"), true, true, true);
                    newtab[i][j] = "" + count;
                }
            }
        }
        return newtab;
    }
}
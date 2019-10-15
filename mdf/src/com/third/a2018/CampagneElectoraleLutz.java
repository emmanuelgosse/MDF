package com.third.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * sac a dos
 */
public class CampagneElectoraleLutz {

    public static String REP = "C:\\tfs\\code\\mdf\\campagneelectorale\\";

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

        StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
        int nbAssesseurs = Integer.parseInt(st.nextToken());
        int nbVoixBuy = Integer.parseInt(st.nextToken());

        int[][] t = new int[nbAssesseurs][2];
        int row = 0;
        while (sc.hasNextLine()) {
            st = new StringTokenizer(sc.nextLine(), " ");
            t[row][0] = Integer.parseInt(st.nextToken());
            t[row][1] = Integer.parseInt(st.nextToken());
            row++;
        }


        int sumVoices = Arrays.stream(t).map(x -> x[0]).reduce(0, Integer::sum);
        int ceiling = sumVoices - nbVoixBuy;
        if (ceiling < 0) {
            System.out.println("MANIPULATION");
            return "MANIPULATION";
        }

        int[] best = new int[ceiling + 1];
        for (int i = 0; i < t.length; i++) {
            int votes = t[i][0];
            int price = t[i][1];

            for (int j = ceiling; j > 0; j--) {
                best[j] = Math.max(best[j], j - votes >= 0 ? price + best[j - votes] : 0);
            }
        }

        int sumPrices = Arrays.stream(t).map(x -> x[1]).reduce(0, Integer::sum);
        int bestCeiling = best[ceiling];

        System.out.println("" + (sumPrices - bestCeiling));

        // ===================================================
        return "" + (sumPrices - bestCeiling);

    }
}
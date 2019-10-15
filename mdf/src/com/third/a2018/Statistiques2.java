package com.third.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Statistiques2 {

    public static String REP = "C:\\tfs\\code\\mdf\\statistiques\\";

    public static int id = 1;

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

    /**
     * val lis = Array.fill(n)(1)
     * val lds = Array.fill(n)(1)
     * for (i <- 1 until n) {
     * for (j <- 0 until i) {
     * if (t(j) < t(i)) {
     * lis(i) = math.max(lis(i), 1 + lis(j))
     * }
     * if (t(n-1-j) < t(n-1-i)) {
     * lds(n-1-i) = math.max(lds(n-1-i), 1 + lds(n-1-j))
     * }
     * }
     * }
     * val res = (1 until (n-1)).filter(i => lis(i) >= 2 && lds(i) >= 2)
     * .map(i => lis(i) + lds(i) - 1).max
     * println(s"$res")
     */
    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int n = Integer.parseInt(sc.nextLine());
        System.err.println(n);

        int[] t = new int[n];
        int row = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            t[row++] = Integer.parseInt(line);
        }

        int[] lis = new int[n];
        int[] lds = new int[n];
        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (t[j] < t[i]) {
                    lis[i] = Math.max(lis[i], 1 + lis[j]);
                }
                if (t[n - 1 - j] < t[n - 1 - i]) {
                    lds[n - 1 - i] = Math.max(lds[n - 1 - i], 1 + lds[n - 1 - j]);
                }
            }
        }

        int[] tab = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            tab[i] = i;
        }

        int res = Arrays.stream(tab).filter(i -> lis[i] >= 2 && lds[i] >= 2)
                .map(i -> lis[i] + lds[i] - 1).max().getAsInt();


        System.out.println("" + res);


        // ===================================================


        return "" + res;

    }
}

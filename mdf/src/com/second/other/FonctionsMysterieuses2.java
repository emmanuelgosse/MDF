package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FonctionsMysterieuses2 {

    public static String NAME = "fonctionsmysterieuses2";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 4;

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
        int N = Integer.parseInt(sc.nextLine());
        String ids = sc.nextLine();

        // Load Double list
        List<String> list = Arrays.asList(ids.split(" "));
        List<Integer> listInt = list.stream().map(Integer::parseInt).collect(Collectors.toList());

        int row = Integer.parseInt(sc.nextLine());

        int[] inters = new int[N];
        Arrays.fill(inters, 1);

        for (int i = 0; i < row; i++) {
            int[] current_ints = new int[N];
            Arrays.fill(current_ints, 0);

            for (int j = 1; j < N; j++) {
                int min = Math.min(listInt.get(i), listInt.get(i + 1));
                int max = Math.max(listInt.get(i), listInt.get(i + 1));

                for (int k = min; k <= max; k++) {
                    current_ints[k] += inters[j];
                }
            }
            inters = current_ints;
        }

        int result = inters[(N+1) / 2];
        String r = "" + result;
        if (r.length() >= 3) {
            r = r.substring(r.length() - 3);
        }
        System.out.println(r);

        // ===================================================

        return r;
    }


}

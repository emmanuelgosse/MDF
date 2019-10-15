package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FonctionsMysterieuses {

    public static String NAME = "fonctionsmysterieuses";

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
        int N = Integer.parseInt(sc.nextLine()) / 2;
        String ids = sc.nextLine();

        // Load Double list
        List<String> list = Arrays.asList(ids.split(" "));
        List<Integer> listInt = list.stream().map(Integer::parseInt).collect(Collectors.toList());

        Integer last = listInt.get(0);

        int count = func(listInt, N);

        if (count == 0) {
            System.out.println("INF");
            return "INF";
        }

        System.out.println("" + count);

        // ===================================================

        return "" + count;
    }

    static int func(List<Integer> listInt, int N) {
        Integer last = listInt.get(0);

        int count = 0;
        for (Integer value : listInt) {

            if (last == N && N == value) {
                return 0;
            }

            if (last <= N && N < value) {
                count++;
            }

            if (last >= N && N > value) {
                count++;
            }

            last = value;
        }
        return count;
    }
}

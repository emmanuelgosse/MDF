package com.second.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Bouquet {

    public static String REP = "C:\\tfs\\code\\mdf\\bouquet\\";

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

        String nb = sc.nextLine();
        StringTokenizer st = new StringTokenizer(nb, " ");
        int v = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int j = Integer.parseInt(st.nextToken());
        int o = Integer.parseInt(st.nextToken());

        int v_r = 0;
        int r_r = 0;
        int j_r = 0;
        int o_r = 0;

        int count = 0;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            st = new StringTokenizer(line, " ");
            int v_c = Integer.parseInt(st.nextToken());
            int r_c = Integer.parseInt(st.nextToken());
            int j_c = Integer.parseInt(st.nextToken());
            int o_c = Integer.parseInt(st.nextToken());

            int v_poss = (v_c + v_r) / v;
            int r_poss = (r_c + r_r) / r;
            int j_poss = (j_c + j_r) / j;
            int o_poss = (o_c + o_r) / o;

            int min_bouquet = Math.min(v_poss, r_poss);
            min_bouquet = Math.min(j_poss, min_bouquet);
            min_bouquet = Math.min(o_poss, min_bouquet);

            v_r = Math.min(v_c + v_r - v*min_bouquet, v_c);
            r_r = Math.min(r_c + r_r - r*min_bouquet, r_c);
            j_r = Math.min(j_c + j_r - j*min_bouquet, j_c);
            o_r = Math.min(o_c + o_r - o*min_bouquet, o_c);

            count += min_bouquet;
        }


        System.out.println(count);


        // ===================================================


        return ""+count;

    }
}

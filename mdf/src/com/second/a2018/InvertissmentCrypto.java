package com.second.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class InvertissmentCrypto {

    public static String REP = "C:\\tfs\\code\\mdf\\invest_crypto\\";

    public static int id = 7;

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

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);

        List<Move> list_bit = new ArrayList<>();
        List<Move> list_er = new ArrayList<>();

        Integer first_bit = null;
        Integer second_bit = null;
        Integer third_bit = null;
        Integer first_e = null;
        Integer second_e = null;
        Integer third_e = null;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            System.err.println(line);
            StringTokenizer st = new StringTokenizer(line, " ");

            int bitcoin = Integer.parseInt(st.nextToken());
            third_bit = second_bit;
            second_bit = first_bit;
            first_bit = bitcoin;

            if (first_bit != null && second_bit != null && third_bit != null)
                list_bit.add(new Move(first_bit, second_bit, third_bit));

            int erit = Integer.parseInt(st.nextToken());
            third_e = second_e;
            second_e = first_e;
            first_e = erit;

            if (first_e != null && second_e != null && third_e != null)
                list_er.add(new Move(first_e, second_e, third_e));
        }

        long count_nit = list_bit.stream().filter(x -> x.moving()).count();
        long count_e = list_er.stream().filter(x -> x.moving()).count();


        if (count_nit < count_e) {
            System.out.println("BITCOIN");
        } else if (count_nit > count_e) {
            System.out.println("ETHEREUM");
        } else {
            System.out.println("KO");
        }

        // ===================================================
        if (count_nit < count_e) {
            return "BITCOIN";
        } else if (count_nit > count_e) {
            return "ETHEREUM";
        } else {
            return "KO";
        }
    }

    public static class Move {
        public Integer first;
        public Integer second;
        public Integer third;
        boolean move = false;

        public Move(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;

            move = moving();
        }

        public boolean moving() {
            if (first > second && third > second) {
                return true;
            }
            if (first < second && third < second) {
                return true;
            }

            return false;
        }
    }

}

package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateurNumTel {

    public static String NAME = "generateurNumTel";

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

        List<String> lst;
        if (total == 1) {
            lst = generatorSimple(1000).stream().map(x -> "" + x).collect(Collectors.toList());
        } else {
            String tel = "12345789";
            lst =  new ArrayList<>(permutations(tel, 1000));
        }

        // simple
        String result = lst.stream().map(x -> "06" + x).collect(Collectors.joining(" "));

        System.out.println(result);

        // ===================================================

        return result;
    }

    private static Set<String> permutations(String word, int limit) {
        Set<String> result = new HashSet<>();
        permute(word, 0, word.length() - 1, result, limit);
        return result;
    }

    private static void permute(String str, int l, int r, Set<String> result, int limit) {
        if (result.size() >= limit) {
        } else if (l == r) {
            result.add(str);
        } else {
            for (int i = l; i <= r; i++) {
                str = swap(str, l, i);
                permute(str, l + 1, r, result, limit);
                str = swap(str, l, i);
            }
        }
    }

    private static String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }



    static List<Integer> generatorSimple(int limit) {
        List<Integer> num = new ArrayList<>();
        int row = 0;
        for (int i = 12340000; i < 22340000; i++) {
            if (checkSimple(i, 4)) {
                num.add(i);
                row++;
            }

            if (row >= limit) {
                break;
            }
        }
        return num;
    }

    static boolean checkSimple(int num, long size) {
        long c =  Arrays.stream(("06" + num).split("")).distinct().count();
        return c == size;
    }


}

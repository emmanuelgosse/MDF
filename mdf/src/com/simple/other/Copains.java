package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Copains {

    public static String NAME = "copains";

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

        StringTokenizer st = new StringTokenizer(sc.nextLine());
        Rock my = new Rock();
        for (int i = 0; i < 5; i++) {
            my.lst.add(Integer.parseInt(st.nextToken()));
        }

        int n = Integer.parseInt(sc.nextLine());
        int k = Integer.parseInt(sc.nextLine());

        // Load StringTokenizer list
        List<Rock> listObject = new ArrayList<>();
        while (sc.hasNextLine()) {
            StringTokenizer st2 = new StringTokenizer(sc.nextLine());
            Rock my2 = new Rock();
            for (int i = 0; i < 5; i++) {
                my2.lst.add(Integer.parseInt(st2.nextToken()));
            }
            my2.f = Integer.parseInt(st2.nextToken());
            listObject.add(my2);
        }

        for (Rock each : listObject) {
            for (int i = 0; i < 5; i++) {
                each.dist += Math.abs(my.lst.get(i) - each.lst.get(i));
            }
        }

        listObject = listObject.stream().sorted(Comparator.comparingInt(a -> a.dist)).limit(k).collect(Collectors.toList());

        int moy = listObject.stream().map(x -> x.f).reduce(Integer::sum).get();
        moy = (int) Math.floor(moy / k);

        System.out.println("" + moy);

        // ===================================================
        return "" + moy;

    }

    static class Rock {
        List<Integer> lst = new ArrayList<>();
        int f;
        int dist = 0;
    }
}

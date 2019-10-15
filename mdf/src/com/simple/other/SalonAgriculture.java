package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SalonAgriculture {

    public static String NAME = "salon_agri";

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
            StringBuilder anwser = new StringBuilder();
            while (sc.hasNextLine()) {
                anwser.append(sc.nextLine());
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


        // Load StringTokenizer list
        List<Boeuf> listObject = new ArrayList();
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            Boeuf b = new Boeuf();
            b.name = st.nextToken();
            b.age = Integer.parseInt(st.nextToken());
            b.poid = Integer.parseInt(st.nextToken());
            b.moy = Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());

            listObject.add(b);
        }

        List<Boeuf> best = listObject.stream().filter(x -> x.age >= 2 && x.age <= 5).filter(x -> x.poid >= 1250 && x.poid <= 1500).collect(Collectors.toList());

        int max = best.stream().map((a -> a.moy)).max(Comparator.comparingInt(a -> a)).get();

        best = best.stream().filter(x -> x.moy == max).sorted(Comparator.comparing(a -> a.name)).collect(Collectors.toList());

        String result = best.stream().map(x -> x.name).collect(Collectors.joining(" "));

        System.out.println(result);

        // ===================================================

        return result.trim();

    }

    static class Boeuf {
        String name;
        int age;
        int poid;
        int moy;
    }
}

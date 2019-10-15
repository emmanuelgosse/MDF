package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Makefile {

    public static String NAME = "makefile";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

    public static int id = 3;

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
            while(sc.hasNextLine()) {
                anwser.append(sc.nextLine()).append(" / ");
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

       String original = sc.nextLine();
        int nb_cible = Integer.parseInt(sc.nextLine());

        // Load StringTokenizer list
        List<Cible> listObject = new ArrayList<>();
        while (sc.hasNextLine()) {
            Cible c = new Cible();
            listObject.add(c);

            c.name = sc.nextLine().split(" ")[0];

            StringTokenizer st = new StringTokenizer(sc.nextLine(),  " ");
            while (st.hasMoreTokens()) {
                c.dependances.add(st.nextToken());
            }
        }

        Set<String> modified = new HashSet<>();
        modified.add(original);

        int size  = -1;
        while(modified.size() != size) {
            size = modified.size();

            for (Cible c : listObject) {
                for (String dep : c.dependances) {
                    if (modified.contains(dep)) {
                        modified.add(c.name);
                    }
                }
            }
        }
        modified.remove(original);

        System.out.println(modified.size());
        modified.forEach(System.out::println);

        // ===================================================

        String result = String.join(" / ", modified);
        return modified.size() + " / " + result + " / ";
    }

    static class Cible {
        String name;
        List<String> dependances = new ArrayList<>();
    }
}
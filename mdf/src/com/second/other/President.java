package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class President {

    public static String NAME = "president";

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

        int nbjoueurs = Integer.parseInt(sc.nextLine());
        int nbcartes = Integer.parseInt(sc.nextLine());
        int firstid = Integer.parseInt(sc.nextLine());


        // Load StringTokenizer list
        List<Main> listObject = new ArrayList<>();
        int row = 1;
        while (sc.hasNextLine()) {
            Main m = new Main();
            m.id = row++;
            String[] tab = sc.nextLine().split(" ");
            m.cartes = Arrays.stream(tab).collect(Collectors.toList());

            listObject.add(m);
        }

        List<Main> list = new ArrayList<>();
        int currentId = firstid - 1;
        for (int i = 0; i < listObject.size(); i++) {
            if (currentId == listObject.size()) {
                currentId = 0;
            }
            list.add(listObject.get(currentId++));
        }

        List<Integer> ids = new ArrayList<>();
        do {
            for (int i = 0; i < nbjoueurs; i++) {
                Main current = list.get(i);
                if (current.cartes.isEmpty()) {
                    continue;
                }

                String  current_cartes = current.cartes.get(0);
                int c = 1;
                int size = Math.min(4, current.cartes.size());
                for (int j = 1; j < size; j++) {
                    String carte = current.cartes.get(j);
                    if (current_cartes.equals(carte)) {
                        c++;
                    }
                }

                current.cartes = current.cartes.subList(c, current.cartes.size());
                if (current.cartes.isEmpty()) {
                    ids.add(current.id);
                }
            }

        } while (ids.size() != list.size());

        String result = ids.stream().map(x -> "" + x).collect(Collectors.joining(" "));

        System.out.println(result);

        // ===================================================

        return result;

    }

    static class Main {
        int id;
        List<String> cartes = new ArrayList<>();
    }
}

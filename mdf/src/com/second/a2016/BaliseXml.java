package com.second.a2016;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class BaliseXml {

    public static String NAME = "balise_xml";

    public static String REP = "C:\\tfs\\code\\mdf\\";

    public static int id = 1;

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

        String chaine = sc.nextLine();
        String[] caracters = chaine.split("");

        // Load Integer list
        List<String> list = new ArrayList<>();
        String less = "";
        for (String car : caracters) {
            if (car.equals("-")) {
                less = "-";
                continue;
            }
            list.add(car + less);
            less = "";
        }

        Set<Caracter> caracs = new HashSet<>();
        int level = 1;
        for (String balise : list) {

            level += (balise.length() == 2) ? -1 : 1;

            String b = balise.substring(0, 1);
            if (balise.length() == 2) {
                Optional<Caracter> opt = caracs.stream().filter(x -> x.letter.equals(b)).findFirst();
                Caracter c = opt.orElseGet(Caracter::new);
                c.letter = b;
                c.score += 1d / level;
                caracs.add(c);
            }
        }

        double max = caracs.stream().map(x->x.score).sorted(Comparator.reverseOrder()).findFirst().get();

        String letter = caracs.stream().filter(x->x.score.equals(max)).map(x -> x.letter).sorted().findFirst().get();

        System.out.println(letter);
        // ===================================================


        return letter;

    }

    static class Caracter {
        String letter;
        Double score = 0d;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Caracter caracter = (Caracter) o;
            return Objects.equals(letter, caracter.letter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(letter);
        }
    }
}

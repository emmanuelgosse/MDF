package com.second.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stream, filter, filtre
 *
 */
public class SautPerche {

    public static String REP = "C:\\tfs\\code\\mdf\\sautperche\\";

    public static int id = 0;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP).list((dir, name) -> name.toLowerCase().startsWith(key));
        if (names == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }

    public static class Essai {
        public String name;
        public Float hauteur;
        public Integer nb_try;
        public String success;
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

        List<Essai> tries = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();

            StringTokenizer st = new StringTokenizer(line, " ");
            String name = st.nextToken();
            Float hauteur = Float.parseFloat(st.nextToken());
            String resultat = st.nextToken();

            Optional<Essai>  opt = tries.stream().filter(x -> x.name.equals(name) && x.hauteur.equals(hauteur)).findFirst();
            Essai oldOne = null;
            if (opt.isPresent())
                oldOne = opt.get();

            if (oldOne != null) {
                oldOne.nb_try++;
                oldOne.success = oldOne.success.equals("S") ? "S" : resultat;
            } else {
                oldOne = new Essai();
                oldOne.name = name;
                oldOne.nb_try = 1;
                oldOne.success = resultat;
                oldOne.hauteur = hauteur;
                tries.add(oldOne);

            }
        }

        List<Essai> plus_haut =  tries.stream().filter(x->x.success.equals("S"))
                .sorted((o1,o2)-> o2.hauteur.compareTo(o1.hauteur))
                .collect(Collectors.toList());

        if (! plus_haut.isEmpty()) {
            final Float best_hauteur = plus_haut.get(0).hauteur;

            plus_haut =  plus_haut.stream().filter(x->x.hauteur.equals(best_hauteur))
                    .sorted((o1,o2)-> o2.hauteur.compareTo(o1.hauteur))
                    .sorted((o1,o2)-> o1.nb_try.compareTo(o2.nb_try))
                    .collect(Collectors.toList());

            if (plus_haut.size() == 1) {
                System.out.println(plus_haut.get(0).name);
                return plus_haut.get(0).name;

            } else if (plus_haut.size() > 1)  {
                final int min_nb_try = plus_haut.get(0).nb_try;
                plus_haut = plus_haut.stream().filter(x->(x.nb_try.equals(min_nb_try))).collect(Collectors.toList());

                if (plus_haut.size() == 1) {
                    System.out.println(plus_haut.get(0).name);
                    return plus_haut.get(0).name;
                } else {
                    System.out.println("KO");
                    return "KO";
                }
            }

            System.out.println("KO");
            return "KO";

        }


        System.out.println("KO");
        // ===================================================
return "";
    }
}

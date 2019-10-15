package com.third.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class CampagneElectorale {

    public static String REP = "C:\\tfs\\code\\mdf\\campagneelectorale\\";

    public static int id = 4;

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

        StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
        int nbAssesseurs = Integer.parseInt(st.nextToken());
        int nbVoixBuy = Integer.parseInt(st.nextToken());


        List<Assesseur> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            st = new StringTokenizer(line, " ");
            int nb = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());

            list.add(new Assesseur(nb, price));
        }

        int sum = list.stream().map(x->x.nb).reduce(0, (a, b) -> a + b);
        if (sum <nbVoixBuy) {
            System.out.println("MANIPULATION");
            return "MANIPULATION";
        }

        List<Integer> answer = new ArrayList<>();
        for (Assesseur ass : list) {
            answer.add(recur(nbVoixBuy, 0, 0, ass, list));
        }

        int minPrice = answer.stream().sorted().findFirst().get();

        System.out.println(""  + minPrice);
        return "" + minPrice;


        // ===================================================




    }

    public static int recur(int nbmin, int nb, int price, Assesseur ass, List<Assesseur> sub) {
        if (nb > nbmin) {
            return price;
        }

        int minlocalPrice = Integer.MAX_VALUE;
        List<Assesseur> sub2 = new ArrayList<>(sub);
        sub2.remove(ass);
        for (Assesseur cur : sub2) {
            minlocalPrice = Math.min(minlocalPrice, recur(nbmin, nb+ass.nb, price+ ass.price, cur, sub2));
        }
        return  minlocalPrice;
    }

    public static class Assesseur {
        int nb;
        int price;

        public Assesseur(int nb, int price) {
            this.nb = nb;
            this.price = price;
        }
    }
}

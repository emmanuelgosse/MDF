package com.third.a2018;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Patchenko {

    public static String REP = "C:\\tfs\\code\\mdf\\patchenko\\";

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

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);


        List<Node> parents = new ArrayList<>();
        Node root = new Node();
        root.score = new BigDecimal(1,new MathContext(14));
        parents.add(root);

        List<Node> nodes = new ArrayList<>();

        int level = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            StringTokenizer st = new StringTokenizer(line, " ");
            List<BigDecimal> values = new ArrayList<>();
            while (st.hasMoreTokens()) {
                BigDecimal droite = new BigDecimal(st.nextToken(),new MathContext(14));
                BigDecimal gauche = new BigDecimal(1,new MathContext(14)).subtract(droite);
                values.add(gauche);
                values.add(droite);
            }

            int position = 0;
            for (int i = 0; i < ((values.size() / 2) + 1); i++) {

                Node current = new Node();
                current.level = level;
                current.position = position;
                List<Node> localParents = current.getBestParent(parents);
                if (i == 0 || i == values.size()/2) {
                    Node parent = localParents.get(0);
                    current.score = parent.score.multiply(values.get(position));

                } else {
                    Node parentG = localParents.get(0);
                    BigDecimal valueGauche = parentG.score.multiply(values.get(position));

                    Node parentD = localParents.get(1);
                    BigDecimal valueDroite = parentD.score.multiply(values.get(position));

                    current.score = valueDroite.compareTo(valueGauche) > 0 ? valueDroite : valueGauche;
                }
                nodes.add(current);
                position++;
            }


            parents = new ArrayList<>(nodes);
            nodes.clear();
            level++;
        }

        BigDecimal dd = parents.stream().map(Node::getScore).sorted(Comparator.reverseOrder()).findFirst().get();
        Node best = parents.stream().filter(x->x.getScore().equals(dd)) .findFirst().get();
        int pos = parents.indexOf(best) +1;

        System.out.println("" + pos);


        // ===================================================


        return "" + pos;

    }


    static class Node {
        BigDecimal score;
        int position;
        int level;

        @Override
        public String toString() {
            return "Node{" +
                    "position=" + position +
                    ", level=" + level +
                    ", score=" + score +
                    '}';
        }

        public BigDecimal getScore() {
            return score;
        }

        public List<Node> getBestParent(List<Node> parents) {
            List<Node> par = new ArrayList<>();
            if (position > 0) {
                par.add(parents.get(position - 1));
            }
            if (position < parents.size()) {
                par.add(parents.get(position));
            }
            return par;
        }
    }

// ===================================================


}

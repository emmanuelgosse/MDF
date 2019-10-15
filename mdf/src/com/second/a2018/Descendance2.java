package com.second.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Descendance2 {

    public static String REP = "C:\\tfs\\code\\mdf\\descendance\\";

    public static int id = 0;

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

    public static class Node {
        public String parent;
        public List<String> children = new ArrayList<>();
        public List<String> children2 = new ArrayList<>();

    }

    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);

        Map<String, String> list = new HashMap<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            StringTokenizer st = new StringTokenizer(line, " ");
            String parent = st.nextToken();
            String child = st.nextToken();
            list.put(child, parent);
        }


        Set<String> parents = new HashSet(list.values());
        parents.removeAll(list.keySet());

        List<Node> roots = new ArrayList<>();
        for (String parent : parents) {
            Node root = new Node();
            root.parent = parent;
            roots.add(root);
        }

        for (Node root : roots) {
            root.children.addAll( list.entrySet().stream().filter(x -> x.getValue().equals(root.parent)).map(x -> x.getKey()).collect(Collectors.toList()));
        }

        for (Node root : roots) {
            root.children2.addAll( list.entrySet().stream().filter(x -> root.children.contains(x.getValue())).map(x -> x.getKey()).collect(Collectors.toList()));
        }

        int max = roots.stream().map(x -> x.children2.size()).max(Integer::compareTo).get();

        List<String> b = roots.stream().filter(x -> x.children2.size() == max).map(x->x.parent).sorted().collect(Collectors.toList());

        String commaSeparatedValues = String.join(" ", b);

        System.out.println(commaSeparatedValues);

        // ===================================================


        return commaSeparatedValues;

    }


}

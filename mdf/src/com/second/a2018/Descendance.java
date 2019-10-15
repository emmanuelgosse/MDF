package com.second.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Descendance {

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

    public static class Link {
        public String parent;
        public String child;

        public String display() {
            return parent + " " + child;
        }
    }

    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);

        List<Link> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            StringTokenizer st = new StringTokenizer(line, " ");
            String parent = st.nextToken();
            String child = st.nextToken();
            Link l = new Link();
            l.parent = parent;
            l.child = child;
            list.add(l);
        }


        List<String> deep = new ArrayList<>();
        for (Link sentence : list) {
            boolean added = false;
            for (int i = 0; i < deep.size(); i++) {
                String value = deep.get(i);
                if (value.contains(sentence.parent) && value.contains(sentence.child)) {
                } else if (value.trim().endsWith(sentence.parent)) {
                    String value2 = value.replace(sentence.parent, sentence.display().replaceAll("\\s", " "));
                    deep.add(value2.trim());
                    added = true;
                } else if (value.trim().startsWith(sentence.child)) {
                    String value2 = value.replace(sentence.child, sentence.display().replaceAll("\\s", " "));
                    deep.add(value2.trim());
                    added = true;
                }
            }
            if (!added) {
                deep.add(sentence.display().trim());
            }
        }

        String maxSize = Collections.max(deep, Comparator.comparingInt(String::length));

        deep = deep.stream().filter(x -> x.length() == maxSize.length()).collect(Collectors.toList());
        // filter(x -> x.length() == maxSize).

        Map<String, String> result2 = deep.stream().collect(Collectors.toMap(x -> x, x -> x.substring(0, 4)));

        Map<String, Long> counted = result2.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long maxValue = counted.values().stream().max(Long::compareTo).get();

        List<String> best = counted.entrySet().stream().filter(x -> x.getValue().equals(maxValue)).map(Map.Entry::getKey).collect(Collectors.toList());

        String answer = best.stream().sorted().collect(Collectors.joining(" "));

        System.out.println(answer.trim());


        // ===================================================


        return answer.trim();

    }


}

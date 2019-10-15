package com.third.a2016;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class RezoInformatique2 {

    public static String NAME = "rezo_informatique2";

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

    public static String function(File input) throws IOException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());

        // Load StringTokenizer list
        List<Vertex> vertices = new ArrayList<>();
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            Vertex verX = Vertex.find(x, vertices);
            Vertex verY = Vertex.find(y, vertices);
            verX.vertices.add(verY);
            verY.vertices.add(verX);
        }

        int min = Integer.MAX_VALUE;
        for (Vertex ver : vertices) {
            int currentMin = 0;
            for (Vertex ver2 : ver.vertices) {
                currentMin = Math.max(currentMin, ver2.count(ver));
            }
            min = Math.min(min, currentMin);
        }

        System.out.println("" + min);
        // ===================================================

        return "" + min;

    }

    public static class Vertex {
        public int x;
        public Set<Vertex> vertices = new HashSet<>();

        public Vertex(int x) {
            this.x = x;
        }

        public static Vertex find(int x, List<Vertex> list) {
            int indexVertice = list.indexOf(new Vertex(x));
            Vertex verX = (indexVertice >= 0) ? list.get(indexVertice) : new Vertex(x);
            list.add(verX);
            return verX;
        }

        public int count(Vertex ver) {
            int count = 1;
            for (Vertex v : vertices) {
                if (!v.equals(ver)) {
                    count += v.count(this);
                }
            }
            return count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return x == vertex.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x);
        }
    }

}

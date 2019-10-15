package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ReseauLent {

    public static String NAME = "reseau_lent";

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

        MSTKruskalGraph k = new MSTKruskalGraph(total, total * (total - 1) / 2);

        int i = 0;
        int current = 0;
        while (sc.hasNextLine()) {
            String[] x = sc.nextLine().split(" ");
            for (int j = i + 1; j < x.length; j++) {
                k.edge[current].src = i;
                k.edge[current].dest = j;
                k.edge[current].weight = Integer.parseInt(x[j]);
                current++;
            }
            i++;
        }

        Edge[] edges = k.kruskalMSTBuild("max");

        int result = Arrays.stream(edges).map(x -> x.weight).min(Integer::compareTo).orElse(0);

        System.out.println("" + result);

        // ===================================================

        return "" + result;

    }

    static class Edge implements Comparable<Edge> {
        int src, dest;
        Integer weight;

        public int compareTo(Edge compareEdge) {
            return this.weight.compareTo(compareEdge.weight);
        }

        @Override
        public String toString() {
            return "Edge{" + "src=" + src + ", dest=" + dest + ", weight=" + weight + '}';
        }
    }

    // A class to represent a subset for union-find
    static class Subset {
        int parent, rank;
    }

    static class MSTKruskalGraph {
        int V, E;    // V-> no. of vertices & E->no.of edges
        Edge[] edge; // collection of all edges

        // Creates a graph with V vertices and E edges
        MSTKruskalGraph(int v, int e) {
            V = v;
            E = e;
            edge = new Edge[E];
            for (int i = 0; i < e; ++i) {
                edge[i] = new Edge();
            }
        }

        // A utility function to find set of an element i (uses path compression technique)
        int find(Subset[] subsets, int i) {
            // find root and make root as parent of i (path compression)
            if (subsets[i].parent != i) {
                subsets[i].parent = find(subsets, subsets[i].parent);
            }
            return subsets[i].parent;
        }

        // A function that does union of two sets of x and y (uses union by rank)
        void Union(Subset[] subsets, int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);

            // Attach smaller rank tree under root of high rank tree (Union by Rank)
            if (subsets[xroot].rank < subsets[yroot].rank) {
                subsets[xroot].parent = yroot;
            } else if (subsets[xroot].rank > subsets[yroot].rank) {
                subsets[yroot].parent = xroot;

                // If ranks are same, then make one as root and increment its rank by one
            } else {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }

        // The main function to construct MST using Kruskal's algorithm
        public Edge[] kruskalMSTBuild(String mode) {
            Edge[] result = new Edge[V];  // Tnis will store the resultant MST
            int e = 0;  // An index variable, used for result[]
            for (int i = 0; i < V; ++i) {
                result[i] = new Edge();
            }

            // Step 1:  Sort all the edges in non-decreasing order of their
            // weight.  If we are not allowed to change the given graph, we
            // can create a copy of array of edges
            if (mode.equals("max")) {
                Arrays.sort(edge, Comparator.reverseOrder());
            } else {
                Arrays.sort(edge);
            }

            // Allocate memory for creating V ssubsets
            Subset[] subsets = new Subset[V];
            for (int i = 0; i < V; ++i) {
                subsets[i] = new Subset();
            }

            // Create V subsets with single elements
            for (int v = 0; v < V; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            int i = 0;  // Index used to pick next edge

            // Number of edges to be taken is equal to V-1
            while (e < V - 1) {
                // Step 2: Pick the smallest edge. And increment the index for next iteration
                Edge next_edge = edge[i++];
                int x = find(subsets, next_edge.src);
                int y = find(subsets, next_edge.dest);

                // If including this edge does't cause cycle, include it in result and increment the index of result for next edge
                if (x != y) {
                    result[e++] = next_edge;
                    Union(subsets, x, y);
                }
                // Else discard the next_edge
            }

            return Arrays.stream(result).filter(x -> x.weight != null).collect(Collectors.toList()).toArray(new Edge[]{});
        }

        public double mstkruskalMST(Edge[] edges) {
            return Arrays.stream(edges).map(x -> x.weight).reduce(Integer::sum).orElse(0);
        }

        public void printEdges(Edge[] edges) {
            for (Edge value : edges) {
                System.out.println(value.src + " -- " + value.dest + " == " + value.weight);
            }
        }
    }
}

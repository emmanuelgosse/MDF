package com.goodclass;

import java.util.ArrayList;

/**
 *
 */
public class LongestPathGraph {

    int vertices;
    ArrayList<Integer>[] edge;

    LongestPathGraph(int vertices) {
        this.vertices = vertices;
        edge = new ArrayList[vertices + 1];
        for (int i = 0; i <= vertices; i++) {
            edge[i] = new ArrayList<>();
        }
    }

    void addEdge(int a, int b) {
        edge[a].add(b);
    }

    void dfs(int node, ArrayList<Integer> adj[], int dp[], boolean visited[]) {
        // Mark as visited
        visited[node] = true;

        // Traverse for all its children
        for (int i = 0; i < adj[node].size(); i++) {

            // If not visited
            if (!visited[adj[node].get(i)])
                dfs(adj[node].get(i), adj, dp, visited);

            // Store the max of the paths
            dp[node] = Math.max(dp[node], 1 + dp[adj[node].get(i)]);
        }
    }

    // Function that returns the longest path
    int findLongestPath(int n) {
        ArrayList<Integer> adj[] = edge;
        // Dp array
        int[] dp = new int[n + 1];

        // Visited array to know if the node
        // has been visited previously or not
        boolean[] visited = new boolean[n + 1];

        // Call DFS for every unvisited vertices
        for (int i = 1; i <= n; i++) {
            if (!visited[i])
                dfs(i, adj, dp, visited);
        }

        int ans = 0;

        // Traverse and find the maximum of all dp[i]
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 5;
        LongestPathGraph graph = new LongestPathGraph(n);
        // Example-1
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 2);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        int path = graph.findLongestPath(n);
        System.out.println(path);

    }
}

package Graphs.supplemental;

import java.util.*;
public class Scc {
    
    public static List<List<Integer>> createGraph(int V) {
        List<List<Integer>> adj = new ArrayList<>();

        for(int i=0; i<V; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(2);
        adj.get(0).add(3);
        adj.get(1).add(0);
        adj.get(2).add(1);
        adj.get(3).add(4);

        return adj;
    }

    public static void topSort(List<List<Integer>> adj, int curr, boolean[] vis, Stack<Integer> st) {
        vis[curr] = true;

        for(int i=0; i<adj.get(curr).size(); i++) {
            int dest = adj.get(curr).get(i);
            if(!vis[dest]) {
                topSort(adj, dest, vis, st);
            }
        }
        st.push(curr);
    }

    public static void dfs(List<List<Integer>> adj, int curr, boolean[] vis) {
        vis[curr] = true;
        System.out.print(curr + " ");

        for(int i=0; i<adj.get(curr).size(); i++) {
            int dest = adj.get(curr).get(i);
            if(!vis[dest]) dfs(adj, dest, vis);
        }
    }

    // strongly conn comp
    public static void kosaraju(List<List<Integer>> adj, int V) {
        boolean[] vis = new boolean[V];
        Stack<Integer> st = new Stack<>();

        // 1. topology sort and store in Stack
        for(int i=0; i<V; i++) {
            if(!vis[i]) topSort(adj, i, vis, st);
        }

        // 2. form transpose graph
        List<List<Integer>> tgraph = new ArrayList<>();
        for(int i=0; i<V; i++) tgraph.add(new ArrayList<>());

        for(int i=0; i<adj.size(); i++) {
            for(int j=0; j<adj.get(i).size(); j++) {
                int src = i;
                int dest = adj.get(i).get(j);

                tgraph.get(dest).add(src);
            }
        }

        int cnt = 0;
        // 3. dfs in stack order
        vis = new boolean[V];

        while(!st.isEmpty()) {
            int curr = st.pop();
            if(!vis[curr]) {
                cnt++;
                System.out.println("\n----");
                dfs(tgraph, curr, vis);
            }
        }

        System.out.println("\nnum of Strongly conn components: " + cnt);
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = createGraph(5);
        kosaraju(graph, 5);
    }
}

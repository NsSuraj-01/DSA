package Graphs;

import java.util.*;

public class Part3 {
    static class Edge{
        int src, dest, wt;
        public Edge(int src, int dest, int wt) {
            this.src = src;
            this.dest = dest;
            this.wt = wt;
        }

        public Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }
    }
    
    public static void construct(ArrayList<Edge> graph) {
        graph.add(new Edge(0, 1, 2));
        graph.add(new Edge(0,2,4));
        graph.add(new Edge(1,2,-4));
        graph.add(new Edge(2,3,2));
        graph.add(new Edge(3,4,4));
        graph.add(new Edge(4,1,-1));
    }

    public static void construct1(ArrayList<Edge>[] adj, int v, int[][] input) {
        
        int e = input[0][1];

        for(int i=0; i<v; i++) {
            adj[i] = new ArrayList<>();
        }

        for(int i=1; i<=e; i++) {
            int src = input[i][0], dest = input[i][1];
            adj[i-1].add(new Edge(src, dest));
        }
    } 

    public static void bellmanford(ArrayList<Edge> graph, int src, int V) {
        int[] dist = new int[V];
        for(int i=0; i<V; i++) {
            if(i != src) dist[i] = Integer.MAX_VALUE;
        }

        // time - O(E*V)
        for(int i=0; i<V-1; i++) {
            // all edges - O(E)
            for(int j=0; j<graph.size(); j++) {
                Edge e = graph.get(j);

                // relaxation
                int u = e.src, v = e.dest;
                int wt = e.wt;

                if(dist[u] != Integer.MAX_VALUE && dist[u] + wt < dist[v]) {
                    dist[v] = wt + dist[u];
                }
            }
        }

        for(int i : dist) {
            System.out.print(i+" ");
        }
        System.out.println();
    }


    public static void dfsHelper(ArrayList<Edge>[] adj, int v, int curr, boolean[] vis, ArrayList<Integer> list) {
        vis[curr] = true;
        list.add(curr);

        for(int i=0; i<adj[curr].size(); i++) {
            Edge e = adj[curr].get(i);
            if(!vis[e.dest]) {
                dfsHelper(adj, v, e.dest, vis, list);
            }
        }
    }

    public static void dfs(ArrayList<Edge>[] adj, int v) {
        boolean[] vis = new boolean[v];
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int cnt = 0;

        for(int i=0; i<v; i++) {
            if(!vis[i]) {
                cnt++;
                dfsHelper(adj,v,i,vis,list);
                ans.add(list);
                list = new ArrayList<>();
            }
        }
        ans.add(0, new ArrayList<>(List.of(cnt)));
        System.out.println(ans);
    }

    public static void main(String[] args) {
        // ArrayList<Edge> graph = new ArrayList<>();
        // int V=5;
        // construct(graph);
        // bellmanford(graph, 0, V);

        
        int[][] input = {{7,6},
                        {1,0},
                        {2,0},{3,0},{4,0},{5,0},{6,0}};
        
        int v = input[0][0]; 
        ArrayList<Edge>[] adj = new ArrayList[v];
        construct1(adj, v, input);
        dfs(adj, v);
    }


}

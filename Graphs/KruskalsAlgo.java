package Graphs;

import java.util.*;

public class KruskalsAlgo {

    static class Edge implements Comparable<Edge> {
        int src, dest;
        int cost;
        public Edge(int src, int dest, int cost) {
            this.src = src; this.dest = dest;
            this.cost = cost;
        }
        @Override
        public int compareTo(Edge e) {
            return this.cost - e.cost;
        }
    }
    
    public static void construct_MST(ArrayList<Edge> graph, int v) {

        graph.add(new Edge(0, 1, 10));
        graph.add(new Edge(0,2,15));
        graph.add(new Edge(0,3,30));

        graph.add(new Edge(1,3,40));
        graph.add(new Edge(1, 0, 10));

        graph.add(new Edge(2,0,15));
        graph.add(new Edge(2,3,50));

        graph.add(new Edge(3,0,30));
        graph.add(new Edge(3,1,40));
        graph.add(new Edge(3,2,50));
    }

    // disjoint set ds
    static class DS {
        int par[], rank[];

        public DS(int n) {
            par = new int[n];
            rank = new int[n];
            for(int i=0; i<n; i++) 
                par[i] = i;
        }

        public int find(int x) {
            if(par[x] == x) return x;
            return par[x] = find(par[x]);
        }

        public void union(int a, int b) {
            int parA = find(a);
            int parB = find(b);

            int rA = rank[parA], rB = rank[parB];
            if(rA > rB) {
                par[parB] = parA;
            } else if(rA < rB) {
                par[parA] = parB;
            } else {
                rank[parA]++;
                par[parB] = parA;
            }
        }
    }
    

    public static void kruskalAlgo(ArrayList<Edge> graph, int V) {
        DS ds = new DS(V);

        Collections.sort(graph);
        int min_cost = 0;
        int cnt=0;

        for(int i=0; cnt<V-1; i++) {
            Edge e = graph.get(i);

            int a = ds.find(e.src);
            int b = ds.find(e.dest);

            if(a != b) { // cycle detection
                ds.union(e.src, e.dest);
                min_cost += e.cost;
                cnt++;
            }
        }

        System.out.println("min cost: " + min_cost);
    } 



    public static void main(String[] args) {
        int V = 4;
        ArrayList<Edge> graph = new ArrayList<>();
        construct_MST(graph, V);

        kruskalAlgo(graph, V);
    }
}

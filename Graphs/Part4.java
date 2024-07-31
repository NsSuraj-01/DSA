package Graphs;

import java.util.*;

public class Part4 {

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

    public static void construct_MST(ArrayList<Edge>[] graph, int v) {
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0, 1, 10));
        graph[0].add(new Edge(0,2,15));
        graph[0].add(new Edge(0,3,30));

        graph[1].add(new Edge(1,3,40));
        graph[1].add(new Edge(1, 0, 10));

        graph[2].add(new Edge(2,0,15));
        graph[2].add(new Edge(2,3,50));

        graph[3].add(new Edge(3,0,30));
        graph[3].add(new Edge(3,1,40));
        graph[3].add(new Edge(3,2,50));
    }

    static class Pair implements Comparable<Pair>{
        int v, cost;
        public Pair(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
        @Override
        public int compareTo(Pair o) {
            return this.cost - o.cost;
        }
    }

    // Prims algo
    public static void primsAlgo(ArrayList<Edge>[] graph, int V, int src) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[] vis = new boolean[V];

        pq.add(new Pair(src, 0));
        int totalCost = 0;

        while(!pq.isEmpty()) {
            Pair curr = pq.remove();
            if(!vis[curr.v]) {
                vis[curr.v] = true;
                totalCost += curr.cost;

                for(int i=0; i<graph[curr.v].size(); i++) {
                    Edge e = graph[curr.v].get(i);
                    pq.add(new Pair(e.dest, e.wt));
                }

            }
        }
        System.out.println("Min cost: " + totalCost);
    }

    // disjoint set -> find and union by rank
    static int n = 7;
    static int[] par = new int[n];
    static int[] rank = new int[n];

    public static void init() {
        Arrays.fill(rank, 1);
        for(int i=0; i<n; i++) {
            par[i] = i;
        }
    }

    // time = constant
    public static int find(int x) {
        if(x == par[x]) return x;
        return par[x] = find(par[x]);
    }

    // time = constant
    // if parA == parB -> cycle exists
    public static void union(int a, int b) {
        int parA = find(a);
        int parB = find(b);

        if(rank[parA] > rank[parB]) par[parB] = parA;
        else if(rank[parA] < rank[parB]) {
            par[parA] = parB;
        } else {
            par[parB] = parA;
            rank[parA]++;
        }
    }

    public static void disjointSetDS() {
        init();
        union(1, 3);
        System.out.println(find(3));
        union(2, 4);
        union(3, 6);
        union(1, 4);
        System.out.println(find(3));
        union(1, 5);
        System.out.println(find(4));
    }

    public static void main(String[] args) {
        int V = 4;
        ArrayList<Edge>[] graph = new ArrayList[V];
        construct_MST(graph, V);

        // primsAlgo(graph, V, 0);
        disjointSetDS();
    }
    
}

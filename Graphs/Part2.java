package Graphs;

import java.util.*;

public class Part2 {
    static class Edge {
        int src, dest, wt;
        
        public Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
            this.wt = 0;
        }

        public Edge(int src, int dest, int wt) {
            this.src = src;
            this.dest = dest;
            this.wt = wt;
        }
    }

    

    public static void construct(ArrayList<Edge>[] graph, int V) {

        for(int i=0; i<V; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0,1));
        graph[0].add(new Edge(0,2));

        graph[1].add(new Edge(1,0));
        graph[1].add(new Edge(1,3));

        graph[2].add(new Edge(2,0));
        graph[2].add(new Edge(2,4));

        graph[3].add(new Edge(3,1));
        graph[3].add(new Edge(3,4));
        graph[3].add(new Edge(3,5));

        graph[4].add(new Edge(4,2));
        graph[4].add(new Edge(4,3));
        graph[4].add(new Edge(4,5));

        graph[5].add(new Edge(5,3));
        graph[5].add(new Edge(5,4));
        graph[5].add(new Edge(5,6));

        graph[6].add(new Edge(6,5));
    }

    public static void construct_directed(ArrayList<Edge>[] graph, int V) {
        

        for(int i=0; i<V; i++) {
            graph[i] = new ArrayList<>();
        }

        // graph[0].add(new Edge(0, 3));

        // graph[5].add(new Edge(5,0));
        // graph[5].add(new Edge(5,2));

        // graph[4].add(new Edge(4,0));
        // graph[4].add(new Edge(4,1));

        // graph[2].add(new Edge(2,3));

        // graph[3].add(new Edge(3,1));
        
        graph[0].add(new Edge(0, 1));
        graph[1].add(new Edge(1, 2));
        graph[2].add(new Edge(2,3));
        graph[3].add(new Edge(3,0));
    }

    public static void construct_dijkstras(ArrayList<Edge>[] graph, int V) {
        

        for(int i=0; i<V; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0, 1, 2));
        graph[0].add(new Edge(0, 2, 4));

        graph[1].add(new Edge(1, 2, 1));
        graph[1].add(new Edge(1, 3, 7));

        graph[2].add(new Edge(2,4,3));

        graph[3].add(new Edge(3,5,1));  

        graph[4].add(new Edge(4,3,2));
        graph[4].add(new Edge(4,5,5));          
    }

    
    // case 1 -> vis[neigh] = T & par != neigh -> cycle
    // case 2 -> vis[neigh] = T & par == neigh -> pass
    // case 3 -> vis[neigh] = F -> recusive call

    private static boolean isCycle1(ArrayList<Edge>[] graph, int curr, int par, boolean[] vis) {
        vis[curr] = true;

        for(int i=0; i<graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            // case 3
            if(!vis[e.dest]) {
                if(isCycle1(graph, e.dest, curr, vis) ) return true;
            }
            // case 1
            else if(vis[e.dest] && par != e.dest) return true;
        }
        return false;

    }

    // cycle detection -> undirected graph
    // 1. dfs

    public static void isCycle1(ArrayList<Edge>[] graph, int V) {
        boolean[] vis = new boolean[V];

        for(int i=0; i<V; i++) {
            if(!vis[i]) {
                if(isCycle1(graph, i, -1, vis)) {
                    System.out.println("cycle exists");
                    return;
                }
            }
        }
        System.out.println("no cycle");
    }

    // cycle detection - undirected
    // 2. bfs

    public static void isCycle2(ArrayList<ArrayList<Integer>> adj, int V) {
        for(int i=0; i<V; i++) {
            if(bfsHelper(adj, V, i)) {
                System.out.println("cycle exists");
            }
        }
        System.out.println("no cycle");
    }

    static class Node {
        int node, par;
        public Node(int node, int par) {
            this.node = node; 
            this.par = par;
        }
    }

    public static boolean bfsHelper(ArrayList<ArrayList<Integer>> adj, int V, int src) {
        boolean[] vis = new boolean[V];
        Queue<Node> q = new LinkedList<>();

        q.add(new Node(src, -1));

        while(!q.isEmpty()) {
            Node curr = q.remove();
            int node = curr.node;
            int par = curr.par;

            for(int e : adj.get(node)) {
                if(!vis[e]) {
                    q.add(new Node(e, node));
                    vis[e] = true;
                } else if(par != e) return true;
            }
        }
        return false;

    }

    private static boolean isCycle_directed(ArrayList<Edge>[] graph, int src, boolean[] vis, boolean[] st) {
        vis[src] = true;
        st[src] = true;

        for(int i=0; i<graph[src].size(); i++) {
            Edge e = graph[src].get(i);
            if(st[e.dest]) return true;
            if(!vis[e.dest]) {
                if(isCycle_directed(graph, e.dest, vis, st)) return true;
            }
        }
        st[src] = false;
        return false;

    }

    // is cycle - directed graph
    public static void isCycle_directed(ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[graph.length];
        boolean[] st = new boolean[graph.length];

        for(int i=0; i<graph.length; i++) {
            if(!vis[i]) {
                if(isCycle_directed(graph,i,vis,st)) {
                    System.out.println("cycle exists");
                    return;
                }
            }
        }
        System.out.println("no cycle");
    }

    public static int find(int x, int[] par, int[] rank) {
        if(par[x] == x) return x;
        return par[x] = find(par[x], par, rank);
    }

    public static void union(int a, int b, int[] par, int[] rank) {
        int parA = find(a,par,rank);
        int parB = find(b,par,rank);
        
        if(rank[parA] > rank[parB]) par[parB] = parA;
        else if(rank[parA] < rank[parB]) {
            par[parA] = parB;
        } else {
            par[parB] = parA;
            rank[parA]++;
        }
    }

    public static boolean cycleDetection(ArrayList<Edge>[] graph, int V) {
        int[] par = new int[V];
        int[] rank = new int[V];

        Arrays.fill(rank, 1);
        for(int i=0; i<V; i++) par[i] = i;

        for(int i=0; i<V; i++) {
            for(int j=0; j<graph[i].size(); j++) {
                Edge e = graph[i].get(j);

                int a = find(e.src, par, rank);
                int b = find(e.dest, par, rank);

                if(a == b) return true;

                union(e.src, e.dest, par, rank);
            }
        }
        return false;
    } 


    public static boolean graphColoring(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        int[] color = new int[graph.length];
        Arrays.fill(color, -1);

        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        color[src] = 0;

        while(!q.isEmpty()) {
            int curr = q.remove();
            if(!vis[curr]) {
                vis[curr] = true;

                for(int i=0; i<graph[curr].size(); i++) {
                    Edge e = graph[curr].get(i);
                    if(color[e.dest] == -1) {
                        color[e.dest] = 1 - color[curr];
                        q.add(e.dest);
                    } else if(color[e.dest] == color[curr]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void bipartite1(ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[graph.length];

        for(int i=0; i<graph.length; i++) {
            if(!vis[i]) {
                if(!graphColoring(graph,i, vis)) {
                    System.out.println("not bipartite graph");
                    return;
                }
            }
        }
        System.out.println("bipartite graph");
    }

    // bipartite2
    // acyclic -> true
    // even len cycle -> true
    // odd len cycle -> false

    private static void topologicalSort(ArrayList<Edge>[] graph, int curr, boolean[] vis, Stack<Integer> st) {
        vis[curr] = true;

        for(int i=0; i<graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            if(!vis[e.dest]) {
                topologicalSort(graph,e.dest,vis,st);
            }
        }
        st.add(curr);
    }

    // topological sort
    public static void topologicalSort(ArrayList<Edge>[] graph) {
        Stack<Integer> st = new Stack<>();
        boolean[] vis = new boolean[graph.length];

        for(int i=0; i<graph.length; i++) {
            if(!vis[i]) {
                topologicalSort(graph,i,vis,st);
            }
        }
        System.out.print("topological sort: ");
        while(!st.isEmpty()) {
            System.out.print(st.pop() + " ");
        }
        System.out.println();
    }

    public static void calcIndegree(ArrayList<Edge>[] graph, int[] indeg) {

        for(int i=0; i<graph.length; i++) {
            for(int j=0; j<graph[i].size(); j++) {
                Edge e = graph[i].get(j);
                indeg[e.dest]++;    
            }
        }
    }

    // topological sort - indegree (Kahn's Algo)
    public static void topoSort(ArrayList<Edge>[] graph) {
        int n = graph.length;

        int[] indeg = new int[n];
        Queue<Integer> q = new LinkedList<>(); 
        ArrayList<Integer> ans = new ArrayList<>();

        calcIndegree(graph, indeg);

        for(int i=0; i<n; i++) {
            if(indeg[i] == 0) q.add(i);
        }

        while(!q.isEmpty()) {
            int curr = q.remove();
            ans.add(curr);

            for(int i=0; i<graph[curr].size(); i++) {
                Edge e = graph[curr].get(i);
                indeg[e.dest]-- ;

                if(indeg[e.dest] == 0) q.add(e.dest);
            }
        }

        System.out.println(ans);
    }

    private static void allPaths(ArrayList<Edge>[] graph, int src, int dest, String ans) {
        
        if(src == dest) {
            ans += src;
            System.out.println(ans);
            return;
        }

        for(int i=0; i<graph[src].size(); i++) {
            Edge e = graph[src].get(i);
            allPaths(graph, e.dest, dest, ans+src);
        }
    } 

    // all paths from src to dest
    public static void allPaths(ArrayList<Edge>[] graph, int src, int dest) {
        allPaths(graph, src, dest, "");
    }

    static class Pair implements Comparable<Pair> {
        int dest, dist;
        public Pair(int dest, int dist) {
            this.dest = dest;
            this.dist = dist;
        }
        @Override
        public int compareTo(Pair o2) {
            return this.dist - o2.dist;
        }
    }

    // single src shortest path
    public static void dijkstraAlgo(ArrayList<Edge>[] g, int src, int dest) {
        int V = g.length;
        int[] dist = new int[V];

        for(int i=0; i<V; i++) {
            if(i!=src) dist[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[] vis = new boolean[V];

        pq.add(new Pair(src, 0));

        while(!pq.isEmpty()) {
            Pair curr = pq.remove();
            
            if(!vis[curr.dest]) {
                vis[curr.dest] = true;
                for(int i=0; i<g[curr.dest].size(); i++) {
                    Edge e = g[curr.dest].get(i);

                    int u = e.src;
                    int v = e.dest;
                    int wt = e.wt;

                    if(dist[v] > dist[u] + wt) {
                        dist[v] = dist[u] + wt;
                    }

                    pq.add(new Pair(v,dist[v]));
                }
            }

        }

        int j=0;
        for(int i : dist) {
            System.out.println("src -> " + (j++) + " : "+ i);
        }
    }

    public static void dfs(ArrayList<Edge>[] graph, int curr, boolean[] vis) {
        vis[curr] = true;
        System.out.print(curr + " ");

        for(int i=0; i<graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            if(!vis[e.dest]) dfs(graph, e.dest, vis);
        }
    }

    public static void traverse(ArrayList<Edge>[] graph, int V) {
        boolean[] vis = new boolean[V];

        for(int i=0; i<V; i++) {
            if(!vis[i]) { 
                // bfs(graph, vis);
                dfs(graph, i, vis);
            }
        }
    }

    public static void main(String[] args) {
        int V=4;
        ArrayList<Edge>[] g = new ArrayList[7];
        // construct_dijkstras(g,V);
        construct(g, 7);
        // construct_directed(g, V);
        // traverse(g, V);
        // isCycle_directed(g);
        // bipartite1(g);
        // topologicalSort(g);
        // topoSort(g);
        // allPaths(g, 5, 1);
        // dijkstraAlgo(g, 0, 5);

        // isCycle1(g, 7);
        // System.out.println(cycleDetection(g, 7));
        
    }
}

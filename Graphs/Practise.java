package Graphs;

import java.util.*;

public class Practise {

    static class Edge {
        int src, dest;
        public Edge(int src, int dest) {
            this.src=src; 
            this.dest = dest;
        }
    }

    public static void construct(ArrayList<Edge>[] graph, int[][] roads, int n) {
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(i != j && roads[i][j] == 1) {
                    graph[i].add(new Edge(i, j));
                }
            }
        }
    }

    public static void dfsHelper(ArrayList<Edge>[] graph, int curr, boolean[] vis) {
        vis[curr] = true;
        System.out.print(curr + " ");

        for(int i=0; i<graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            if(!vis[e.dest]) {
                dfsHelper(graph, e.dest, vis);
            }
        }
    }

    public static void dfs(ArrayList<Edge>[] graph, int n) {
        boolean[] vis = new boolean[n];
        for(int i=0; i<n; i++) {
            if(!vis[i]) {
                dfsHelper(graph, i, vis);
            }
        }
    }

    // number of connected components
    public static void connComponents(ArrayList<Edge>[] graph, int n) {
        boolean[] vis = new boolean[n];
        int count = 0;

        for(int i=0; i<n; i++) {
            if(!vis[i]) {
                count++;
                dfsHelper(graph, i, vis);
            }
        }
        System.out.println(count);
    }

    public static void findNumOfProvinces(int[][] roads, int n) {
        // Write your code here.
        ArrayList<Edge>[] graph = new ArrayList[n];
        construct(graph, roads, n);
        // dfs(graph, n);
        connComponents(graph, n);
    }

    public static void construct1(List<List<Integer>> edges, ArrayList<Edge>[] g, int v) {
        for(int i=0; i<v; i++) {
            g[i] = new ArrayList<>();
        }

        for(int i=0; i<v; i++) {
            for(int j=0; j<v; j++) {
                if(i != j && edges.get(i).get(j) == 1) {
                    g[i].add(new Edge(i, j));
                    g[j].add(new Edge(j, i));
                }
            }
        }
    }

    //  graph coloring -> bfs traversal
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
                    } else if(color[curr] == color[e.dest]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // bipartite graph
    public static boolean isBipartite(List<List<Integer>> edges) {
        int v = edges.size();
        ArrayList<Edge>[] graph = new ArrayList[v];
        construct1(edges,graph,v);
        
        // dfs(graph, v);
        boolean[] vis = new boolean[v];
        for(int i=0; i<v; i++) {
            if(!vis[i]) {
                if(!graphColoring(graph,i,vis)) return false;
            }
        }
        return true;
    }

    // cycle detection - disjoint set
    static class Ds {
        int[] par, rank;
        public Ds(int n) {
            par = new int[n];
            rank = new int[n];

            Arrays.fill(rank, 1);
            for(int i=0; i<n; i++) {
                par[i] = i;
            }
        }

        public int find(int x) {
            if(par[x] == x) return x;
            return par[x] = find(par[x]);
        }

        public void union(int a, int b) {
            int parA = find(a);
            int parB = find(b);

            int rA = rank[parA];
            int rB = rank[parB];
            if(rA == rB) {
                par[parB] = parA;
                rank[parA]++;
            } else if(rA > rB) {
                par[parB] = parA;
            } else {
                par[parA] = parB;
            }
        }
    }

    private static boolean cycleDetection(ArrayList<ArrayList<Integer>> graph, int V) {
        Ds ds = new Ds(V);

        for(int i=0; i<graph.size(); i++) {
            for(int j=0; j<graph.get(i).size(); j++) {
                int a = i;
                int b = graph.get(i).get(j);

                int parA = ds.find(a);
                int parB = ds.find(b);

                if(parA == parB) return true;
                ds.union(a, b);
            }
        }

        return false;
    }

    public static void cycleDetection() {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        int[][] adj = {{1}, {0, 2, 4}, {1, 3}, {2, 4}, {1, 3}};
        int V = adj.length;

        for(int i=0; i<V; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i=0; i<adj.length; i++) {
            for(int j=0; j<adj[i].length; j++) {
                graph.get(i).add(adj[i][j]);
            }
        }

        for(int i=0; i<V; i++) {
            if(cycleDetection(graph, V)) {
                System.out.println("cycle exists");
            }
        }
        System.out.println("no cycle");
    }


    // topology sort - dfs -> O(V+E)
    public static void topoHelper(ArrayList<ArrayList<Integer>> adj, int V, boolean[] vis, Stack<Integer> st, int curr){
        vis[curr] = true;
        for(int i=0; i<adj.get(curr).size(); i++) {
            int dest = adj.get(curr).get(i);
            if(!vis[dest]) topoHelper(adj, V, vis, st, dest);
        }
        st.push(curr);
    }

    public static void topSort(ArrayList<ArrayList<Integer>> adj, int V) {
        boolean[] vis = new boolean[V];
        Stack<Integer> st = new Stack<>();

        for(int i=0; i<V; i++) {
            if(!vis[i]) topoHelper(adj, V, vis, st, i);
        }

        while(!st.isEmpty()) {
            System.out.print(st.pop() + " ");
        }
    }

    // kahns algo -> O(V+E)
    public static void topoSort(ArrayList<ArrayList<Integer>> adj, int V) {
        int[] indeg = new int[V];

        for(int i=0; i<adj.size(); i++) {
            for(int j=0; j<adj.get(i).size(); j++) {
                int dest = adj.get(i).get(j);
                indeg[dest]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();

        for(int i=0; i<V; i++) {
            if(indeg[i] == 0) q.add(i);
        }

        ArrayList<Integer> order = new ArrayList<>();

        while(!q.isEmpty()) {
            int curr = q.remove();
            order.add(curr);

            for(int i=0; i<adj.get(curr).size(); i++) {
                int dest = adj.get(curr).get(i);
                if(--indeg[dest] == 0) q.add(dest);
            }
        }

        System.out.println("topology sort: " + order);
    }

    // course schedule
    public static boolean sort(ArrayList<ArrayList<Integer>> graph, int V, boolean[] vis, boolean[] recSt, int curr, Stack<Integer> st) {
        vis[curr] = true;
        recSt[curr] = true;

        for(int i=0; i<graph.get(curr).size(); i++) {
            int dest = graph.get(curr).get(i);
            if(recSt[dest]) return true;

            if(!vis[dest]) {
                if(sort(graph, V, vis, recSt, dest, st)) return true;
            }
        }

        recSt[curr] = false;
        st.push(curr);
        return false;
    }

    public static void findOrder(int n, int[][] req) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for(int i=0; i<n; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i=0; i<req.length; i++) {
            graph.get(req[i][0]).add(req[i][1]);
        }

        boolean[] vis = new boolean[n];
        boolean[] recSt = new boolean[n];
        Stack<Integer> st = new Stack<>();

        for(int i=0; i<n; i++) {
            if(!vis[i]) {
                sort(graph, n, vis, recSt, i, st);
            }
        }
        
        System.out.println(st);
    }

    




    public static void main(String[] args) {
        int n=4;
        int[][] roads = {{1,1,1,0},
                        {1,1,1,0},
                        {1,1,1,0},
                        {0,0,0,1}};
        // findNumOfProvinces(roads, n);
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        edges.add(new ArrayList<>(List.of(1,1,1,0)));
        edges.add(new ArrayList<>(List.of(1,1,1,0)));
        edges.add(new ArrayList<>(List.of(1,1,1,1)));
        edges.add(new ArrayList<>(List.of(0,0,1,1)));
        // System.out.println(isBipartite(edges));
        // cycleDetection();

        // findOrder(4, new int[][]{{1,0},{2,0},{3,1},{3,2}});

        int V = 6;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        adj.get(2).add(3);
        adj.get(3).add(1);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(5).add(0);
        adj.get(5).add(2);
        // topoSort(adj, V);

        int[][] adjList = new int[][]{{1,2},{2,3},{5},{0},{5},{},{}};
        int vertices = adjList.length;
        // findSafeStates(adjList, vertices);

    }
}

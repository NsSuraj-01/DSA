package Graphs;

import java.util.*;

public class Part1 {
    static class Edge {
        int src, dest;
        public Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
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

    public static void bfs(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        Queue<Integer> q = new LinkedList<>();
        q.add(src);

        while(!q.isEmpty()) {
            int curr = q.remove();
            
            if(!vis[curr]) {
                vis[curr] = true;
                System.out.print(curr+" ");
                for(int i=0; i<graph[curr].size(); i++) {
                    Edge e = graph[curr].get(i);
                    q.add(e.dest);
                }
            }
        }
        System.out.println("\n-------");
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
                // bfs(graph,i, vis);
                dfs(graph, i, vis);
            }
        }
    }

    private static boolean hasPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis) {
        if(src == dest) return true;
        vis[src] = true; 

        for(int i=0; i<graph[src].size(); i++) {
            Edge e = graph[src].get(i);
            if(!vis[e.dest] && hasPath(graph, e.dest, dest, vis)) {
                return true;
            }
        }
        return false;
    } 

    public static void hasPath(ArrayList<Edge>[] graph, int src, int dest) {
        boolean[] vis = new boolean[graph.length];
        boolean isPath =  hasPath(graph, src, dest, vis);
        if(isPath) System.out.println("Path exists");
        else System.out.println("no path");
    }
    
    public static void main(String[] args) {
        int V=7;
        ArrayList<Edge>[] g = new ArrayList[V];
        construct(g,V);
        
        // hasPath(g, 1, 5);
        
    }
}

package Graphs.Problems;

import java.util.*;

public class TopoSort {

    public static void bfs(List<List<Integer>> graph, int src, boolean vis[]) {
        Queue<Integer> q = new LinkedList<>();
        q.add(src);

        while(!q.isEmpty()) {
            int cur = q.remove();
            
            if(!vis[cur]) {
                vis[cur] = true;
                System.out.print(cur + " ");

                for(int i=0; i<graph.get(cur).size(); i++) {
                    int dest = graph.get(cur).get(i);
                    q.add(dest);
                }
            }
        }
        System.out.println();
    }

    public static void traversal(List<List<Integer>> graph, int V) {
        boolean vis[] = new boolean[V];

        for(int i=0; i<V; i++) {
            if(!vis[i]) bfs(graph, i, vis);
        }
    }


    public static void findSafeStates(int edges[][], int V) {
        // 1. transpose graph
        // 2. top sort - bfs

        List<List<Integer>> tgraph = new ArrayList<>();
        for(int i=0; i<V; i++) tgraph.add(new ArrayList<>());

        for(int i=0; i<edges.length; i++) {
            for(int j=0; j<edges[i].length; j++) {
                int src = i, dest = edges[i][j];
                tgraph.get(dest).add(src);
            }
        }
        // traversal(tgraph, V);

        int indeg[] = new int[V];
        for(int i=0; i<tgraph.size(); i++) {
            for(int j=0; j<tgraph.get(i).size(); j++) {
                int dest = tgraph.get(i).get(j);
                indeg[dest]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        List<Integer> ans = new ArrayList<>();

        
        for(int i=0; i<V; i++) {
            if(indeg[i] == 0) q.add(i);
        }

        while(!q.isEmpty()) {
            int cur = q.remove();
            ans.add(cur);

            for(int i=0; i<tgraph.get(cur).size(); i++) {
                int dest = tgraph.get(cur).get(i);
                indeg[dest]--;

                if(indeg[dest] == 0) {
                    q.add(dest);
                }
            }
        }

        Collections.sort(ans);
        System.out.println(ans);
    }

    public static void alienHelper(List<List<Integer>> adj, int cur, boolean vis[], StringBuilder ans) {
        vis[cur] = true;
        
        for(int i=0; i<adj.get(cur).size(); i++) {
            int dest = adj.get(cur).get(i);
            if(!vis[dest]) {
                alienHelper(adj, dest, vis, ans);
            }
        }
        ans.append( (char)(cur + 'a') );
    }

    // alien dictionary, N - num of char
    public static void alienDict(List<String> dict, int N) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0; i<N; i++) {
            adj.add(new ArrayList<>());
        }

        // create graph
        for(int i=0; i<dict.size()-1; i++) {
            String s1 = dict.get(i);
            String s2 = dict.get(i+1);

            for(int j=0, k=0; j<s1.length() && k<s2.length(); j++, k++) {
                char c1 = s1.charAt(j);
                char c2 = s2.charAt(k);

                if(c1 != c2) {
                    int src = c1 - 'a';
                    int dest = c2 - 'a';
                    adj.get(src).add(dest);
                    break;
                } 
            }
        }

        // topsort
        StringBuilder ans = new StringBuilder();
        boolean vis[] = new boolean[N];
        for(int i=0; i<N; i++) {
            if(!vis[i]) alienHelper(adj, i, vis, ans);
        }
        
        System.out.println("dict: " + ans.reverse().toString());
    } 

    public static void main(String[] args) {
        int edges[][] = {{1,2}, {2,3}, {5}, {0},
                         {5}, {}, {}, {1}};
        // findSafeStates(edges, 8);
        
        List<String> dict = new ArrayList<>();
        String d[] = {"baa","abcd","abca","cab","cad"};
        for(String str : d) dict.add(str);
        alienDict(dict, 4);
    }
    
}

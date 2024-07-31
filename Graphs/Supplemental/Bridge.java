package Graphs.supplemental;

import java.util.*;
public class Bridge {

    public static List<List<Integer>> createGraph(int V) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0; i<V; i++) {
            adj.add(new ArrayList<>());
        }

        /*
            2 -- 0 -- 3 - 5
            |  /      | /
             1        4
        */

        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(0).add(3);

        adj.get(1).add(0);
        adj.get(1).add(2);
        
        adj.get(2).add(0);
        adj.get(2).add(1);

        adj.get(3).add(0);
        adj.get(3).add(4);
        adj.get(3).add(5);

        adj.get(4).add(3);
        adj.get(4).add(5);

        adj.get(5).add(3);
        adj.get(5).add(4);

        return adj;
    } 

    // tarjan's algo
    public static void tarjansAlgo(List<List<Integer>> adj, int curr, int par, boolean[] vis, int[] dt, int[] low, int time, List<List<Integer>> res) {
        vis[curr] = true;
        dt[curr] = low[curr] = ++time;

        for(int i=0; i<adj.get(curr).size(); i++) {
            int dest = adj.get(curr).get(i);
            if(dest == par) continue;
            
            if(!vis[dest]) {
                tarjansAlgo(adj, dest, curr, vis, dt, low, time, res);

                low[curr] = Math.min(low[curr], low[dest]);
                if(dt[curr] < low[dest]) {
                    res.add(Arrays.asList(curr, dest));
                }
            }
            else {
                low[curr] = Math.min(low[curr], dt[dest]);
            }
        }
    }


    public static void bridge(List<List<Integer>> adj, int V) {
        int[] dt = new int[V]; // discovery time
        int[] low = new int[V]; // lowest discovery time
        boolean[] vis = new boolean[V];
        int time = 0;
        List<List<Integer>> res = new ArrayList<>();

        for(int i=0; i<V; i++) {
            if(!vis[i]) {
                tarjansAlgo(adj, i, -1, vis, dt, low, time, res);
            }
        }

        System.out.println("Bridge: ");
        System.out.println(res);
    }

    public static void main(String[] args) {
        int V = 6;
        List<List<Integer>> graph = createGraph(V);

        bridge(graph, V);
    }
}

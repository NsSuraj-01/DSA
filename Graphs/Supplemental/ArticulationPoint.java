package Graphs.supplemental;

import java.util.ArrayList;
import java.util.List;

public class ArticulationPoint {
    
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

        adj.get(4).add(3);

        return adj;
    } 


    public static void tarjanAlgo(List<List<Integer>> adj, int curr, int par, boolean vis[], boolean[] ap, int dt[], int low[], int time) {
        vis[curr] = true;
        dt[curr] = low[curr] = ++time;
        int child = 0; // child = unvis neigh

        for(int i=0; i<adj.get(curr).size(); i++) {
            int dest = adj.get(curr).get(i);

            if(par == dest) continue;

            if(vis[dest]) {
                low[curr] = Math.min(low[curr], dt[dest]);
            }
            else {
                tarjanAlgo(adj, dest, curr, vis, ap, dt, low, time+1);

                low[curr] = Math.min(low[curr], low[dest]);

                if(par!=-1 && dt[curr] <= low[dest]) {
                    ap[curr] = true;
                }
                child++;

                if(par == -1 && child > 1) {
                    ap[curr] = true;
                }
            }
        }
    }


    public static void articulationPoint(List<List<Integer>> adj, int V) {
        boolean[] vis = new boolean[V], ap = new boolean[V];
        int[] dt = new int[V], low = new int[V];
        int time = 0;

        for(int i=0; i<V; i++) {
            if(!vis[i]) {
                tarjanAlgo(adj, i, -1, vis, ap, dt, low, time);
            }
        }

        System.out.println("articulation points: ");
        for(int i=0; i<V; i++) {
            if(ap[i]) System.out.print(i+" ");
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Integer>> adj = createGraph(V);

        articulationPoint(adj, V);
    }
}

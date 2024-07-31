package Graphs.Problems;

import java.util.*;

public class ShortestPath {
    
    final static int maxi = (int)1e9 + 7;

    static class Pair {
        int x, y, dist;
        public Pair(int x, int y, int dist) {
            this.x = x; this.y = y;
            this.dist = dist;
        }
    }
    // shortest path in a binary matrix
    public static void shortestPath(int grid[][]) {
        if(grid[0][0] == 1) {
            System.out.println("unreachable");
            return;
        }

        int n = grid.length, m = grid[0].length;

        int dist[][] = new int[n][m];
        int maxi = (int)1e9 + 7;

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                dist[i][j] = maxi;
            }
        }
        dist[0][0] = 0;

        int dx[] = {-1,0,1,0,1,1,-1,-1};
        int dy[] = {0,-1,0,1,1,-1,1,-1};

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0,0, 0));

        while(!q.isEmpty()) {
            Pair cur = q.remove();
            int x = cur.x;
            int y = cur.y;
            if(x == n-1 && y == m-1) {
                break;
            }

            for(int i=0; i<8; i++) {
                int nr = x + dx[i];
                int nc = y + dy[i];

                if(nr>=0 && nr<n && nc>=0 && nc<m && grid[nr][nc] == 0) {
                    if(dist[x][y] + 1 < dist[nr][nc]) {
                        dist[nr][nc] = 1 + dist[x][y];
                        q.add(new Pair(nr, nc, cur.dist + 1));
                    } 
                }
            }
        }

        for(int row[] : dist) {
            for(int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
        System.out.println("min dist: " + dist[n-1][m-1]);
    }
    
    static class Info {
        int node, cost, stops;
        public Info(int node, int cost, int stops) {
            this.node = node;
            this.cost = cost;
            this.stops = stops;
        }
    }

    static class Edge {
        int dest, wt;
        public Edge(int dest, int wt) {
            this.dest = dest;
            this.wt = wt;
        }
    }

    // cheapest connecting flights within k stops
    public static void cheapestFlights(int flights[][], int src, int dest, int k) {
        int n = flights.length;
        List<List<Edge>> adj = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adj.add(new ArrayList<>());
        }

        for(int flight[] : flights) {
            adj.get(flight[0]).add(new Edge(flight[1], flight[2]));
        }

        int dist[] = new int[n];
        for(int i=0; i<n; i++) {
            if(i != src) dist[i] = maxi;
        }

        Queue<Info> q = new LinkedList<>();
        q.add(new Info(src, 0, 0));

        while(!q.isEmpty()) {
            Info cur = q.remove();
            if(cur.stops > k) break;

            for(int i=0; i<adj.get(cur.node).size(); i++) {
                int u = cur.node;
                int v = adj.get(cur.node).get(i).dest;
                int price = adj.get(cur.node).get(i).wt;

                if(cur.stops <= k && cur.cost + price < dist[v]) {
                    dist[v] = cur.cost + price;
                    q.add(new Info(v, dist[v], cur.stops + 1));
                }
            }
        }
        if(dist[dest] != maxi) {
            System.out.println("min cost: " + dist[dest]);
        } else {
            System.out.println("unreachable");
        }
    }

    // num of ways to arrive at dest
    public static void numWays(int arr[][], int n) {
        List<List<Edge>> adj = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adj.add(new ArrayList<>());
        }

        for(int edge[] : arr) {
            adj.get(edge[0]).add(new Edge(edge[1], edge[2]));
            adj.get(edge[1]).add(new Edge(edge[0], edge[1]));
        }

        int dist[] = new int[n];
        Arrays.fill(dist, (int)1e9);

        dist[0] = 0;

        int ways[] = new int[n];
        ways[0] = 1;

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.wt - b.wt);

        while(!pq.isEmpty()) {
            Edge cur = pq.poll();

            if(cur.wt > dist[cur.dest]) continue;
            for(Edge dest : adj.get(cur.dest)) {
                int u = cur.dest;
                int v = dest.dest;
                int time = dest.wt;

                if(time + dist[u] < dist[v]) {
                    dist[v] = time + dist[u];
                    pq.add(new Edge(v, dist[v]));
                    ways[v] = ways[u];
                }

                if(time + dist[u] == dist[v]) {
                    ways[v] += ways[u];
                }
            }
        }
        System.out.println("num of ways: " + ways[n-1]);
    }

    // find node with smallest num of neigh with threshold dist
    public static void smallestNeigh(int n, int edges[][], int distanceThreshold) {
        int dist[][] = new int[n][n];
        for(int row[] : dist) {
            Arrays.fill(row, -1);
        }

        for(int edge[] : edges) {
            int r = edge[0], c = edge[1];
            int wt = edge[2];

            dist[r][c] = wt;
            dist[c][r] = wt;
        } 
        int maxi = (int)1e9;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(i == j) dist[i][j] = 0;
                else if(dist[i][j] == -1) dist[i][j] = maxi;
            }
        }
        
        for(int k=0; k<n; k++) {
            for(int row=0; row<n; row++) {
                for(int col=0; col<n; col++) {
                    dist[row][col] = Math.min(dist[row][col], 
                    dist[row][k] + dist[k][col]);
                }
            }
        }

        int ans = -1;
        int mincount = maxi;
        for(int i=n-1; i>=0; i--) {
            int count = 0;
            for(int j=0; j<n; j++) {
                if(dist[i][j] != 0 && dist[i][j] <= distanceThreshold) {
                    count++;
                }
            }
            if(count < mincount) {
                ans = i;
                mincount = count;
            }
        }
        for(int row[] : dist) {
            for(int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
        System.out.println(ans);
        
    }



    public static void main(String[] args) {
        // int grid[][] = {{0,1}, {1,0}};
        // shortestPath(grid);

        int arr[][] = {{4,1,1}, {1,2,3}, {0,3,2}, {0,4,10},
                        {3,1,1}, {1,4,3}} ;
        // cheapestFlights(arr, 0, 1, 1);
        int n = 5;
        int[][] edges = {
            {0, 1, 2},
            {0, 4, 8},
            {1, 2, 3},
            {1, 4, 2},
            {2, 3, 1},
            {3, 4, 1}
        };
        int distanceThreshold = 2;
        smallestNeigh(n, edges, distanceThreshold);
    }
}

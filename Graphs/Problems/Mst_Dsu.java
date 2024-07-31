package Graphs.Problems;

import java.util.*;

public class Mst_Dsu {
    static class Dsu {
        int par[], rank[], size[];
        public Dsu(int n) {
            par = new int[n];
            rank = new int[n];
            size = new int[n];
            
            for(int i=0; i<n; i++) {
                par[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if(x == par[x]) return x;
            return par[x] = find(par[x]);
        }

        public void unionByRank(int a, int b) {
            int parA = find(a);
            int parB = find(b);

            // cycle condition
            if(parA == parB) return;

            int rA = rank[parA];
            int rB = rank[parB];

            if(rA > rB) {
                par[parB] = parA;
            } else if(rA < rB) {
                par[parA] = parB;
            } else {
                par[parB] = parA;
                rank[parA] += 1;
            }
        }

        public void unionBySize(int a, int b) {
            int parA = find(a);
            int parB = find(b);

            if(parA == parB) return;

            if(size[parA] < size[parB]) {
                par[parA] = parB;
                size[parB] += size[parA];
            } else {
                par[parB] = parA;
                size[parA] += size[parB];
            }
        }
    }


    static int dx[] = {-1,0,1,0};
    static int dy[] = {0,-1,0,1};

    private static boolean isValid(int row, int col, int n, int m) {
        return (row >= 0 && row < n && col >= 0 && col < m) ;
    }

    // maximum connected groups
    public static void maxConnGroup(int grid[][]) {
        int n = grid.length, m = grid[0].length;
        Dsu ds = new Dsu(n*m);

        // step 1 - form components
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(grid[i][j] == 0) continue;
                for(int idx=0; idx<4; idx++) {
                    int nr = i + dx[idx];
                    int nc = j + dy[idx];

                    if(isValid(nr, nc, n, m) && grid[nr][nc] == 1) {
                        int cur = i*m + j;
                        int adj = nr*m + nc;

                        ds.unionBySize(cur, adj);
                    }
                }
            }
        }

        // step 2 - for each 0, find max size 
        int max_size = 0;

        for(int row=0; row<n; row++) {
            for(int col=0; col<m; col++) {
                if(grid[row][col] == 1) continue;
                
                // 2 adj of a 0 can be from same component
                Set<Integer> components = new HashSet<>();
                for(int idx=0; idx<4; idx++) {
                    int nr = row + dx[idx];
                    int nc = col + dy[idx];

                    if(isValid(nr, nc, n, m)) {
                        if(grid[nr][nc] == 1) {
                            components.add(ds.find(nr*m + nc));
                        }
                    }
                }

                int totSize = 0;
                for(int par : components) {
                    totSize += ds.size[par];
                }
                max_size = Math.max(max_size, totSize + 1);
            }
        }

        // what if all cells are 1's
        for(int cell = 0; cell < n*m; cell++) {
            max_size = Math.max(max_size, ds.size[ds.par[cell]]);
        }

        System.out.println("max conn group- " + max_size);
    }

    public static int find_minOp(int x, int par[]) {
        if(par[x] == x) return x;
        return par[x] = find_minOp(par[x], par);
    }

    public static void union_minOp(int a, int b, int par[], int rank[], int count) {
        int parA = par[a];
        int parB = par[b];

        if(parA == parB) {
            count++;
            return;
        }

        int rA = rank[parA];
        int rB = rank[parB];

        if(rA > rB) {
            par[parB] = parA;
        } else if(rA < rB) {
            par[parA] = parB;
        } else {
            par[parB] = parA;
            rank[parA] += 1;
        }
    }

    // min operations to make network conn
    public static void minOp(int n, int edges[][]) {
        int extraEdges = 0;
        int nc = 0;
        Dsu ds = new Dsu(n);

        for(int edge[] : edges) {
            int a = ds.find(edge[0]);
            int b = ds.find(edge[1]);
            if(a == b) extraEdges++;
            else {
                ds.unionByRank(edge[0],edge[1]);
            }
        }
        for(int i=0; i<n; i++) {
            if(ds.par[i] == i) nc++;
        }

        if(extraEdges >= nc-1) {
            System.out.println("min operations - " + (nc-1));
        } else {
            System.out.println("not possible");
        }
    }

    // most stones removed
    public static void mostStonesRemoved(int stones[][]) {
        int n = stones.length; // num of stones
        int maxRow = 0, maxCol = 0;
        
        // to know dimension of stones
        for(int i=0; i<n; i++) {
            maxRow = Math.max(maxRow, stones[i][0]);
            maxCol = Math.max(maxCol, stones[i][1]);
        }

        Dsu ds = new Dsu(maxRow + maxCol + 1);
        Map<Integer,Integer> nodes = new HashMap<>();

        for(int i=0; i<n; i++) {
            int nodeRow = stones[i][0];
            int nodeCol = stones[i][1] + maxRow + 1;
            ds.unionBySize(nodeRow, nodeCol);
            nodes.put(nodeRow, 1);
            nodes.put(nodeCol, 1);
        }

        int nc = 0;
        for(int key : nodes.keySet()) {
            if(ds.par[key] == key) nc++;
        }


        int ans = (n - nc);
        System.out.println("max stones removed: " + ans);
    }

    // accounts merge
    public static void accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        Dsu ds = new Dsu(n);

        Map<String, Integer> mpp = new HashMap<>();
        for(int i=0; i<n; i++) {
            for(int j=1; j<accounts.get(i).size(); j++) {
                String mail = accounts.get(i).get(j);
                if(!mpp.containsKey(mail)) {
                    mpp.put(mail, i);
                } else {
                    ds.unionBySize(i, mpp.get(mail));
                }
            }   
        }

        List<String>[] mailList = new ArrayList[n];
        for(int i=0; i<n; i++) {
            mailList[i] = new ArrayList<>();
        }

        for(String key : mpp.keySet()) {
            String mail = key;
            int node = ds.find(mpp.get(key));
            mailList[node].add(mail);           
        }

        List<List<String>> ans = new ArrayList<>();
        for(int i=0; i<n; i++) {
            if(mailList[i].size() == 0) continue;
            Collections.sort(mailList[i]);
            List<String> temp = new ArrayList<>();
            temp.add(accounts.get(i).get(0));

            for(String item : mailList[i]) temp.add(item);
            ans.add(temp);
        }
        
        // print ans
        for(List<String> list : ans) {
            System.out.println(list);
        }
    }

    // num of islands 2
    public static void numOfIslands(int queries[][], int n, int m, int k) {
        int island[][] = new int[n][m];
        int vis[][] = new int[n][m];

        int dx[] = {-1, 0, 1, 0};
        int dy[] = {0, -1, 0, 1};

        Dsu ds = new Dsu(n*m);
        
        int cnt = 0;
        List<Integer> ans = new ArrayList<>();

        for(int query[] : queries) {
            int row = query[0];
            int col = query[1];

            if(vis[row][col] == 1) {
                ans.add(cnt);
                continue;
            }
            vis[row][col] = 1;
            cnt++;

            for(int i=0; i<4; i++) {
                int nR = row + dx[i];
                int nC = col + dy[i];

                if(isValid(nR, nC, n, m) && vis[nR][nC] == 1) {
                    int curnode = row * m + col;
                    int adjnode = nR * m + nC;

                    if(ds.par[curnode] != ds.par[adjnode]) {
                        cnt--;
                        ds.unionBySize(curnode, adjnode);
                    }
                }
            }
            ans.add(cnt);
        }
        
        System.out.println(ans);
    }


    public static void main(String[] args) {
        // int grid[][] = {{1,1,0,1,1}, {1,1,0,1,1},
        //                 {0,0,1,0,0}, {0,0,1,1,1}};
        // maxConnGroup(grid);

        // int edges[][] = {{0,1}, {0,2}, {0,3}, {1,2}, {1,3}};
        // int n = 6;
        // minOp(n, edges);

        // int stones[][] = {{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}};
        // mostStonesRemoved(stones);

        // String[][] data = {
        //     {"John", "johnsmith@mail.com", "john_newyork@mail.com"},
        //     {"John", "johnsmith@mail.com", "john00@mail.com"},
        //     {"Mary", "mary@mail.com"},
        //     {"John", "johnnybravo@mail.com"}
        // };

        // List<List<String>> accounts = new ArrayList<>();

        // for (String[] row : data) {
        //     List<String> innerList = new ArrayList<>();
        //     for (String item : row) {
        //         innerList.add(item);
        //     }
        //     accounts.add(innerList);
        // }
        // accountsMerge(accounts);

        int n = 4, m = 5, k = 4; 
        int queries[][] = {{1,1},{0,1},{3,3},{3,4}};
        
        numOfIslands(queries, n, m, k);                   
    }
}

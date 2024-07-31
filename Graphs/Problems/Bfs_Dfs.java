package Graphs.Problems;
import java.util.*;

public class Bfs_Dfs {
    static class Info {
        int i, j;
        int time;
        public Info(int i, int j, int time) {
            this.i = i;
            this.j = j;
            this.time = time;
        }
    }

    // Rotten oranges - multi source bfs
    public static void minTime(int[][] oranges) {
        Queue<Info> q = new LinkedList<>();
        int n = oranges.length;
        int m = oranges[0].length;

        int[][] vis = new int[n][m];
        int fresh = 0;

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(oranges[i][j] == 2) {
                    q.add(new Info(i,j,0));
                    vis[i][j] = 2;
                }
                else vis[i][j] = 0; 
                
                if(oranges[i][j] == 1) fresh++;
            }
        }

        int time = 0;
        int cnt=0;

        int[] nextRow = {0, 1, 0, -1};
        int[] nextCol = {-1, 0, 1, 0};

        while(!q.isEmpty()) {
            Info curr = q.remove();
            int row = curr.i;
            int col = curr.j;
            int tm = curr.time;
            time = Math.max(time, tm);

            for(int i=0; i<4; i++) {
                int nR = row + nextRow[i];
                int nC = col + nextCol[i];

                if(nR >= 0 && nR < n && nC >= 0 && nC < m 
                && vis[nR][nC] == 0 && oranges[nR][nC] == 1) {
                    vis[nR][nC] = 2;
                    q.add(new Info(nR, nC, tm+1));
                    cnt++;
                }
            }
        }

        if(cnt != fresh) {
            System.out.println("fresh oranges left");
            return;
        }
        System.out.println("Min time: "+time);
    }

    static class Node {
        int i,j,steps;
        public Node(int i, int j, int steps) {
            this.i = i; this.j = j;
            this.steps = steps;
        }
    }

    // find nearest 0 from 1's - multi source bfs
    public static void _01matrix(int[][] grid) {
        Queue<Node> q = new LinkedList<>();
        
        int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m];
        int[][] dist = new int[n][m];

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(grid[i][j] == 0) {
                    vis[i][j] = 1;
                    q.add(new Node(i, j, 0));
                }
            }
        }

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        while(!q.isEmpty()) {
            Node curr = q.remove();
            dist[curr.i][curr.j] = curr.steps;
            
            for(int i=0; i<4; i++) {
                int nR = curr.i + dx[i];
                int nC = curr.j + dy[i];

                if(nR>=0 && nR<n && nC>=0 && nC<m &&
                vis[nR][nC] != 1) {
                    vis[nR][nC] = 1;
                    q.add(new Node(nR, nC, curr.steps+1));
                }
            }
        }

        for(int[] row : dist) {
            for(int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }


    public static void dfs(char[][] grid, int i, int j, int n, int m, int[][] vis, int[] dx, int[] dy) {
        vis[i][j] = 1;

        for(int k=0; k<4; k++) {
            int nR = i+dx[k];
            int nC = j+dy[k];

            if(nR>=0 && nR<n && nC>=0 && nC<m &&
            vis[nR][nC] == 0 && grid[nR][nC] == '0') {
                dfs(grid,nR,nC,n,m,vis,dx,dy);
            }
        }
    }

    // surrounded regions - multisource dfs
    public static void surrRegions(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m];

        int[] dx = {-1,0,1,0};
        int[] dy = {0,-1,0,1};

        // boundary rows
        for(int j=0; j<m; j++) {
            if(vis[0][j]!=1 && grid[0][j] == '0') {
                dfs(grid,0,j, n, m, vis, dx, dy);
            }

            if(vis[n-1][j] != 1 && grid[n-1][j] == '0') {
                dfs(grid,n-1,j, n, m, vis, dx, dy);
            }
        }

        // boundary cols
        for(int i=0; i<n; i++) {
            if(vis[i][0] != 1 && grid[i][0] == '0') {
                dfs(grid,i,0, n, m, vis, dx, dy);
            }
            if(vis[i][m-1] != 1 && grid[i][m-1] == '0') {
                dfs(grid,i,m-1, n, m, vis, dx, dy);
            }
        }


        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(vis[i][j] == 0 && grid[i][j] == '0') {
                    grid[i][j] = 'x';
                }
            }
        }

        for(char[] row : grid) {
            for(char item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public static void countHelper(int[][] grid, int i, int j, int n, int m, int[][] vis, int dx[], int dy[]) {
        vis[i][j] = 1;

        for(int k = 0; k<4; k++) {
            int nR = i + dx[k];
            int nC = j + dy[k];

            if(nR>=0 && nR<n && nC>=0 && nC<m  && vis[nR][nC] != 1 && grid[nR][nC] == 1) {
                countHelper(grid, nR, nC, n, m, vis, dx, dy);
            }

        }
    }


    // num of enclaves
    public static void countEnclaves(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] vis = new int[m][n];
        
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        // travel boundaries 
        // boundary cols
        for(int i=0; i<n; i++) {
            if(vis[i][0] != 1 && grid[i][0] == 1) {
                countHelper(grid, i, 0, n, m, vis, dx, dy);
            }

            if(vis[i][m-1] != 1 && grid[i][m-1] == 1) {
                countHelper(grid, i, m-1, n, m, vis, dx, dy);
            }
        }

        // boundary rows
        for(int j=0; j<m; j++) {
            if(vis[0][j] != 1 && grid[0][j] == 1) {
                countHelper(grid, 0, j, n, m, vis, dx, dy);
            }

            if(vis[m-1][j] != 1 && grid[m-1][j] == 1) {
                countHelper(grid, n-1, j, n, m, vis, dx, dy);
            }
        }


        int cnt = 0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(vis[i][j] != 1 && grid[i][j] == 1) {
                    cnt  += 1;
                }
            }
        }

        System.out.println(cnt);
    }

    static class Dim {
        int i, j;
        public Dim(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void islandsBfs(int grid[][], int i, int j, int n, int m, int vis[][], int dx[], int dy[]) {
        Queue<Dim> q = new LinkedList<>();
        q.add(new Dim(i,j));

        while(!q.isEmpty()) {
            Dim cur = q.remove();

            if(vis[cur.i][cur.j] != 1) {
                vis[cur.i][cur.j] = 1;

                for(int k=0; k<4; k++) {
                    int nr = cur.i + dx[k];
                    int nc = cur.j + dy[k];

                    if(nr>=0 && nr<n && nc>=0 && nc<m && vis[nr][nc] != 1 && grid[nr][nc] == 1) {
                        q.add(new Dim(nr, nc));
                    }
                }
            }
        }
    }

    // number of islands
    public static void numIslands(int grid[][]) {
        int n = grid.length;
        int m = grid[0].length;

        int dx[] = {-1, 0, 1, 0};
        int dy[] = {0, -1, 0, 1};

        int vis[][] = new int[n][m];

        int cnt = 0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(vis[i][j] != 1 && grid[i][j] == 1) {
                    ++cnt;
                    islandsBfs(grid, i, j, n, m, vis, dx, dy);
                }
            }
        }

        System.out.println("num of islands: " + cnt);
    }


    public static void distinctIslandsBfs(int grid[][], int i, int j, int n, int m, boolean vis[][], int base_x, int base_y, List<String> list) {
        Queue<Dim> q = new LinkedList<>();
        q.add(new Dim(i, j));

        int dx[] = {-1,0,1,0};
        int dy[] = {0, -1, 0, 1};

        while(!q.isEmpty()) {
            Dim cur = q.remove();
            int row = cur.i, col = cur.j;

            if(!vis[row][col]) {
                vis[row][col] = true;
                list.add(""+(row-base_x) + ","+(col-base_y) + " ");

                for(int k=0; k<4; k++) {
                    int nR = row + dx[k];
                    int nC = col + dy[k];

                    if(nR>=0 && nR<n && nC>=0 && nC<m && !vis[nR][nC] && grid[nR][nC] == 1) {
                        q.add(new Dim(nR, nC));
                    }
                }
            } 
        }
    }

    // number of distinct islands
    // store the shape in set ds
    public static void distinctIslands(int grid[][]) {
        int n = grid.length, m = grid[0].length;
        boolean vis[][] = new boolean[n][m];

        Set<List<String>> stt = new HashSet<>();
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(!vis[i][j] && grid[i][j] == 1) {
                    List<String> setList = new ArrayList<>();
                    distinctIslandsBfs(grid, i, j, n, m, vis, i, j, setList);
                    stt.add(setList);
                }
            }
        }

        int len = stt.size();
        System.out.println("distinct islands: " + len);
        for(List<String> lst : stt) {
            for(String s : lst) {
                System.out.print(s);
            }
            System.out.println();
        }
    }

    // get max size of 1s by changing atmost one 0 to 1
    public static void maxSize(int adj[][]) {
        int n = adj.length;
        int m = adj[0].length;

        boolean vis[][] = new boolean[n][m];
        int max_size = 0;

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(!vis[i][j] && adj[i][j] == 1) {
                    max_size = Math.max(max_size, dfs_maxsize(adj, i, j, n, m, vis));
                }
            }
        }

        // change 0 -> 1
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(adj[i][j] == 0) {
                    max_size = Math.max(max_size, updatedSize(adj,i,j,n,m));
                }
            }
        }

        System.out.println(max_size);
    }

    static int dx[] = {-1, 0, 1, 0};
    static int dy[] = {0, -1, 0, 1};

    public static int dfs_maxsize(int adj[][], int x, int y, int n, int m, boolean vis[][]) {
        if(x<0 || x>=n || y<0 || y>=m || vis[x][y] || adj[x][y] == 0) {
            return 0;
        }

        vis[x][y] = true;

        int size = 1;
        for(int i=0; i<4; i++) {
            int nr = x + dx[i];
            int nc = y + dy[i];
            size += dfs_maxsize(adj, nr, nc, n, m, vis);
        }

        return size;
    }

    public static int updatedSize(int grid[][], int x, int y, int n, int m) {
        int temp[][] = new int[n][m];
        boolean tempvis[][] = new boolean[n][m];

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                temp[i][j] = grid[i][j];
            }
        }
        temp[x][y] = 1;
        return dfs_maxsize(temp, x, y, n, m, tempvis);
    }


    public static void main(String[] args) {
        // int[][] oranges = {{2,1,1},
        //                 {1,1,0},
        //                 {0,1,1}};
        // minTime(oranges);

        // int[][] mat = {{0,0,0},{0,1,0},{1,1,1}};
        // _01matrix(mat);

        // char[][] grid = {{'x','x','x','x'},
        //                 {'x','0','0','x'},
        //                 {'x','x','0','x'},
        //                 {'x','0','x','x'}};
        // surrRegions(grid);

        // int[][] grid = {{0,0,0,0},{1,0,1,0},
        //                 {0,1,1,0},{0,0,0,0}};
        // countEnclaves(grid);

        int grid[][] = {{1, 1, 0, 0, 0},
                        {1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1},
                        {0, 0, 0, 1, 1}};
        // distinctIslands(grid);

        maxSize(grid);

    }
    
}

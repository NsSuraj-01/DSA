package DP.Problems;

import java.util.*;

public class DpGrids {
    
    // Ninjas training
    public static int meritHelper(int arr[][], int days, 
    int day, int prevAc) {
        if(day == days) return 0;

        int score = 0;
        for(int ac = 0; ac < arr[0].length; ac++) {
            if(ac != prevAc) {
                int cur = arr[day][ac] + meritHelper(arr, days, day+1, ac);
                score = Math.max(score, cur);
            }
        }
        return score;

    }

    public static int meritMem(int arr[][], int n, int day, int prevAc,
    int dp[][]) {
        if(day == n) return 0;

        if(dp[day][prevAc] != -1) return dp[day][prevAc];

        int score = 0;
        for(int ac = 0; ac < arr[0].length; ac++) {
            if(prevAc != ac) {
                int cur = arr[day][ac] + meritMem(arr, n, day+1, ac, dp);
                score = Math.max(cur, score);
            }
        }
        return dp[day][prevAc] = score;
    }
    
    public static void ninjasTraining(int ac[][]) {
        int days = ac.length;
        int num_ac = ac[0].length;

        // int points = meritHelper(ac, days, 0, -1);
        int dp[][] = new int[days][num_ac+1];
        for(int row[] : dp) {
            Arrays.fill(row, -1);
        }
        int points = meritMem(ac, days, 0, 0, dp);

        System.out.println(points);
    }

    public static int unqPathsMem(int i, int j, int dp[][]) {
        if(i < 0 || j < 0) return 0;
        if(i == 0 && j == 0) return 1;

        if(dp[i][j] != -1) return dp[i][j];

        int up = unqPathsMem(i-1, j, dp);
        int left = unqPathsMem(i, j-1, dp);
        return dp[i][j] = (up + left);
        
    }

    // unique paths
    public static void unqPaths(int n, int m) {

        int dp[][] = new int[n][m];
        for(int row[] : dp) Arrays.fill(row, -1);

        int mem = unqPathsMem(n, m, dp);
    }

    static int dx[] = {-1, 0, 1, 0};
    static int dy[] = {0, -1, 0, 1};

    public static boolean isValid(int row, int col, int n, int m) {
        return (row >= 0 && row < n && col >= 0 && col < m); 
    }

    public static int unqPaths2Mem(int grid[][], int i, int j, int n,
    int m, int dp[][]) {
        if(i < 0 || j < 0) return 0;
        if(i == 0 && j == 0) return 1;

        if(dp[i][j] != -1) return dp[i][j];

        int cnt = 0;
        for(int id=0; id<2; id++) {
            int nr = i + dx[id];
            int nc = j + dy[id];
            if(isValid(nr, nc, n, m) && grid[nr][nc] == 0){
                cnt += unqPaths2Mem(grid, nr, nc, n, m, dp);
            }
        }
        return dp[i][j] = cnt;
    }

    public static int unqPaths2Tab(int grid[][]) {
        int n = grid.length, m = grid[0].length;
        int dp[][] = new int[n][m];

        if(grid[0][0] == 1) return 0;

        dp[0][0] = 1;
        for(int j=1; j<m; j++) {
            if(grid[0][j] == 0) dp[0][j] = dp[0][j-1];
        }
        for(int i=1; i<n; i++) {
            if(grid[i][0] == 0) dp[i][0] = dp[i-1][0];
        }

        for(int i=1; i<n; i++) {
            for(int j=1; j<m; j++) {
                if(grid[i][j] == 0) {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[n-1][m-1];
    }

    // unique paths 2 (with obstacles) 
    // grid : 0 - vacant, 1 - obs
    public static void unqPaths2(int grid[][]) {
        int n = grid.length;
        int m = grid[0].length;
        if(grid[n-1][m-1] == 1) {
            System.out.println("no path possible");
            return;
        }
        int dp[][] = new int[n][m];
        for(int row[] : dp) Arrays.fill(row, -1);

        int mem = unqPaths2Mem(grid, n-1, m-1, n, m, dp);
        int tab = unqPaths2Tab(grid);
        System.out.println("mem : " + mem);
        System.out.println("tab: " + tab);
    }

    public static int pathSumRec(int grid[][], int i, int j) {
        if(i == 0 && j == 0) return grid[i][j];
        if(i < 0 || j < 0) return (int)1e9;

        int up = pathSumRec(grid, i-1, j);
        int left = pathSumRec(grid, i, j-1);
        
        return grid[i][j] + Math.min(up, left);
    }

    public static int pathSumTab(int grid[][]) {
        int n = grid.length, m = grid[0].length;
        int dp[][] = new int[n][m];

        for(int rw[] : dp) {
            Arrays.fill(rw, (int)1e9);
        }
        dp[0][0] = grid[0][0];
        for(int i=1; i<n; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for(int j=1; j<m; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }

        for(int i=1; i<n; i++) {
            for(int j=1; j<m; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }

        return dp[n-1][m-1];
    }

    // min path sum
    public static  int minPathSum(int grid[][]) {
        int n = grid.length, m = grid[0].length;
        return pathSumRec(grid, n-1, m-1);
    }

    public static void main(String[] args) {
        // int ac[][] = { {1,2,5}, {3,1,1}, {3,3,3} };
        // ninjasTraining(ac); 

        int grid[][] = {{0,0,0}, {0,1,0}, {0,0,0}};
        unqPaths2(grid);

    }
}

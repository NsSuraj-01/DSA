package DP;

import java.util.*;

public class MCM {


    public static void printGrid(int[][] grid) {
        for(int[] row : grid) {
            for(int item : row) {
                System.out.print(item + " ");
            } 
            System.out.println();
        }
    }

    // mcm - memoization
    public static int mcm_mem(int[] arr, int i, int j, int[][] dp) {
        if(i == j) return 0;

        if(dp[i][j] != -1) return dp[i][j];
        
        int ans = Integer.MAX_VALUE;
        for(int k=i; k<=j-1; k++) {
            int cost1 = mcm_mem(arr, i, k, dp);
            int cost2 = mcm_mem(arr, k+1, j, dp);
            int cost3 = arr[i-1]*arr[k]*arr[j];

            int total = cost1+cost2+cost3;
            ans = Math.min(ans, total);
        }

        return dp[i][j] = ans;
    }

    // mcm - tabulation
    public static int mcm_tab(int[] arr, int n) {
        int[][] dp = new int[n][n];
        // init
        for(int i=0; i<n; i++) {
            dp[i][i] = 0;
        }

        // len : 2 -> n-1
        for(int len = 2; len<n; len++) {
            for(int i=1; i<=n-len; i++) { // row
                int j = i-1+len; // col
                dp[i][j] = Integer.MAX_VALUE;

                for(int k=i; k<j; k++) {
                    int c1 = dp[i][k];
                    int c2 = dp[k+1][j];
                    int c3 = arr[i-1]*arr[k]*arr[j];
                    int total = c1+c2+c3;
                    dp[i][j] = Math.min(dp[i][j], total);
                }

            }
        }
        printGrid(dp);

        return dp[1][n-1];
    }

    // mcm - recursion
    public static int mcm_rec(int[] arr, int i, int j) {
        if(i == j) return 0;

        int ans = Integer.MAX_VALUE;
        
        for(int k=i; k<=j-1; k++) {
            int cost1 = mcm_rec(arr, i, k);
            int cost2 = mcm_rec(arr, k+1, j);
            int cost3 = arr[i-1]*arr[k]*arr[j];
            int total = cost1 + cost2 + cost3;
            ans = Math.min(ans, total);
        }

        return ans;
    } 

    // matrix chain mupliplication
    public static void mcm(int[] arr, int n) {
        // int res = mcm_rec(arr, 1, n-1);

        // int[][] dp = new int[n][n];
        // for(int[] row : dp) {
        //     Arrays.fill(row, -1);
        // }
        // int res = mcm_mem(arr, 1, n-1, dp);

        int res = mcm_tab(arr, n);

        System.out.println("Min cost: " + res);
    }

    public static void main(String[] args) {
        long st = System.currentTimeMillis();


        int[] arr = {1,2,3,4,3};
        mcm(arr, arr.length);

        long end = System.currentTimeMillis();
        System.out.println("Run time: " + (end-st) + "ms");
    } 
    
}

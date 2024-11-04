package DP.Problems;

import java.util.*;

public class Lcs {
    

    // recursion
    public static int lis_recursion(int arr[], int idx, int prev, int n) {
        if(idx == n) return 0;

        // not take
        int len = 0 + lis_recursion(arr, idx+1, prev, n);

        // take
        if(prev == -1 || arr[idx] > arr[prev]) {
            len = Math.max(len, 1 + lis_recursion(arr, idx+1, idx, n));
        }
        return len;
    }

    // memoization
    public static int lis_mem(int arr[], int idx, int prev, int n, int dp[][]) {
        if(idx == n) return 0;

        if(dp[idx][prev+1] != -1) return dp[idx][prev+1];

        int len = 0 + lis_mem(arr, idx+1, prev, n, dp);
        if(prev == -1 || arr[idx] > arr[prev]) {
            len = Math.max(len, 1 + lis_mem(arr, idx+1, idx, n, dp));
        }

        return dp[idx][prev+1] = len;
    }

    public static int lis_tab(int arr[], int n) {
        int dp[][] = new int[n+1][n+1];

        for(int i=n-1; i>=0; i--) {
            for(int j=i-1; j>=-1; j--) {
                int len = 0 + dp[i+1][j+1];
                if(j == -1 || arr[i] > arr[j]) {
                    len = Math.max(len,1+ dp[i+1][i+1]);
                }
                dp[i][j+1] = len;
            }
        }
        return dp[0][0];
    }

    // longest inc subseq
    public static void lis(int arr[]) {
        int n = arr.length;

        // int len = lis_recursion(arr, 0, -1, n);
        int dp[][] = new int[n][n+1];
        // req coordinate change for prev arr -> 0 - n; size=n+1
        for(int row[] : dp) Arrays.fill(row, -1);
        // int len = lis_mem(arr, 0, -1, n, dp);
        // for(int row[] : dp) {
        //     for(int item : row) {
        //         System.out.print(item + " ");
        //     }
        //     System.out.println();
        // }
        int len = lis_tab(arr, n);

        System.out.println("longest inc subseq len: " + len);
    }

    // print longest inc subseq
    public static void printLis(int arr[], int n) {

    }

    // find distinct palindromic substrings - using tabulation
    public static void findStr(String s) {
        int n = s.length();
        boolean dp[][] = new boolean[n][n];
        Set<String> ans = new HashSet<>();

        for(int gap=0; gap<n; gap++) {
            for(int i=0, j=gap; j<n; i++, j++) {
                if(gap == 0) dp[i][j] = true;
                else if(gap == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                }
                else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && 
                    dp[i+1][j-1]);
                }

                if(dp[i][j]) ans.add(s.substring(i, j+1));
            }
        }

        System.out.println(ans);
    }


    public static void main(String[] args) {
        int arr[] = {3,5,7,12,1,6,13};
        // printLis(arr, arr.length);
        // lis(arr);

        findStr("abaaa");
    }
}

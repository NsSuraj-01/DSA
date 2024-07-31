package DP;

import java.util.*;

public class LCS {

    // lcs recursion : time = 2^(m+n)
    public static int lcs_rec(String s1, int n, String s2, int m) {
        if(n == 0 || m == 0) return 0;

        if(s1.charAt(n-1) == s2.charAt(m-1)) {
            return 1+lcs_rec(s1, n-1, s2, m-1); 
        } else {
            int a = lcs_rec(s1, n, s2, m-1);
            int b = lcs_rec(s1, n-1, s2, m);
            return Math.max(a, b);
        }
    }

    // lcs memoization : time = O(n*m)
    public static int lcs_mem(String s1, int n, String s2, int m, int[][] dp) {
        if(n == 0 || m == 0) {
            return 0;
        }

        if(dp[n][m] != -1) return dp[n][m];

        if(s1.charAt(n-1) == s2.charAt(m-1)) {
            return dp[n][m] = 1 + lcs_mem(s1, n-1, s2, m-1, dp); 
        } else {
            int a = lcs_mem(s1, n-1, s2, m, dp);
            int b = lcs_mem(s1, n, s2, m-1, dp);
            return dp[n][m] = Math.max(a,b);
        }
    }

    // lcs - tabulation
    public static int lcs_tab(String s1, int n, String s2, int m) {
        int[][] dp = new int[n+1][m+1];
        // init dp[i][0] = dp[0][j] = 0

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[n][m];
    }
    
    // longest common subseq
    public static void lcs(String s1,String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // int max_seq = lcs_mem(s1, n, s2, m, dp);
        int max_seq = lcs_tab(s1,n,s2,m);
        System.out.println(max_seq);
    }

    // longest common substring
    public static int lcSubstring_rec(String s1, int n, String s2, int m) {
        if(n == 0 || m == 0) return 0;

        if(s1.charAt(n-1) == s2.charAt(m-1)) {
            return 1 + lcSubstring_rec(s1, n-1, s2, m-1);
        } else {
            return 0;
        }
    }

    // longest common substring memoization
    public static int lcSubstring_tab(String s1, int n, String s2, int m) {
        int[][] dp = new int[n+1][m+1];
        // init dp[i][0] = dp[0][i] = 0
        int maxLen = 0;

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = 0;
                }
                maxLen = Math.max(dp[i][j], maxLen);
            }
        }
        return maxLen;
    }

    // longest common substring
    public static void lcSubstring(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int maxlen = lcSubstring_rec(s1,n,s2,m);
        // int maxlen = lcSubstring_tab(s1,n,s2,m);

        System.out.println(maxlen);
    }

    // longest increasing subsequence
    public static void longIncSubseq(int[] arr) {
        TreeSet<Integer> set = new TreeSet<>();
        for(int i : arr) {
            set.add(i);
        }

        int n = arr.length;
        int m = set.size();
        int[] a = new int[m];

        int k=0;
        for(int item : set) {
            a[k++] = item;
        }

        int[][] dp = new int[n+1][m+1];
        // init dp[i][0] = dp[0][j] = 0

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(arr[i-1] == a[j-1]) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        System.out.println("longest increasing subseq: " + dp[n][m]);
    }

    // Edit distance
    public static void editDistance(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n+1][m+1];

        // init
        for(int i=0; i<n+1; i++) {
            dp[i][0] = i;
        }
        for(int j=0; j<m+1; j++) {
            dp[0][j] = j;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    int add = 1 + dp[i][j-1];
                    int remove = 1 + dp[i-1][j];
                    int replace = 1 + dp[i-1][j-1];
                    dp[i][j] = Math.min(add, Math.min(remove, replace)); 
                }
            }
        }

        System.out.println("min operations: " + dp[n][m]);

    }

    // string conversion
    public static void stringConversion(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n+1][m+1];

        for(int i=0; i<n+1; i++) {
            dp[i][0] = i;
        }
        for(int j=0; j<m+1; j++) {
            dp[0][j] = j;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    int add = 1 + dp[i][j-1];
                    int remove = 1 + dp[i-1][j];
                    dp[i][j] = Math.min(add, remove);
                }
            }
        }
        System.out.println("min operations: " + dp[n][m]);
    }

    // wildcard match - tabulation
    public static boolean wildcard_tab(String str, String pattern) {
        int n = str.length(), m = pattern.length();
        boolean[][] dp = new boolean[n+1][m+1];

        // init
        dp[0][0] = true;
        for(int i=1; i<n+1; i++) {
            dp[i][0] = false;
        }
        for(int j=1; j<m+1; j++) {
            dp[0][j] = (pattern.charAt(j-1) == '*') ? dp[0][j-1] : false ; 
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                char ch1 = str.charAt(i-1);
                char ch2 = pattern.charAt(j-1);

                if((ch1 == ch2) || ch2 == '?') {
                    dp[i][j] = dp[i-1][j-1];
                } 
                else if(ch2 == '*') {
                    // ignore * ; match with empty str
                    boolean ignore = dp[i][j-1];
                    // consider * ; for future use -> to match seq
                    boolean consider = dp[i-1][j];
                    dp[i][j] = ignore || consider;
                } 
                else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[n][m];
    }

    // wildcard matching
    public static void wildcardMatching(String str, String pattern) {
        if(wildcard_tab(str,pattern)) {
            System.out.println("Match found");
        } else {
            System.out.println("No match");
        }
    }


    public static void main(String[] args) {
        // lcSubstring("abcdge", "acdge");
        // int[] arr = {50,3,10,7,40,80};
        // longIncSubseq(arr);

        // editDistance("intention", "execution");
        // stringConversion("pear", "sea");
        wildcardMatching("abc","*c");
        
    }
}

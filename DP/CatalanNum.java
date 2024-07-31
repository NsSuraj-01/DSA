package DP;

public class CatalanNum {
    
    // catalan number recursion
    public static int catNum_rec(int n) {
        if(n == 0 || n == 1) return 1;

        int ans = 0;
        for(int i=0; i<n; i++) {
            ans += catNum_rec(i) * catNum_rec(n-1-i);
        }

        return ans;
    }

    // catalan num mem - O(n^2)
    public static int catNum_mem(int n, int[] dp) {
        if(n == 0 || n == 1) return 1;

        if(dp[n] != 0) return dp[n];

        int ans = 0;
        for(int i=0; i<n; i++) {
            ans += catNum_mem(i, dp) * catNum_mem(n-1-i, dp);
        }

        return dp[n] = ans;
    }

    // catalan num tab - O(n^2)
    public static int catNum_tab(int n) {
        int[] dp = new int[n+1];
        dp[0] = dp[1] = 1;

        for(int i=2; i<n+1; i++) {
            for(int j=0; j<i; j++) {
                dp[i] += dp[j]*dp[i-1-j];
            }
        }

        return dp[n];
    }

    // catalan number
    public static void catalanNum(int n) {
        // int num = catNum_rec(n);
        // int num = catNum_mem(n, new int[n+1]);
        int num = catNum_tab(n);
        System.out.println(num);
    }

    // count bst's - tabulation; time = O(n^2)
    public static int countBST_tab(int n) {
        int[] dp = new int[n+1];
        dp[0] = dp[1] = 1;

        for(int i=2; i<n+1; i++) {
            for(int j=0; j<i; j++) {
                int left = dp[j]; // left subtree
                int right = dp[i-1-j]; // right subtree
                dp[i] += left*right;
            }
        }

        return dp[n];
    }

    // mountain ranges
    public static void mountainRanges(int n) {
        int num = catNum_tab(n);
        System.out.println(num);
    }

    // count num of bst's
    public static void countBST(int n) {
        int cnt = countBST_tab(n);
        System.out.println("Num of BSTs: " + cnt);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // catalanNum(15);
        countBST(5);





        long end = System.currentTimeMillis();
        System.out.println("Run time: " + (end-start) + "ms");

    }
}

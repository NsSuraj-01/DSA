package DP;

public class Fibonacci {

    // 1. Fibonacci series
    // recursion
    public static int fibonacci_recursion(int n) {
        if(n == 0 || n == 1) {
            return n;
        }

        return fibonacci_recursion(n-1) + fibonacci_recursion(n-2);
    }

    // memoization
    public static int fibonacci_mem(int n, int[] dp) {
        if(n == 0 || n == 1) return n;

        if(dp[n] != 0) {
            return dp[n];
        }

        dp[n] = fibonacci_mem(n-1, dp) + fibonacci_mem(n-2, dp) ;
        return dp[n];
    }

    // tabulation
    public static int fib_tab(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0; dp[1] = 1;

        for(int i=2; i<=n; i++) {
            dp[i] = dp[i-1]+dp[i-2];
        }

        return dp[n];
    }

    public static void find_fibonacciNumber(int n) {
        int[] dp = new int[n+1];

        int num = fib_tab(n);
        System.out.println(num);
    }

    // 2. Climbing stairs problem
    // recursion
    public static int climbing_rec(int n) {
        if(n<0) return 0;
        if(n==0 || n==1) return 1;

        return climbing_rec(n-1) + climbing_rec(n-2);
    }

    // memoization
    public static int climb_mem(int n, int[]dp) {
        if(n < 0) return 0;
        if(n == 0 || n == 1) return 1;
        
        if(dp[n] != 0) return dp[n];

        dp[n] = climb_mem(n-1, dp) + climb_mem(n-2, dp);
        return dp[n];
    }

    // tabulation
    public static int climb_tab(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1; dp[1] = 1;
        
        for(int i=2; i<=n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    public static void climbing_stairs(int n) {
        int[] dp = new int[n+1];
        // int ways = climbing_rec(n);
        // int ways = climb_mem(n, dp);
        int ways = climb_tab(n);
        System.out.println(ways);
    }


    public static void main(String[] args) {
        // find_fibonacciNumber(4);
        climbing_stairs(5);
    }
    
}

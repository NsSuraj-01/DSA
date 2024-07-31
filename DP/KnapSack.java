package DP;

import java.util.*;

public class KnapSack {

    // 0-1 knapsack recursion -> time = exponential
    public static int knap_recursion(int[] val, int[] wt, int W, int n) {
        if(W == 0 || n == 0) {
            return 0;
        }

        if(wt[n-1] <= W) {
            int ans1 = val[n-1] + knap_recursion(val, wt, W-wt[n-1], n-1);
            int ans2 = knap_recursion(val, wt, W, n-1);
            return Math.max(ans1, ans2);
        } 

        else {
            return knap_recursion(val, wt, W, n-1);
        }
    }

    // memoization -> time = O(n*W)
    public static int knap_mem(int[] wt, int[] val, int W, int n, int[][] dp) {
        if(W == 0 || n == 0) {
            return 0;
        }

        if(dp[n][W] != -1) return dp[n][W];

        if(wt[n-1] <= W) {
            int include = val[n-1] + knap_mem(wt, val, W-wt[n-1], n-1, dp);
            int exclude = knap_mem(wt, val, W, n-1, dp);
            dp[n][W] = Math.max(include, exclude);
            return dp[n][W];
        }

        else {
            return dp[n][W] = knap_mem(wt, val, W, n-1, dp);
        }

    }

    public static int knap_tab(int[] wt, int[] val, int W, int n) {
        int[][] dp = new int[n+1][W+1];
        for(int i=0; i<=n; i++) {
            dp[i][0] = 0;
        }
        for(int j=0; j<=W; j++) {
            dp[0][j] = 0;
        }

        for(int i=1; i<=n; i++) {
            for(int j=1; j<=W; j++) {
                int value = val[i-1];
                int weight = wt[i-1];

                if(weight <= j) {
                    // include
                    int inc = value + dp[i-1][j-weight];
                    // exclude
                    int exc = dp[i-1][j];
                    dp[i][j] = Math.max(inc, exc);
                }
                else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        for(int[] row : dp) {
            for(int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }

        return dp[n][W];
    }

    // space optimization
    public static int knap_spaceOpt(int[] wt, int[] val, int W, int n) {
        int[] prev = new int[W+1];
        int[] curr = new int[W+1];
        

        for(int i=1; i<=n; i++) {
            for(int j=1; j<=W; j++) {
                int value = val[i-1];
                int weight = wt[i-1];

                if(weight <= j) {
                    // include
                    int inc = value + prev[j-weight];
                    // exclude
                    int exc = prev[j];
                    curr[j] = Math.max(inc, exc);
                }
                else {
                    curr[j] = prev[j];
                }
            }
            
            for(int k=0; k<W+1; k++) {
                prev[k] = curr[k];
            }
        }

        return prev[W];
    }

    // unbounded knapsack
    public static int unbounded_knapsack(int[] wt, int[] val, int W, int n) {
        int[][] dp = new int[n+1][W+1];
        for(int i=0; i<=n; i++) {
            dp[i][0] = 0;
        }
        for(int j=0; j<=W; j++) {
            dp[0][j] = 0;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<W+1; j++) {
                int weight = wt[i-1];
                int value = val[i-1];

                if(weight <= j) {
                    int inc = value + dp[i][j-weight];
                    int exc = dp[i-1][j]; 
                    dp[i][j] = Math.max(inc, exc);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][W];
    }



    // 0-1 knapsack
    public static void knapsack(int[] wt, int[] val, int W, int n) {
        int[][] dp = new int[n+1][W+1];
        for(int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // int profit = knap_recursion(val, wt, W, n);
        // int profit = knap_mem(wt, val, W, n, dp);
        int profit = knap_tab(wt, val, W, n);
        int pr = knap_spaceOpt(wt, val, W, n);
        // int profit = unbounded_knapsack(wt, val, W, n);
        System.out.println("tabulation " + profit);
        System.out.println("space opt: " + pr);
    }

    // target sum subset tab
    // 1<=arr[i]<=1000
    public static boolean subsetSum_tab(int[] arr, int n, int target) {
        boolean[][] dp = new boolean[n+1][target+1];

        
        for(int i=0; i<n+1; i++) {
            dp[i][0] = true;
        }
        if(arr[0] <= target) dp[1][arr[0]] = true;

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<target+1; j++) {
                int value = arr[i-1];

                // include
                if(value <= j && dp[i-1][j-value]) {
                    dp[i][j] = true;
                }
                // exclude
                else if(dp[i-1][j]) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[n][target];
    }



    // target sum subset
    public static void targetSum(int[] arr, int n, int target) {
        
        boolean isExist = subsetSum_tab(arr, n, target);
        
        if(isExist) {
            System.out.println("Subset found");
            return;
        }
        System.out.println("No subset found");

    }

    // target sum subset2 - count no of subsets
    // 0 <= nums[i] <= 1000
    public static void perfectSum_tab(int arr[],int n, int sum) 
	{ 
	    int mod = (int)1e9 + 5;
	    // Your code goes here
	    int[][] dp = new int[n+1][sum+1];
	    
        // init
        for(int j=1; j<sum+1; j++) {
            dp[0][j] = 0;
        }
        // if sum=0, count is atleast 1. Remaining possibilities 
        // is calculated in the loops
	    for(int i=0; i<n+1; i++) {
	        dp[i][0] = 1;
	    }
	    
	    for(int i=1; i<n+1; i++) {
            // if items in arr = 0
	        for(int j=0; j<sum+1; j++) {
	            int val = arr[i-1];
	            
	            if(val <= j) {
	                dp[i][j] = (dp[i-1][j-val] + dp[i-1][j]) % mod;
	            } else {
	                dp[i][j] = dp[i-1][j];
	            }
	        }
	    }
	    
	    System.out.println("num of subsets: " + dp[n][sum]);
	}

    public static int perfectSum_mem(int[] nums,int i, int sum, int[][] dp) {
        if(i < 0) return 0;
        if(i == 0) {
            if(nums[i] == 0) return 2;
            if(sum == 0 || sum == nums[0]) return 1;
        }

        if(dp[i][sum] != -1) return dp[i][sum];

        int notTake = perfectSum_mem(nums, i-1, sum, dp);
        int take = 0;
        if(nums[i] <= sum) {
            take = perfectSum_mem(nums, i-1, sum-nums[i], dp);
        }

        return dp[i][sum] = take + notTake;
    }
    
    public static void perfectSum(int[] nums, int n, int sum) {
        int[][] dp = new int[n][sum+1];
        for(int[] row : dp) Arrays.fill(row, -1);
        int ans = perfectSum_mem(nums, n-1, sum, dp);
        System.out.println(ans);
    }

    // coin change recursion
    public static boolean coinchange_rec(int[] coins, int n, int sum, ArrayList<Integer> list) {
        if(n < 0) return false;

        if(n >= 0 && sum == 0) {
            System.out.println(list);
            return true;
        }
        if(n == 0) return false;

        int value = coins[n-1];
        if(value <= sum) {
            list.add(value);
            boolean inc = coinchange_rec(coins, n, sum-value, list);

            list.remove(list.size()-1);
            boolean exc = coinchange_rec(coins, n-1, sum, list);
            
            return inc || exc ;
        }
        else {
            return coinchange_rec(coins, n-1, sum, list) ;
        }
    }

    // coin change tab
    public static int coinchange_tab(int[] coins, int n, int sum) {
        int[][] dp = new int[n+1][sum+1];
        // init dp[i][0]=1 , dp[0][i] = 0
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 1;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<sum+1; j++) {
                int value = coins[i-1];
                if(value <= j) {
                    dp[i][j] = dp[i][j-value] + dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][sum];
    }

    public static int rodcut_tab(int[] pieces, int[] prices, int len) {
        int n = prices.length;
        int[][] dp = new int[n+1][len+1];

        // init dp[i][0] = dp[0][i] = 0;

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<len+1; j++) {
                int price = prices[i-1];
                int length = pieces[i-1];

                if(length <= j) {
                    int inc = price + dp[i][j-length];
                    int exc = dp[i-1][j];
                    dp[i][j] = Math.max(inc, exc);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][len];
        
    }

    // rod cutting
    public static void rodCutting(int[] pieces, int[] prices, int len) {
        int maxProfit = rodcut_tab(pieces, prices, len);
        System.out.println(maxProfit);
    }




    // Coin change
    public static void coinChange(int[] coins, int n, int sum) {
        // coinchange_rec(coins, n, sum, new ArrayList<>() );

        int ways = coinchange_tab(coins, n, sum);
        System.out.println(ways);
    }



    public static void main(String[] args) {
        int[] wt = {2,5,1,3,4};
        int[] val = {15,14,10,45,30};
        int W = 7;

        // knapsack(wt, val, W, wt.length);

        int[] coins = {2,5,3,6};
        // int sum = 10;
        // coinChange(coins, coins.length, sum);

        // int[] nums = {4,2,7,2,11};
        // int target = 5;
        // targetSum(nums, nums.length, target);

        int[] pieces = {1,2,3,4,5,6,7,8};
        int[] prices = {1,5,8,9,10,17,17,20};
        int len = 8;
        rodCutting(pieces, prices, len);

        int[] arr = {9, 7, 0, 3, 9, 8, 6, 5, 7, 6};
        int sum = 31;
        // perfectSum_tab(arr, arr.length, sum);
        // perfectSum(arr, arr.length, sum);
    }
    
}

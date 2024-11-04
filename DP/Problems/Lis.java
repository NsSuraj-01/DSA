package DP.Problems;

import java.util.*;
public class Lis {

    public static int lis_helper(int arr[], int id, int prev) {
        if(id == arr.length) return 0;
        
        int notTake = 0 + lis_helper(arr, id+1, prev);
        
        int take = 0;
        if(prev == -1 || arr[id] > arr[prev]) {
            take = 1 + lis_helper(arr, id+1, id);
        }

        return Math.max(take, notTake);
    }
    
    // len_lis rec
    public static void lis(int arr[]) {
        int len = lis_helper(arr, 0, -1);
        System.out.println("length of lis: " + len);
    }

    // 1. get lis : time =  O(n ^ 2), space = O(n)
    public static int len_lis(int arr[]) {
        int n = arr.length;
        int dp[] = new int[n];
        Arrays.fill(dp, 1);

        int len = 1;

        for(int i=0; i<n; i++) {
            for(int prev = 0; prev < i; prev++) {
                if(arr[i] > arr[prev]) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }
            len = Math.max(len, dp[i]);
        }

        System.out.println("len of lis: " + len);
        return len;
    }

    // 2. print lis
    public static void printLis(int arr[]) {
        int n = arr.length;
        List<Integer> lis = new ArrayList<>();

        int dp[] = new int[n];
        int hash[] = new int[n];

        Arrays.fill(dp, 1);

        int ans = 1, lastidx = 0;

        for(int i=0; i<n; i++) {
            hash[i] = i;
            for(int prev=0; prev<i; prev++) {

                if(arr[prev] < arr[i] && dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                    hash[i] = prev;
                }
            }

            if(ans < dp[i]) {
                ans = dp[i];
                lastidx = i;
            }
        }

        System.out.println("len of lis: " + ans );

        lis.add(0, arr[lastidx]);
        while(hash[lastidx] != lastidx) {
            lastidx = hash[lastidx];
            lis.add(0, arr[lastidx]);
        }

        System.out.println(lis);
    }

    public static int lowerBound(List<Integer> arr, int num) {
        if(arr.size()== 0) return -1;

        int low = 0, high = arr.size() - 1;

        while(low <= high) {
            int mid = (low + high) >> 1;

            if(arr.get(mid) > num) {
                high = mid-1;
            } else low = mid+1;
        }
        return low;
    }

    // 3. get lis - optimal
    // Patience sorting - O(n * logn)
    public static int lenLis_opt(int arr[]) {
        int n = arr.length;
        List<Integer> seq = new ArrayList<>();
        int len = 1;
        seq.add(arr[0]);

        for(int i=1; i<n; i++) {
            if(seq.get(seq.size()-1) < arr[i]) {
                seq.add(arr[i]);
                len++;
            }
            else {
                // int idx = Collections.binarySearch(seq, arr[i]);
                int idx = lowerBound(seq, arr[i]);
                if(idx < 0) idx = 0;
                seq.set(idx, arr[i]);
            }
        }

        System.out.println("len of lis: " + len);
        return len;
    }

    // 5. longest div subset
    public static int longDivSubset(int arr[]) {
        int n = arr.length;
        Arrays.sort(arr);

        int dp[] = new int[n];
        int hash[] = new int[n];
        Arrays.fill(dp, 1);

        int len = 1, lastidx = 0;

        for(int i=0; i<n; i++) {
            hash[i] = i;
            for(int prev = i-1; prev >= 0; prev-- ) {

                if(arr[i] % arr[prev] == 0 && 
                dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                    hash[i] = prev;
                    break;
                }
            }

            if(len < dp[i]) {
                len = dp[i];
                lastidx = i;
            }
        }

        List<Integer> ans = new ArrayList<>();
        ans.add(0, arr[lastidx]);

        while(hash[lastidx] != lastidx) {
            lastidx = hash[lastidx];
            ans.add(0, arr[lastidx]);
        }
        System.out.println("len of longest div seq: " + len);
        System.out.println(ans);

        return len;
    }

    // 6. longest string chain
    public static int longestStringChain(String words[]) {
        Arrays.sort(words, (s1, s2) -> (s1.length() - s2.length() ));
        
        Map<String, Integer> dp = new HashMap<>();
        int maxlen = 1;
        String ans = "";

        for(int i=0; i<words.length; i++) {
            String word = words[i];
            int len = 1;
            for(int j=0; j<word.length(); j++) {
                String substr = word.substring(0, j) + word.substring(j+1);
                if(dp.containsKey(substr)) {
                    len = Math.max(len, 1 + dp.get(substr));
                }
            }
            dp.put(word, len);
            if(len > maxlen) {
                ans = word;
                maxlen = len;
            }
        }

        System.out.println("longest chain: " + maxlen);
        System.out.println("string: " + ans);
        return maxlen;
    }

    // 7. longest decreasing subseq
    public static int lds(int arr[]) {
        int n = arr.length;
        int dp[] = new int[n];

        Arrays.fill(dp, 1);
        int len = 1;

        for(int i=0; i<n; i++) {
            for(int prev=0; prev < i; prev++) {
                if(arr[prev] < arr[i] && dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                }
            }
            len = Math.max(len, dp[i]);
        }

        System.out.println("len of lds: " + len);
        return len;
    }

    // 8. longest bitonic subseq
    public static int longestBitonicSubseq(int arr[]) {
        int n = arr.length;

        int dp1[] = new int[n];

        Arrays.fill(dp1, 1);
        int len = 1;

        for(int i=0; i<n; i++) {
            for(int prev=0; prev < i; prev++) {
                if(arr[prev] < arr[i] && dp1[i] < 1 + dp1[prev]) {
                    dp1[i] = 1 + dp1[prev];
                }
            }
        }

        int dp2[] = new int[n];

        Arrays.fill(dp2, 1);

        for(int i = n-1; i >= 0; i--) {
            for(int prev=n-1; prev > i; prev--) {
                if(arr[prev] < arr[i] && dp2[i] < 1 + dp2[prev]) {
                    dp2[i] = 1 + dp2[prev];
                }
            }
        }

        for(int i=0; i<n; i++) {
            if(dp1[i] > 1 && dp2[i] > 1) {
                len = Math.max(len, dp1[i] + dp2[i] - 1);
            }
        }
        System.out.println("len of longest bitonic subseq: " + len);
        return len;
    }

    // 9. count num of lis
    public static int countLis(int arr[]) {
        int n = arr.length;
        int dp[] = new int[n];
        Arrays.fill(dp, 1);

        int cnt[] = new int[n];
        Arrays.fill(cnt, 1);

        int len = 1;

        for(int i=0; i<n; i++) {
            for(int prev = 0; prev < i; prev++) {
                if(arr[prev] < arr[i] && dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                    // inherit
                    cnt[i] = cnt[prev];
                }
                else if(arr[prev] < arr[i] && dp[i] == 1 + dp[prev]) {
                    // include
                    cnt[i] += cnt[prev];
                }
            }
            len = Math.max(len, dp[i]);
        }
        
        int ans = 0;
        for(int i=0; i<n; i++) {
            if(dp[i] == len) {
                ans += cnt[i];
            }
        }

        System.out.println("count of lis: " + ans);
        return ans;
        
    }


    public static void main(String[] args) {
        // int arr[] ={0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};
        // len_lis(arr);
        // printLis(arr);
        // lenLis_opt(arr);

        // int arr[] = {3,4,16,8};
        // longDivSubset(arr);

        // String words[] = {"a", "wxz", "ba", "bda", "wxzy", "bdac"};
        // longestStringChain(words);

        int arr[] = {1,5,4,3,2,6,7,10,8,9};
        // longestBitonicSubseq(arr);

        countLis(arr);
        lis(arr);
    }
}

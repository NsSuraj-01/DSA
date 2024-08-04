package SlidingWindow;

import java.util.*;

public class Understanding {
    
    // ----- Fixed len window problems -----

    // 1. max/min subarr sum of size = k -> time = O(N)
    public static void maxSubArrSum(int arr[], int k) {
        int maxSum = 0, minSum=(int)1e9;
        int n = arr.length;
        
        if(n == 0) return;

        int sum=0;
        int i=0;

        for(; i<k; i++) {
            sum += arr[i];
        }
        maxSum = Math.max(sum, maxSum);
        minSum = Math.min(sum, minSum);

        for(i=1; i+k-1 < n; i++) {
            // remove leading and add trailing
            sum -= arr[i-1];
            sum += arr[i+k-1];
            maxSum = Math.max(sum,maxSum);
            minSum = Math.min(minSum, sum);
        }
        System.out.println("max sum: " + maxSum);
        System.out.println("min sum: " + minSum);
    }

    // 2. first neg number of window size = k
    // time = O(N), space = O(k)
    public static void firstNeg(int arr[], int k) {
        int i=0;
        int n = arr.length;

        Queue<Integer> q = new LinkedList<>();

        for(; i<k; i++) {
            if(arr[i] < 0) q.add(arr[i]);
        }

        int first = q.isEmpty() ? 0 : q.peek();
        System.out.print(first + " ");

        for(i=1; i+k-1 < n; i++) {
            if(arr[i-1] < 0) q.remove(); 

            first = q.isEmpty() ? 0 : q.peek();
            System.out.print(first + " ");

            if(arr[i+k-1] < 0) q.add(arr[i+k-1]);
        }
    }

    // 3. count the occurences of anagrams
    public static void countOccurencesAnagrams(String str, String pattern) {
        Map<Character, Integer> mpp = new HashMap<>();
        int count = 0; // num of anagrams in string
        int k = pattern.length();
        int n = str.length();

             

        for(int i=0 ; i<k; i++) {
            char ch = pattern.charAt(i);
            mpp.put(ch, mpp.getOrDefault(ch, 0) + 1);
        }

        int cnt_map = mpp.size();

        int i=0;
        for(; i<k; i++) {
            char ch = str.charAt(i);
            mpp.put(ch, mpp.get(ch) - 1);
            if(mpp.get(ch) == 0) cnt_map--;
        }

        for(i=1; i+k-1 < n; i++) {
            char leading = str.charAt(i-1);
            mpp.put(leading, mpp.get(leading) + 1);
            if(mpp.get(leading) == 1) cnt_map++;

            char trailing = str.charAt(i+k-1);
            mpp.put(trailing, mpp.get(trailing) - 1);
            if(mpp.get(trailing) == 0) cnt_map--;

            if(cnt_map == 0) count++;
        }

        System.out.println("anagram count: " + count);
    }

    public static void main(String[] args) {
        // int arr[] = {1,3,9,4,2,6,8,1};
        // maxSubArrSum(arr, 3);

        // int arr[] = {-2,1,-3,4,-1,2,1,5,4};
        // firstNeg(arr, 3);

        String str = "baatbt";
        countOccurencesAnagrams(str, "bat");
    }
    
}

package BinarySearch;
import java.util.*;

public class BinarySearch {

    public static boolean isPossible(int bloom[], int m, int k, int day) {
        int cnt=0, numB = 0;
        for(int i=0; i<bloom.length; i++) {
            if(bloom[i] <= day) cnt += 1;
            else {
                numB += (cnt/k);
                cnt = 0;
            }
        }
        numB += cnt/k;
        return numB >= m ;
    }

    
    // bouquets binary search
    public static void minDays(int bloom[], int m, int k) {
        int mini = bloom[0], maxi = bloom[0];

        for(int i : bloom) {
            mini = Math.min(mini, i);
            maxi = Math.max(i, maxi);
        }

        int low = mini, high = maxi;

        while(low <= high) {
            int mid = low + (high-low)/2;

            if(isPossible(bloom, m, k, mid)) high = mid-1;
            else low = mid+1;
        }

        System.out.println("min num of days: " + low);
    }


    // packages to ship - binary search
    public static boolean isPoss(int[] wts, int capacity, int max) {
        int dayCnt = 1, load = 0;
       
        for(int wt : wts) {
            if(load + wt > capacity) {
                dayCnt++;
                load = wt;
            } else {
                load += wt;
            }
        }

        return dayCnt <= max;
    } 

    public static void shipWithinDays(int[] weights, int days) {
        int low = weights[0], high = 0;

        for(int i : weights) {
            low = Math.max(i, low);
            high += i;
        }

        while(low <= high) {
            int mid = low + (high-low)/2;

            if(isPoss(weights, mid, days)) high = mid-1;
            else low = mid+1;
        }
        System.out.println("min num of days - " + low);
    }

    public static boolean cntCows(int[] nums, int dist, int cows) {
        int cnt = 1, prev = nums[0];

        for(int i=1; i<nums.length; i++) {
            if(nums[i] - prev >= dist) {
                cnt++;
                prev = nums[i];
            } 

            if(cnt >= cows) return true;
        }

        return false;
       
    }

    // aggressive cows
    public static void distance(int[] stalls, int cows) {
        int n = stalls.length;
        Arrays.sort(stalls);
        int low = 1;
        int high = stalls[n-1] - stalls[0];


        while(low <= high) {
            int mid = low + ((high-low)>>1);
            // mid - min dist

            if(cntCows(stalls, mid, cows)) low = mid+1;
            else high = mid-1;
        }
        System.out.println("min dist - " + high);
    }

    public static boolean splitArrHelper(int[] arr, int k, int sum) {
        int cur = 0, arrCnt = 1;
        for(int i=0; i<arr.length; i++) {
            if(cur + arr[i] > sum) {
                cur = arr[i];
                arrCnt += 1;
                if(arrCnt > k) return false;
            } else {
                cur += arr[i];
            }
        }
        return true;
    }

    // split arr - largest Sum
    public static void splitArr(int[] arr, int k) {
        int low = arr[0], high = 0;
        for(int num : arr) {
            low = Math.max(low, num);
            high += num;
        }

        while(low <= high) {
            int mid = low + ((high-low) >> 1);

            if(splitArrHelper(arr, k, mid)) high = mid-1;
            else low = mid+1;
        }
        System.out.println("minimized max sum - " + low);
    } 

    // max gas stations - O(k*n)
    public static void maxStations_bruteforce(int dist[], int k) {
        int n = dist.length;
        int sectionDist[] = new int[n-1];

        for(int j=1; j<=k; j++) {

            double maxLen = -1;
            int maxIdx = -1;
            for(int i=0; i<n-1; i++) {
                double diff = (dist[i+1] - dist[i]);
                double sectionLen = diff / (double)(sectionDist[i]+1);
                if(maxLen < sectionLen) {
                    maxIdx = i;
                    maxLen = sectionLen;
                }
            }

            sectionDist[maxIdx]++;
        }

        double ans = -1;
        for(int i=0; i<n-1; i++) {
            double diff = dist[i+1] - dist[i];
            double len = diff / (double)(sectionDist[i]+1);
            ans = Math.max(ans, len); 
        }

        ans = Math.round(ans*100)/(double)100;
        System.out.println("max dist: " + ans);
    } 

    static class Pair {
        double val; int idx;
        public Pair(double val, int idx) {
            this.val = val;
            this.idx = idx;
        }
    }

    // gas stations - better - O(n * logN)
    public static void maxStations_better(int dist[], int k) {
        int n = dist.length;
        int sectionDist[] = new int[n-1];
        PriorityQueue<Pair> pq = new PriorityQueue<>(
            Comparator.comparingDouble((Pair p) -> p.val).reversed()
        );

        for(int i=0; i<n-1; i++) {
            pq.add(new Pair(dist[i+1] - dist[i], i));
        }

        for(int j=1; j<=k; j++) {
            Pair cur = pq.remove();
            int idx = cur.idx;

            sectionDist[idx]++;
            double diff = dist[idx+1] - dist[idx];
            double newSectionLen = diff / (double)(sectionDist[idx]+1);
            pq.add(new Pair(newSectionLen, idx));
        }

        double ans = pq.peek().val;
        ans = Math.round(ans*100) / (double)100 ;
        System.out.println("max dist: " + ans);
    } 

    public static void main(String[] args) {
        double st = System.currentTimeMillis();

        // splitArr(new int[]{1,2,3,4,5}, 3);
        int arr[] = {1,2,3,4,5,6,7,8,9,10};
        // maxStations_bruteforce(arr, 5);
        maxStations_better(arr, 9);


        double end = System.currentTimeMillis();
        System.out.println("Time - " + (end-st));
    }
    
}

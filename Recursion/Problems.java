package Recursion;

import java.util.*;
public class AcProblems {
    

    public static void helperBinStrings(int n, String ans, int prev) {
        if(ans.length() == n) {
            System.out.println(ans);
            return;
        }

        if(prev == 0) helperBinStrings(n, ans+1, 1);
        helperBinStrings(n, ans+0, 0);
    }

    // generate n-len binary strings with no consec 1's
    public static void generate(int n) {
        helperBinStrings(n, "", 0);
    }
    
    public static int friendsHelper(int n) {
        if(n <= 2) return n;

        int single = 1 * friendsHelper(n-1);
        int pair = (n-1) * friendsHelper(n-2);
        return single+pair;
    }

    // friends pairing problem - number of ways n friends can be paired 
    // or remains single
    public static void friendsPairing(int n) {
        int ways = friendsHelper(n);
        System.out.println("num of ways: " + ways);
    }

    public static int tilingHelper(int n) {
        if(n == 0 || n == 1) return 1;

        int vertical = tilingHelper(n-1);
        int horizontal = tilingHelper(n-2);

        return vertical + horizontal;
    }

    // tiling problem - given m*n floor, number of ways tiles(m*1) can
    // be placed on the floor
    public static void tilingProblem(int n) {
        int ways = tilingHelper(n);
        System.out.println("min ways: " + ways);
    }

    public static void main(String[] args) {
        // generate(3);
        // friendsPairing(3);
        tilingProblem(3);
    }
}

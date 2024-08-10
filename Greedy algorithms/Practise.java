package Greedy;

import java.util.*;
public class Practise {
    
    // chocola problem - cut the chocolate of size n*m to 1*1 pieces 
    // with min cost
    public static void chocolaProblem(Integer ver[], Integer hor[]) {
        Arrays.sort(ver, Collections.reverseOrder());
        Arrays.sort(hor, Collections.reverseOrder());

        int h=0, v=0;
        int hpieces=1, vpieces=1;

        int minCost = 0;

        while(h < hor.length && v < ver.length) {
            if(ver[v] > hor[h]) { // cut vertical piece first
                minCost += (ver[v]*hpieces);
                vpieces++;
                v++;
            } else { // cut horizontal piece first
                minCost += (hor[h] * vpieces);
                hpieces++;
                h++;
            }
        }

        // cut the remaining pieces
        while(h < hor.length) {
            minCost += (hor[h] * vpieces);
            hpieces++;
            h++;
        }

        while(v < ver.length) {
            minCost += (ver[v] * hpieces);
            vpieces++;
            v++;
        }

        System.out.println("min cost: " + minCost);
    }

    // lemonade change
    public static boolean lemonadeChange(int bills[]) {
        int five = 0, ten = 0;

        for(int i=0; i<bills.length; i++) {
            if(bills[i] == 5) {
                five++;
            } else if(bills[i] == 10) {
                if(five >= 1) {
                    ten++; five--;
                } else return false;
            } else if(bills[i] == 20) {
                if(five >= 1 && ten >= 1) {
                    five--; ten--;
                } else if(five >= 3) {
                    five -= 3;
                } else return false;
            }
        }
        return true;
    }


    // given a page reference, find number of page faults using lru
    public static void pgFaultCount(int pages[], int capacity) {
        int n = pages.length;
        Deque<Integer> dq = new LinkedList<>();
        int pf = 0;

        Map<Integer, Boolean> lookup = new HashMap<>();

        int i=0;
        for(; i<n; i++) {
            if(!lookup.containsKey(pages[i])) {
                pf++;
                if(dq.size() < capacity) { 
                    dq.addLast(pages[i]);
                    lookup.put(pages[i], true);
                }
                else {
                    int item = dq.getFirst();
                    dq.removeFirst();
                    lookup.remove(item);

                    dq.addLast(pages[i]);
                    lookup.put(pages[i], true);
                }
            }

            else {
                dq.remove(pages[i]);
                dq.addLast(pages[i]);
            }
        }

        System.out.println("num of pg faults: " + pf);
    }
    

    // insert interval(merge if overlapping) - leetcode 57
    public static void insertInterval(int intervals[][], int newInterval[]) {
        // 1. add new interval
        int n = intervals.length;
        int temp[][] = new int[n+1][2];

        int i=0;
        while(i<n) {
            while(i<n && intervals[i][0] < newInterval[0]) {
                temp[i] = new int[]{intervals[i][0],intervals[i][1]};
                i++;
            }
            temp[i] = new int[]{newInterval[0], newInterval[1]};

            while(i<n) {
                temp[i+1] = new int[]{intervals[i][0], intervals[i][1]};
                i++;
            }

        }

        // 2. merge overlapping 
        List<List<Integer>> ans = new ArrayList<>();
        
        int m = temp.length;
        for(i=0; i<m; i++) {
            int st = temp[i][0], end = temp[i][1];

            while(i+1 < m && end >= temp[i+1][0]) {
                end = Math.max(end, temp[i+1][1]);
                i++;
            }
            ans.add(new ArrayList<>(List.of(st, end)));
        }

        System.out.println(ans);
    }
    
    public static void main(String[] args) {
        // Integer hcost[] = {4,1,2};
        // Integer vcost[] = {2,1,3,1,4};
        // chocolaProblem(hcost, vcost);

        // int bills[] = {5,5,5,5,10,5,10,10,10,20};
        // System.out.println(lemonadeChange(bills));

        // int pages[] = {5,0,1,3,2,4,1,0,5};
        // pgFaultCount(pages, 4);

        // int intervals[][] = {{1,2},{3,5},{6,7},{8,10},
        //                     {12,16}};
        int intervals[][] = {{1,5}};
        int newInterval[] = {4,8};
        insertInterval(intervals, newInterval); 
    }
}

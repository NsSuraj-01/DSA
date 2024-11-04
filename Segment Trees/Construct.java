package SegmentTrees;

import java.util.*;
public class Construct {
    
    static class SegmentTree {
        int tree[];

        public SegmentTree(int arr[], int n) {
            tree = new int[4*n]; // for safety purpose
            buildTree(arr, 0, 0, n-1);
        }

        // O(N)
        public int buildTree(int arr[], int idx, int si, int ei) {
            if(si == ei) {
                tree[idx] = arr[si];
                return arr[si];
            }
            
            int mid = (si+ei) >> 1;
            buildTree(arr, 2*idx+1, si, mid);
            buildTree(arr, 2*idx+2, mid+1, ei);

            tree[idx] = tree[2*idx+1] + tree[2*idx+2];
            return tree[idx];
        }

        public int getSumHelper(int idx, int si, int sj, int qi, int qj) {
            if(qj < si || qi > sj) { // non overlapping
                return 0;
            } 
            else if(si >= qi && sj <= qj) { // complete overlap
                return tree[idx];
            } 
            else { // partial overlap
                int mid = (si + sj) / 2;
                int left = getSumHelper(2*idx+1, si, mid, qi, qj);
                int right = getSumHelper(2*idx+2, mid+1, sj, qi, qj);

                return left + right ;
            }
        }

        // get sum in range - O(log N)
        public int getSum(int arr[], int qi, int qj) {
            return getSumHelper(0, 0, arr.length-1, qi, qj);
        }

        public void updateHelper(int i, int idx, int si, int sj, int diff) {
            if(idx < si || idx > sj) { // non overlapping
                return;
            }

            tree[i] += diff;
            
            if(si != sj) { // non leaf nodes
                int mid = (si+sj) / 2;
                updateHelper(2*i+1, idx, si, mid, diff);
                updateHelper(2*i+2, idx, mid+1, sj, diff);
            }
        }

        // update item
        public void update(int arr[], int targetIdx, int newVal) {
            int diff = newVal - arr[targetIdx];
            arr[targetIdx] = newVal;
            updateHelper(0,targetIdx, 0, arr.length-1, diff);
        }


        public void printTree() {
            for(int i : tree) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        int arr[] = {2,3,6,8,12,15};
        SegmentTree tree = new SegmentTree(arr, arr.length);
        tree.printTree();
        int sum = tree.getSum(arr, 2,5);
        System.out.println(sum);

        tree.update(arr, 2, 2);
        System.out.println(tree.getSum(arr, 2, 5));
    }
}


// {6,8,-1,2,17,1,3,2,4} - max/ min
package SegmentTrees;

import java.util.*;
public class Problems {

    static int maxi = (int)1e9;
    static int mini = -maxi;
    
    static class Maxtree {
        int tree[];
        public Maxtree(int arr[]) {
            tree = new int[arr.length * 4];
            construct(arr, 0, 0, arr.length-1);
        }

        public int construct(int arr[], int idx, int si, int sj) {
            if(si == sj) {
                tree[idx] = arr[si];
                return tree[idx];
            }

            int mid = (si + sj) >> 1;
            construct(arr, 2*idx+1, si, mid);
            construct(arr, 2*idx+2, mid+1, sj);

            int l = 2*idx+1, r = 2*idx+2;
            tree[idx] = Math.max(tree[l], tree[r]);

            return tree[idx];
        }

        public int qhelper(int idx, int si, int sj, int qi, int qj) {
            if(qj < si || sj < qi) return mini;
            else if(si >= qi && sj <= qj) {
                return tree[idx];
            }
            else {
                int mid = (si + sj) >> 1;
                int left = qhelper(2*idx+1, si, mid, qi, qj);
                int right = qhelper(2*idx+2, mid+1, sj, qi, qj);

                return Math.max(left, right);
            }
        }

        // query
        public int query(int arr[], int qi, int qj) {
            return qhelper(0, 0, arr.length-1, qi, qj);
        }

        public void uhelper(int i, int idx, int si, int sj, int val) {
            if(idx < si || idx > sj) return;

            if(si == sj && idx == si) {
                tree[i] = val;
                return;
            }

            int mid = (si + sj) >> 1;

            if(si <= idx && idx <= mid) {
                uhelper(2*i+1, idx, si, mid, val);
            } else {
                uhelper(2*i+2, idx, mid+1, sj, val);
            }
            
            int left = 2*i+1, right = 2*i+2;
            tree[i] = Math.max(tree[left], tree[right]);
        }

        // update (idx -> val)
        public void update(int arr[], int idx, int val) {
            arr[idx] = val;
            uhelper(0, idx, 0, arr.length-1, val);
        }

        public void printTree() {
            for(int val : tree) {
                System.out.print(val + " ");
            }
            System.out.println();
        }


        public static void main(String[] args) {
            int arr[] = {2,3,6,8,12,15};
            Maxtree st = new Maxtree(arr);
            
            st.printTree();
            st.update(arr, 2, 22);
            st.printTree();
        }
    }
}

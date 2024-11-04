package SegmentTrees;


// {6,8,-1,2,17,1,3,2,4} - max/ min
public class Min_max {

    static class MaxTree {
        int tree[];
        public MaxTree(int arr[], int n) {
            tree = new int[4*n];
            buildTree(arr, 0, 0, n-1);
        }

        public int buildTree(int arr[], int idx, int si, int ei) {
            if(si == ei) {
                tree[idx] = arr[si];
                return tree[idx];
            }

            int mid = (si + ei) / 2;
            buildTree(arr, 2*idx+1, si, mid);
            buildTree(arr, 2*idx+2, mid+1, ei);

            tree[idx] = Math.max(tree[2*idx + 1], tree[2*idx + 2]);
            return tree[idx];
        }

        public int helper(int idx, int si, int sj, int qi, int qj) {
            if(qj <= si || qi >= sj) { // non overlap
                return 0;
            }
            else if(si >= qi && sj <= qj) { // overlap
                return tree[idx];
            }
            else { // partial overlap
                int mid = (si + sj) / 2;
                int left = helper(2*idx+1, si, mid, qi, qj);
                int right = helper(2*idx+2, mid+1, sj, qi, qj);

                return Math.max(left, right);
            }
        }

        public int maxElem(int arr[], int qi, int qj) {
            int n = arr.length;
            return helper(0, 0, n-1, qi, qj);
        }

        public void printTree() {
            for(int item : tree) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    static class MinTree {
        int tree[];
        public MinTree(int arr[], int n) {
            tree = new int[4*n];
            buildTree(arr, 0, 0, n-1);
        }

        public int buildTree(int arr[], int idx, int si, int ei) {
            if(si == ei) {
                tree[idx] = arr[si];
                return tree[idx];
            }

            int mid = (si+ei) / 2;
            buildTree(arr, 2*idx+1, si, mid);
            buildTree(arr, 2*idx+2, mid+1, ei);

            tree[idx] = Math.min(tree[2*idx+1], tree[2*idx+2]);
            return tree[idx];
        }

        public int helper(int idx, int si, int sj, int qi, int qj) {
            if(qj <= si || qi >= sj) { // non overlap
                return 0;
            }
            else if(si >= qi && sj <= qj) { // overlap
                return tree[idx];
            }
            else { // partial overlap
                int mid = (si + sj) / 2;
                int left = helper(2*idx+1, si, mid, qi, qj);
                int right = helper(2*idx+2, mid+1, sj, qi, qj);

                return Math.min(left, right);
            }
        }

        public int getMinElem(int arr[], int qi, int qj) {
            int n = arr.length;
            return helper(0, 0, n-1, qi, qj);
        }


        public void printTree() {
            for(int item : tree) {
                System.out.print(item + " ");
            }
            System.out.println();
        }


    }

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};
        MaxTree maxi = new MaxTree(arr, arr.length);
        maxi.printTree();
    }
    
}

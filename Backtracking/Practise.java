package Backtracking;

import java.util.*;

public class Practise {

    private static void perm(String s, String res) {
        if(s.length() == 0) {
            System.out.print(res+" ");
            return;
        }

        for(int i=0; i<s.length(); i++) {
            char ch = s.charAt(i);
            String newstr = s.substring(0, i) + s.substring(i+1);
            perm(newstr, res+ch);
        } 
    }
  
    // find all permutations of the string
    public static void perm(String s) {
        perm(s, "");
    }


    private static void subsets(String s, String ans, int idx) {
        if(idx == s.length()) {
            if(ans.isEmpty()) System.out.print("null");
            else System.out.print(ans+" ");
            return;
        }

        subsets(s, ans+s.charAt(idx), idx+1);
        subsets(s, ans, idx+1);
    }

    // finding subsets
    public static void subsets(String str) {
        subsets(str,"", 0);
    }

    // jumps - return true, if you can able to reach the last pos of
    // arr otherwise false
    public static void jumps(int arr[]) {
        int i=0, maxReach = 0;

        for(; i<arr.length; i++) {
            if(maxReach < i) {
                System.out.println("not reachable");
                return;
            }
            maxReach = Math.max(maxReach, i+arr[i]);
        }
        System.out.println("reachable");
    }

    private static void printGrid(char grid[][]) {
        System.out.println("-----------");
        for(char row[] : grid) {
            for(char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    private static boolean isSafe_nQueens(char grid[][], int row, int col, int n) {
        for(int i=row-1; i>=0; i--) {
            if(grid[i][col] == 'Q') return false;
        }

        for(int i=row-1, j=col-1; i>=0 && j>=0; i--, j--) {
            if(grid[i][j] == 'Q') return false;
        }

        for(int i=row-1, j=col+1; i>=0 && j<n; i--, j++) {
            if(grid[i][j] == 'Q') return false;
        }

        return true;
    }

    private static boolean nQueens_1soln(char grid[][], int row, int n) {
        if(row == n) {
            return true;
        }

        for(int col=0; col<n; col++) {
            if(isSafe_nQueens(grid, row, col, n)) {
                grid[row][col] = 'Q';
                if(nQueens_1soln(grid, row+1, n)) {
                    return true;
                }
                grid[row][col] = 'x';
            }
        }
        return false;
    }

    private static void addGrid(char grid[][], List<List<String>> res) {
        List<String> currow = new ArrayList<>();
        
        for(char row[] : grid) {
            StringBuilder str = new StringBuilder();
            for(char ch : row) {
                str.append(ch);
            }
            currow.add(str.toString());
        }

        res.add(new ArrayList<>(currow));
    } 

    private static void nQueens_allSoln(char grid[][], int row, int n, List<List<String>> res) {
        if(row == n) {
            addGrid(grid, res);
            return;
        }

        for(int col=0; col<n; col++) {
            if(isSafe_nQueens(grid, row, col, n)) {
                grid[row][col] = 'Q';
                nQueens_allSoln(grid, row+1, n, res);
                grid[row][col] = 'x';
            }
        }
    }

    // N-queens
    public static void nQueens(char grid[][]) {
        int n = grid.length;

        // nQueens_1soln(grid, 0, n);
        // printGrid(grid);
        List<List<String>> res = new ArrayList<>();
        nQueens_allSoln(grid, 0, n, res);
        System.out.println(res);
    }

    static String comb[] = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs",
                        "tuv", "wxyz"};

    private static void keypadComb(String str, String res) {
        if(str.isEmpty()) {
            System.out.print(res+" ");
            return;
        }

        String key = comb[str.charAt(0) - '0'];
        
        for(int i=0; i<key.length(); i++) {
            keypadComb(str.substring(1), res+key.charAt(i));
        }
    }

    // keypad combinations
    public static void keypadComb(String str) {
        keypadComb(str, "");
    }

    public static void printMatrix(int ar[][]) {
        for(int row[] : ar) {
            for(int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    private static boolean isSafe_knights(int row, int col, int n) {
        return (row>=0 && row<n && col>=0 && col<n);
    }

    final static int[] move_x = {2, 1, -1, -2, -2, -1, 1, 2};
    final static int[] move_y = {1, 2, 2, 1, -1, -2, -2, -1};

    private static boolean knightsTour(int arr[][], int row, int col, int count, int n) {
        for(int i=0; i<8; i++) {
            if(count >= n*n) return true;

            int nr = row + move_x[i];
            int nc = col + move_y[i];

            if(isSafe_knights(nr, nc, n) && arr[nr][nc] == 0) {
                arr[nr][nc] = count;
                if(knightsTour(arr, nr, nc, count+1, n)) {
                    return true;
                }
                arr[nr][nc] = 0;
            }
        }
        return false;
    }

    // knights tour
    public static void knightsTour(int n) {
        int arr[][] = new int[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                arr[i][j] = 0;
            }
        }

        if(knightsTour(arr,0,0,1,n)) {
            printMatrix(arr);
        }
        else {
            System.out.println("not possible");
        }
    }


    private static boolean isSafe_sudoku(int grid[][], int row, int col, int num) {
        // entire col
        for(int i=0; i<9; i++) {
            if(grid[i][col] == num) return false;
        }
        
        // entire row
        for(int j=0; j<9; j++) {
            if(grid[row][j] == num) return false;
        }

        // submatrix
        int sr = (row/3) * 3;
        int sc = (col/3) * 3;
        for(int i=sr; i<sr+3; i++) {
            for(int j=sc; j<sc+3; j++) {
                if(grid[i][j] == num) return false;
            }
        }

        return true;
    }

    private static boolean sudokuSolver(int grid[][], int row, int col) {
        if(row == 9) return true;

        int nr = row, nc = col+1;
        if(nc == 9) {
            nc = 0;
            nr = row+1;
        }

        if(grid[row][col] != 0) {
            return sudokuSolver(grid, nr, nc);
        }

        for(int digit=1; digit<=9; digit++) {
            if(isSafe_sudoku(grid, row, col, digit)) {
                grid[row][col] = digit;
                if(sudokuSolver(grid, nr, nc)) return true;
                grid[row][col] = 0;
            }
        }
        return false;
    }

    // sudoku solver - 9*9 grid, and return the solution
    public static void sudokuSolver(int grid[][]) {
        if(sudokuSolver(grid, 0, 0)) {
            printMatrix(grid);
        }
        else {
            System.out.println("soln doesn't exists");
        }
    }

    
    // rat in maze
    final static int dx[] = {1, 0, -1, 0};
    final static int dy[] = {0, 1, 0, -1};
    final static char move[] = {'D', 'R', 'U', 'L'};

    private static boolean isSafe_Rat(int grid[][], int row, int col, int n) {
        return (row>=0 && row<n && col>=0 && col<n && grid[row][col] == 1);
    }

    private static boolean helper_ratInMaze(int grid[][], int row, int col, int n, boolean vis[][], String path, List<String> ans) {
        if(row == n && col == n) return false;

        vis[row][col] = true;
        if(row == n-1 && col == n-1) {
            ans.add(path);
            vis[row][col] = false;
            return true;
        }

        for(int i=0; i<4; i++) {
            int nr = row + dx[i];
            int nc = col + dy[i];

            if(isSafe_Rat(grid, nr, nc, n) && !vis[nr][nc]) {
                helper_ratInMaze(grid, nr, nc, n, vis, path+move[i], ans);
            }
        } 

        vis[row][col] = false;
        return false;
    }

    // rat in a maze - print all paths
    public static void ratInMaze(int grid[][]) {
        int n = grid.length;
        List<String> paths = new ArrayList<>();

        boolean vis[][] = new boolean[n][n];
        helper_ratInMaze(grid, 0, 0, n, vis, "", paths);
        System.out.println(paths);
    }


    public static boolean helper_ratInMaze2(int grid[][], int row, int col, int n, int sol[][]) {
        if(row == n && col == n) return false;

        if(row == n-1 && col == n-1) {
            sol[row][col] = 1;
            return true;
        }

        if(isSafe_Rat(grid, row, col, n)) {
            sol[row][col] = 1;
            // bottom
            if(helper_ratInMaze2(grid, row+1, col, n, sol)) {
                return true;
            }
            // right
            if(helper_ratInMaze2(grid, row, col+1, n, sol)) {
                return true;
            }

            sol[row][col] = 0;
            return false;
        }
        return false;
    }

    // rat in a maze print Single path
    public static void ratInMaze2(int grid[][]) {
        int n = grid.length;

        int sol[][] = new int[n][n];
        helper_ratInMaze2(grid, 0, 0, n, sol);

        printMatrix(sol);
    }



    public static void main(String[] args) {
        // perm("abc");
        // subsets("abc");
        // int arr[] = {3,2,1,1,4};
        // jumps(arr);


        // int n=4;
        // char grid[][] = new char[n][n];
        // for(char row[] : grid) {
        //     Arrays.fill(row, 'x');
        // }
        // nQueens(grid);

        // keypadComb("23");
        // knightsTour(5);

        // int[][] grid = {
        //     {0,0,8,0,0,0,0,0,0},
        //     {4,9,0,1,5,7,0,0,2},
        //     {0,0,3,0,0,4,1,9,0},
        //     {1,8,5,0,6,0,0,2,0},
        //     {0,0,0,0,2,0,0,6,0},
        //     {9,6,0,4,0,5,3,0,0},
        //     {0,3,0,0,7,2,0,0,4},
        //     {0,4,9,0,3,0,0,5,7},
        //     {8,2,7,0,0,9,0,1,3} };
        // sudokuSolver(grid);


        int grid[][] = {{1,0,0,0},
                        {1,1,1,1},
                        {0,1,0,1},
                        {0,1,0,1}};
        ratInMaze2(grid);
    }
}

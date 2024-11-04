package Tries;

import java.util.*;

import Arrays.Arrays_Easy;
public class Problems {

    static class Node {
        Node child[];
        boolean eow;
        
        public Node() {
            child = new Node[26];
            for(int i=0; i<26; i++) child[i] = null;
            
            this.eow = false;
        }
    }
    
    static class Trie {
        Node root;
        public Trie() {
            root = new Node();
        }
        
        public void insert(String word) {
            Node cur = root;
            for(int i=0; i<word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if(cur.child[idx] == null) {
                    cur.child[idx] = new Node();
                }
                cur = cur.child[idx];
            }
            cur.eow = true;
        }
        
        public void dfs(Node cur, String word, List<String> ans) {
            if(cur == null) return;
            
            if(cur.eow) {
                ans.add(word);
                return;
            }
            
            for(int i=0; i<26; i++) {
                if(cur.child[i] != null) {
                    dfs(cur.child[i], word + (char)(i+'a'), ans);
                }
            }
        }
        
        public void printTrie() {
            Node cur = root;
            List<String> words = new ArrayList<>();
            dfs(cur, "", words);
            System.out.println(words);
        }
        
        public boolean contains(String word) {
            Node cur = root;
            for(int i=0; i<word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if(cur.child[idx] == null) return false;
                cur = cur.child[idx];
            }
            return cur.eow;
        }
        
        public void countUniqueHelper(Node cur, int cnt[]) {
            if(cur == null) return;
            if(cur.eow) {
                cnt[0]++;
                return;
            }
            
            for(int i=0; i<26; i++) {
                if(cur.child[i] != null) {
                    countUniqueHelper(cur.child[i], cnt);
                }
            }
        }
        
        public int countUniqueWords() {
            Node cur = root;
            int cnt[] = new int[1];
            countUniqueHelper(cur, cnt);
            return cnt[0];
        }
    }
    
    
    public static boolean wordBreak_helper(Trie ds, String word) {
        if(word.length() == 0) return true;
        
        for(int id = 1; id <= word.length(); id++) {
            if(ds.contains(word.substring(0, id)) && wordBreak_helper(ds, word.substring(id)) ) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean wordBreak(String dict[], String word) {
        Trie ds = new Trie();
        for(String w : dict) {
            ds.insert(w);
        }
        
        return wordBreak_helper(ds, word);
    }
    
    // print unique rows of matrix
    public static void printUniqueRows(int mat[][]) {
        Trie ds = new Trie();
        String rows[] = new String[mat.length];
        
        int i=0;
        for(int row[] : mat){
            StringBuilder rowString = new StringBuilder();
            for(int item : row) {
                char ch = item == 0 ? 'a' : 'b';
                rowString.append(ch);
            }
            rows[i++] = rowString.toString();
        }
        
        for(String row : rows) {
            ds.insert(row);
        }
        
        System.out.println(ds.countUniqueWords());
        ds.printTrie();
    }

    // find anagrams
    public static void findAnagrams(String words[]) {
        // List<List<String>> ans = new ArrayList<>();
        // Trie ds = new Trie();

        Map<String, List<String> > mpp = new HashMap<>();

        for(String word : words) {
            char wordArr[] = word.toCharArray();
            Arrays.sort(wordArr);
            
            String key = new String(wordArr);
            if(mpp.containsKey(key)) {
                mpp.get(key).add(word);
            }
            else mpp.put(key, new ArrayList<>(List.of(word)));
        }

        System.out.println(mpp);
    }

    public static void main(String[] args) {
        String words[] = {"act","god","cat","dog","tac"};
        findAnagrams(words);
    }
}

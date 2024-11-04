package Tries;

import java.util.*;
public class Implement {
    
    static class Node{
        Node child[] = new Node[26];
        boolean eow = false;
        int freq = 1;
        public Node() {
            for(int i=0; i<26; i++) {
                child[i] = null;
            }
        }
    }

    static class Trie {
        Node root;
        public Trie() {
            root = new Node();
        }

        public void insert(String s) {
            Node cur = root;
            for(int level=0; level<s.length(); level++) {
                int idx = s.charAt(level) - 'a';
                if(cur.child[idx] == null) {
                    cur.child[idx] = new Node();
                } else {
                    cur.child[idx].freq++;
                }
                cur = cur.child[idx];
            }
            cur.eow = true;
        }

        public boolean search(String key) {
            Node cur = root;

            for(int level=0; level<key.length(); level++) {
                int idx = key.charAt(level) - 'a';
                if(cur.child[idx] == null) return false;
                cur = cur.child[idx];
            }
            return cur.eow;
        }

        private void dfs(Node root, StringBuilder cur, List<String> res) {
            if(root.eow) {
                res.add(cur.toString());
            }

            for(char c = 'a'; c <= 'z' ; c++) {
                int idx = c - 'a';
                if(root.child[idx] != null) {
                    cur.append(c);
                    dfs(root.child[idx], cur, res);
                    cur.deleteCharAt(cur.length() - 1);
                }
            }
        }

        public void printTrie() {
            List<String> res = new ArrayList<>();
            dfs(root, new StringBuilder(), res);

            for(String s : res) {
                System.out.print(s + " ");
            }
        }

        public boolean startsWith(String prefix) {
            Node cur = root;
            for(int i=0; i<prefix.length(); i++) {
                int idx = prefix.charAt(i) - 'a';
                if(cur.child[idx] == null) return false;
                cur = cur.child[idx];
            }
            return true;
        }

        public int countNodes(Node root) {
            if(root == null) return 0;

            int count = 0;
            for(int i=0; i<26; i++) {
                if(root.child[i] != null) {
                    count += countNodes(root.child[i]);
                }
            }
            return count+1;
        }

        public int countWordsEqualTo(String word) {
            if(root == null) return 0;

            Node cur = root;
            for(int i=0; i<word.length(); i++) {
                int idx = word.charAt(i) - 'a';

                if(cur.child[idx] == null) return 0;
                cur = cur.child[idx];
            }
            if(cur.eow) return cur.freq;
            return 0;
        }

        public int countWordsStartsWith(String word) {
            Node cur = root;
            for(int i=0; i<word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if(cur.child[idx] == null) return 0;
                cur = cur.child[idx];
            }
            return cur.freq;
        }

        // word is present in the trie.
        public void erase(String word) {
            Stack<Node> st = new Stack<>();
            Node cur = root;

            for(int i=0; i<word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                st.push(cur);
                cur = cur.child[idx];
            }
            cur.eow = false;

            for(int i=word.length()-1; i>=0; i--) {
                Node par = st.pop();
                int idx = word.charAt(i) - 'a';
                par.child[idx].freq--;

                if(par.child[idx].freq == 0) {
                    par.child[idx] = null;
                }
            }
        }
    }

    public static void main(String[] args) {
        String words[] = {"apple", "apply", "app", "aplet"};
        Trie ds = new Trie();

        for(String word : words) {
            ds.insert(word);
        }
        // System.out.println(trie.search("theie"));
        // trie.printTrie();

        // System.out.println(ds.startsWith("theres"));
        ds.erase("apple");
        Node cur = ds.root;
        String word = "apply";
        for(int i=0; i<word.length(); i++) {
            char ch = word.charAt(i);
            System.out.println("freq of " + ch + " - " + cur.child[ch-'a'].freq);
        }
    }
}

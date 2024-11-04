package Tries;

// smallest unique prefix of all words
public class Prefix_problem {

    static class Node {
        Node child[];
        boolean eow;
        int freq;
        public Node() {
            child = new Node[26];
            for(int i=0; i<26; i++) child[i] = null;
            eow = false;
            freq = 1;
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
                } else {
                    cur.child[idx].freq++;
                }
                cur = cur.child[idx];
            }
            cur.eow = true;
        }
    }

    public static void prefixHelper(Node root, String ans) {
        if(root.freq == 1) {
            System.out.println(ans);
            return;
        }

        for(int i=0; i<26; i++) {
            if(root.child[i] != null) {
                prefixHelper(root.child[i], ans+(char)(i + 'a'));
            }
        }
    }

    public static void prefixProblem(String words[]) {
        Trie ds = new Trie();
        for(String word : words) {
            ds.insert(word);
        }
        ds.root.freq = -1;
        prefixHelper(ds.root, "");
    }
    
    public static void main(String[] args) {
        String words[] = {"duck", "zebra", "dove", "doll"};
        prefixProblem(words);
    }
}

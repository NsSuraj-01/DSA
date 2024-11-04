package Tries;

public class LongestWord extends Implement{
    
    static String ans = "";

    public static void helper(Node root, StringBuilder cur) {
        if(root == null) return;

        for(int i=0; i<26; i++) {
            if(root.child[i] != null && root.child[i].eow) {
                cur.append((char)(i + 'a'));
                if(cur.length() > ans.length()) 
                    ans = cur.toString();
                helper(root.child[i], cur);
                cur.deleteCharAt(cur.length() - 1);
            }
        }
    }

    public static void longestword(String words[]) {
        Trie ds = new Trie();
        for(String word : words) {
            ds.insert(word);
        }

        helper(ds.root, new StringBuilder());
    }

    public static void main(String[] args) {
        String words[] = {"a", "ap", "app", "apple", "appl", "apply"};
        longestword(words);
        System.out.println(ans);
    }
}

package Tries;

public class WordBreak extends Implement {


    public static boolean helper(Trie ds, String key) {
        if(key.length() == 0) return true;

        for(int i=1; i<=key.length(); i++) {
            if(ds.search(key.substring(0, i)) && helper(ds, key.substring(i))) {
                return true;
            }
        }
        return false;
    }

    public static void wordBreak(String words[], String key) {
        Trie ds = new Trie();
        for(String word : words) {
            ds.insert(word);
        }

        System.out.println(helper(ds, key));
    }

    public static void main(String[] args) {
        String words[] = {"i", "like", "samsung", "apple", "love", "airpods"};
        Trie ds = new Trie();
        String key = "ilovepods";

        wordBreak(words, key);
    }
    
}

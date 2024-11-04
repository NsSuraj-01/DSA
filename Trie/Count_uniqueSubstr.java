package Tries;

public class Count_uniqueSubstr extends Implement {
    

    public static void uniqueSubStr(String word) {
        Trie ds = new Trie();
        for(int i=0; i<word.length(); i++) {
            String suff = word.substring(i);
            ds.insert(suff);
        }

        System.out.println("unique substrings: " + ds.countNodes(ds.root));
    }
    
    public static void main(String[] args) {
        String word = "apple";
        uniqueSubStr(word);
    }
}

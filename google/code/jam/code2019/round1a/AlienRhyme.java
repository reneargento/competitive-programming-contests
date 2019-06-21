package google.code.jam.code2019.round1a;

import java.util.*;

/**
 * Created by Rene Argento on 12/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/0000000000051635/0000000000104e05
public class AlienRhyme {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int wordsCount = scanner.nextInt();
            String[] words = new String[wordsCount];

            for (int i = 0; i < wordsCount; i++) {
                StringBuilder word = new StringBuilder(scanner.next());
                words[i] = word.reverse().toString();
            }

            int pairCount = getPairCount(words);
            System.out.println("Case #" + t + ": " + pairCount);
        }
    }

    public static int getPairCount(String[] words) {
        Trie trie = new Trie();

        for (String word : words) {
            trie.add(word);
        }

        int unpairedWords = computeUnpairedWords(trie.root, trie.root);
        return words.length - unpairedWords;
    }

    private static int computeUnpairedWords(Trie.Node node, Trie.Node root) {
        if (node == null) {
            return 0;
        }

        if (node.next.isEmpty()) {
            return 1;
        }

        int unpairedCount = 0;

        for (Character character : node.next.keySet()) {
            Trie.Node childNode = node.next.get(character);
            unpairedCount += computeUnpairedWords(childNode, root);
        }

        if (node.isKey) {
            unpairedCount++;
        }

        if (unpairedCount >= 2 && node != root) {
            unpairedCount -= 2;
        }

        return unpairedCount;
    }

    public static class Trie {

        private static class Node {
            private Map<Character, Node> next = new HashMap<>();
            private boolean isKey;
            private int size;
        }

        Node root = new Node();

        public void add(String key) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }

            root = add(root, key, 0);
        }

        private Node add(Node node, String key, int digit) {
            if (node == null) {
                node = new Node();
            }

            node.size++;

            if (digit == key.length()) {
                node.isKey = true;
                return node;
            }

            char nextChar = key.charAt(digit);

            Node nextNode = add(node.next.get(nextChar), key, digit + 1);
            node.next.put(nextChar, nextNode);
            return node;
        }
    }
}
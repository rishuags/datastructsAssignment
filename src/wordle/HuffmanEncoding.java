
package wordle;

import project20280.hashtable.ChainHashMap;
import project20280.interfaces.Position;
import project20280.priorityqueue.HeapPriorityQueue;
import project20280.tree.LinkedBinaryTree;

import java.util.*;

public class HuffmanEncoding {

    public static ChainHashMap<String, String> charEncode = new ChainHashMap<>();

    public LinkedBinaryTree<String> getHuffmanTree(List<String> dictionary) {
        Map<Character, Integer> letterFrequency = getLetterFrequency(dictionary);
        HeapPriorityQueue<Integer, LinkedBinaryTree<String>> priorityQueue = new HeapPriorityQueue<>();

        letterFrequency.forEach((character, frequency) -> {
            LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
            tree.addRoot(character.toString());
            priorityQueue.insert(frequency, tree);
        });

        while (priorityQueue.size() > 1) {
            var entry1 = priorityQueue.removeMin();
            var entry2 = priorityQueue.removeMin();

            LinkedBinaryTree<String> mergedTree = new LinkedBinaryTree<>();
            mergedTree.addRoot(entry1.getValue().root().getElement() + entry2.getValue().root().getElement());

            mergedTree.attach(mergedTree.root(), entry1.getValue(), entry2.getValue());
            priorityQueue.insert(entry1.getKey() + entry2.getKey(), mergedTree);
        }

        return priorityQueue.isEmpty() ? null : priorityQueue.removeMin().getValue();
    }

    public Map<Character, Integer> getLetterFrequency(List<String> list) {
        Map<Character, Integer> letterFreq = new HashMap<>();
        list.forEach(word -> word.chars().mapToObj(c -> (char) c).forEach(
                c -> letterFreq.merge(c, 1, Integer::sum)
        ));
        return letterFreq;
    }

    public Map<String, Integer> getWordFrequency(List<String> list) {
        Map<Character, Integer> letterFrequency = getLetterFrequency(list);
        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        list.forEach(word -> {
            int sumfreq = word.chars().map(c -> letterFrequency.get((char) c)).sum();
            wordFrequencyMap.put(word, sumfreq);
        });

        return wordFrequencyMap;
    }

    public static void getCharEncoding(LinkedBinaryTree<String> huffmanTree, String code, Position<String> p) {
        if (huffmanTree.left(p) != null) {
            if (huffmanTree.left(p).getElement().length() > 1) {
                getCharEncoding(huffmanTree, code + "0", huffmanTree.left(p));
            } else {
                charEncode.put(huffmanTree.left(p).getElement(), code + "0");
            }
        }

        if (huffmanTree.right(p) != null) {
            if (huffmanTree.right(p).getElement().length() > 1) {
                getCharEncoding(huffmanTree, code + "1", huffmanTree.right(p));
            } else {
                charEncode.put(huffmanTree.right(p).getElement(), code + "1");
            }
        }
    }
}

package wordle;

import project20280.hashtable.ChainHashMap;
import project20280.interfaces.Entry;
import project20280.interfaces.Position;
import project20280.priorityqueue.HeapPriorityQueue;
import project20280.tree.LinkedBinaryTree;

import java.util.*;
public class HuffmanEncoding {

    public static ChainHashMap<String,String> charEncode = new ChainHashMap<>();
    public LinkedBinaryTree<String> getHuffmanTree(List<String> dictionary){
        Map<Character, Integer> letterFrequency = getFrequency(dictionary);
        Map<String , Integer> wordFrequencyMap = getWordFrequency(dictionary);

        for(Map.Entry<Character,Integer> entry : letterFrequency.entrySet()){
            System.out.println("Letter: " + entry.getKey() + "\nFrequency: " + entry.getValue());
        }
        for(Map.Entry<String,Integer> entry : wordFrequencyMap.entrySet()){
            System.out.println("Word: " + entry.getKey() + "\nFrequency: " + entry.getValue());
        }
        HeapPriorityQueue<Integer, LinkedBinaryTree<String>> pq = new HeapPriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : letterFrequency.entrySet()) {
            LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
            tree.addRoot(entry.getKey().toString());

            pq.insert(entry.getValue(), tree);
        }

        while (pq.size() > 1)
        {
            Entry<Integer, LinkedBinaryTree<String>> e1 = pq.removeMin();
            Entry<Integer, LinkedBinaryTree<String>> e2 = pq.removeMin();
            LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
            tree.addRoot(e1.getValue().root().toString() + e2.getValue().root().toString());
            LinkedBinaryTree<String> t1 = e1.getValue();
            LinkedBinaryTree<String> t2 = e2.getValue();

            tree.attach(tree.root(),t1,t2);
            pq.insert(e1.getKey() + e2.getKey(), tree);
        }
        return pq.removeMin().getValue();
    }
    public Map<Character, Integer> getFrequency(List<String> list) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (String word : list) {
            for (char c : word.toCharArray()) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }
        return frequencyMap;
    }
    public Map<String,Integer> getWordFrequency(List<String> list) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        Map<Character, Integer> frequencyMap = getFrequency(list);
        for (String word : list){
            int frequencySum = 0;
            for (int i = 0; i < 5; i++) {
                frequencySum += frequencyMap.get(word.charAt(i));
            }
            wordFrequencyMap.put(word,frequencySum);
        }
        return wordFrequencyMap;
    }
    public static void getCharEncoding(LinkedBinaryTree<String> huffmanTree, String code, Position<String> p){

        if((huffmanTree.left(p).getElement()).length() != 1){
            getCharEncoding(huffmanTree,code + "0", huffmanTree.left(p));
        }
        else{
            charEncode.put( (huffmanTree.left(p).getElement()),code+"0");
        }
        if(huffmanTree.right(p).getElement().length() != 1){
            getCharEncoding(huffmanTree,code+"1", huffmanTree.right(p));
        }
        else{
            charEncode.put(huffmanTree.right(p).getElement(),code+"1");
        }
    }

}


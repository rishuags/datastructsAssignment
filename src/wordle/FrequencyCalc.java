package wordle;

import java.util.*;

public class FrequencyCalc {

    public static void main(String[] args) {
        Wordle wordle = new Wordle();

        List<String> dictionary = wordle.readDictionary("wordle/resources/extended-dictionary.txt");

        //System.out.println(dictionary);

        HashMap<Character, Integer> letterFrequency = new HashMap<>();

        for (String word : dictionary) {
            // Iterate through each letter in the word
            for (char letter : word.toCharArray()) {
                // Increment the count for the letter in the HashMap
                letterFrequency.put(letter, letterFrequency.getOrDefault(letter, 0) + 1);
            }
        }

        // Print the frequency of each letter
        for (char letter : letterFrequency.keySet()) {
            //System.out.println(letter + ": " + letterFrequency.get(letter));
        }

        List<Map.Entry<Character, Integer>> frequencyList = new ArrayList<>(letterFrequency.entrySet());
        frequencyList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<Character, Integer> entry : frequencyList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}

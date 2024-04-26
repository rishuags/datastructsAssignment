package wordle;

import project20280.tree.BinaryTreePrinter;
import project20280.tree.LinkedBinaryTree;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wordle {

    String fileName = "wordle/resources/extended-dictionary.txt";
    //String fileName = "wordle/resources/extended-dictionary.txt";
    List<String> dictionary = null;
    final int num_guesses = 5;
    final long seed = 42;
    //Random rand = new Random(seed);
    Random rand = new Random();

    static final String winMessage = "CONGRATULATIONS! YOU WON! :)";
    static final String lostMessage = "YOU LOST :( THE WORD CHOSEN BY THE GAME IS: ";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_GREY_BACKGROUND = "\u001B[100m";

    Wordle() {

        this.dictionary = readDictionary(fileName);

//        System.out.println("dict length: " + this.dictionary.size());
//        System.out.println("dict: " + dictionary);
        HuffmanEncoding huffman = new HuffmanEncoding();

        LinkedBinaryTree<String> tree = huffman.getHuffmanTree(dictionary);

        BinaryTreePrinter<String> printer = new BinaryTreePrinter<>(tree);
        String treeString = printer.print();
        System.out.println(treeString);

        HuffmanEncoding.getCharEncoding(tree,"",tree.root());
        double asciiLength = 0;
        double huffmanLength = 0;
        for(String word : dictionary){
            for(char c : word.toCharArray()){
                asciiLength += 8;
                String string = Character.toString(c);
                huffmanLength += HuffmanEncoding.charEncode.get(string).length();
            }
        }
        System.out.println("The ratio is: " + huffmanLength/asciiLength);

    }

    public static void main(String[] args) {
        Wordle game = new Wordle();

        String target = game.getRandomTargetWord();

        //System.out.println("target: " + target);
//        String testTarget = "regal";
        game.play(target);
        target.toCharArray()[0] = '!';



    }

    public void play(String target) {
        // TODO
        // TODO: You have to fill in the code
        HuffmanEncoding huffman = new HuffmanEncoding();
        Map<String, Integer> wordFrequencyMap = huffman.getWordFrequency(dictionary);
        for(int i = 0; i < num_guesses; ++i) {
            String guess = getGuess();

            if(guess == target) { // you won!
                win(target);

                return;
            }

            /***/
            char [] guessChar = guess.toCharArray();
            char[] targetChar = target.toCharArray();
            HashMap<Integer, Character> indexElementMap = new HashMap<>();;
            // the hint is a string where green="+", yellow="o", grey="_"
            // didn't win ;(
            String [] hint = {"_", "_", "_", "_", "_"};
            boolean removed = false;
            for (int k = 0; k < 5; k++) {
                // TODO:
                indexElementMap.put(k,targetChar[k]);
                if(guessChar[k]==targetChar[k]){
                    hint[k] = "+";

                    for(Integer j=0; j < 5; j++){
                        if(indexElementMap.containsKey(j) && indexElementMap.get(j) == guessChar[k]  && !removed ){
                            indexElementMap.remove(j);
                            removed = true;
                        }
                    }
                }

            }

            // set the arrays for yellow (present but not in right place), grey (not present)
            // loop over each entry:
            //  if hint == "+" (green) skip it
            //  else check if the letter is present in the target word. If yes, set to "o" (yellow)


            for (int k = 0; k < 5; k++) {
                // TODO:
                if(indexElementMap.containsValue(guessChar[k]) && guessChar[k] != targetChar[k]){
                    hint[k] = "o";
                    removed = false;

                    for(Integer j=0; j < 5; j++){
                        if(indexElementMap.containsKey(j) && indexElementMap.get(j) == guessChar[k]  && !removed ){
                            indexElementMap.remove(j);
                            removed = true;
                        }
                    }


                }

            }
            /***/
            for (int pos = 0; pos < 5; pos++) {
                Map<String, Integer> wordsToRemove = new HashMap<>();

                // Handling '+' hint
                if (hint[pos].equals("+")) {
                    for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                        if (entry.getKey().charAt(pos) != guess.charAt(pos)) {
                            wordsToRemove.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                // Handling '_' hint
                else if (hint[pos].equals("_")) {
                    int numOfDuplicates = 1;
                    char letter = '!';
                    for (int c = 0; c < 5; c++) {
                        if (guess.charAt(c) == guess.charAt(pos) && pos != c) {
                            if (hint[c].charAt(0) != '_') {
                                numOfDuplicates++;
                                letter = guess.charAt(c);
                            }
                        }
                    }
                    for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                        int numOfDictionaryDuplicates = 1;
                        for (int d = 0; d < 5; d++) {
                            if (entry.getKey().charAt(d) == letter) {
                                numOfDictionaryDuplicates++;
                            }
                        }
                        if (numOfDictionaryDuplicates > numOfDuplicates) {
                            wordsToRemove.put(entry.getKey(), entry.getValue());
                        } else if (entry.getKey().indexOf(guess.charAt(pos)) != -1 && letter == '!') {
                            wordsToRemove.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                // Handling 'o' hint
                else if (hint[pos].equals("o")) {
                    for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                        if (entry.getKey().charAt(pos) == guess.charAt(pos)) {
                            wordsToRemove.put(entry.getKey(), entry.getValue());
                        }
                        if (entry.getKey().indexOf(guess.charAt(pos)) == -1) {
                            wordsToRemove.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                // Removing the words to remove from the main map
                for (Map.Entry<String, Integer> entry : wordsToRemove.entrySet()) {
                    wordFrequencyMap.remove(entry.getKey());
                }
            }


            // after setting the yellow and green positions, the remaining hint positions must be "not present" or "_"
            System.out.println("hint: " + Arrays.toString(hint));


            // check for a win
            int num_green = 0;
            for(int k = 0; k < 5; ++k) {
                if(hint[k] == "+") num_green += 1;
            }
            if(num_green == 5) {
                 win(target);
                 return;
            }
            int count = 1;
            for(Map.Entry<String,Integer> entry : wordFrequencyMap.entrySet()){
                //System.out.println(count + ": Word: " + entry.getKey() + "\nFrequency: " + entry.getValue());
                count++;
            }

            int val = Integer.MIN_VALUE;
            String key = null;
            for(Map.Entry<String,Integer> entry : wordFrequencyMap.entrySet()){
                if(entry.getValue()>val){
                    val = entry.getValue();
                    key = entry.getKey();
                }
            }
            System.out.println("Your next guess should be: " + key);
        }



        lost(target);
    }

    public void lost(String target) {
        System.out.println();
        System.out.println(lostMessage + target.toUpperCase() + ".");
        System.out.println();

    }
    public void win(String target) {
        System.out.println(ANSI_GREEN_BACKGROUND + target.toUpperCase() + ANSI_RESET);
        System.out.println();
        System.out.println(winMessage);
        System.out.println();
    }

    public String getGuess() {
        Scanner myScanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());  // Create a Scanner object
        System.out.println("Guess:");

        String userWord = myScanner.nextLine();  // Read user input
        userWord = userWord.toLowerCase(); // covert to lowercase

        // check the length of the word and if it exists
       /* while ((userWord.length() != 5) || !(dictionary.contains(userWord))) {
            if ((userWord.length() != 5)) {
                System.out.println("The word " + userWord + " does not have 5 letters.");
            } else {
                System.out.println("The word " + userWord + " is not in the word list.");
            }
            // Ask for a new word
            System.out.println("Please enter a new 5-letter word.");
            userWord = myScanner.nextLine();
        }
        */

        return userWord;
    }

    public String getRandomTargetWord() {
        // generate random values from 0 to dictionary size
        return dictionary.get(rand.nextInt(dictionary.size()));
    }
    public List<String> readDictionary(String fileName) {
        List<String> wordList = new ArrayList<>();

        try {
            // Open and read the dictionary file
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            assert in != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //Read file line By line
            while ((strLine = reader.readLine()) != null) {
                wordList.add(strLine.toLowerCase());
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return wordList;
    }
}

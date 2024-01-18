package FoodPriceAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// TNode class represents a node in the Trie data structure
class TNode {
    TNode[] children; // Array to store child nodes
    boolean isEndOfWord; // Flag to indicate if the node marks the end of a valid word

    // Constructor to initialize TNode
    TNode() {
        children = new TNode[26]; // Assuming English alphabets
        isEndOfWord = false;
    }
}

// WordCompletion class for word completion using Trie
public class WordCompletion {
    private static TNode root = new TNode(); // Root node of the Trie

    // Main method to execute the word completion functionality
    public static void main(String[] args) {
        // Load words from different files into the Trie
        loadWordsIntoTrie(
            "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\TargetData.csv");
        loadWordsIntoTrie(
            "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\LoblawsData.csv");
        loadWordsIntoTrie(
            "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\JioMartData2.csv");

        Scanner scanner = new Scanner(System.in);
        String prefix;
        char choice;

        try {
            do {
                System.out.print("\nEnter a prefix (or '*' to exit): ");
                prefix = scanner.nextLine().toLowerCase().trim();
                choice = prefix.charAt(0);

                if (choice != '*') {
                    // Get word completions for the entered prefix
                    List<String> completions = getWordCompletions(prefix);

                    if (completions.isEmpty()) {
                        System.out.println("\nNo completions found for '" + prefix + "'");
                    } else {
                        System.out.println("\nCompletions for '" + prefix + "':");

                        // Display word completions
                        for (String completion : completions) {
                            System.out.println(completion);
                        }
                    }
                }

            } while (choice != '*');
        } catch (Exception e) {
            // Handle exceptions gracefully by displaying a message
            System.out.println("Exception: " + e.getClass());
            // Restart the main method in case of an exception
            WordCompletion.main(args);
        }
    }

    // Method to load words from a file into the Trie
    public static void loadWordsIntoTrie(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+"); // Split by spaces

                // Process each word and insert into Trie
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim();
                    if (!word.isEmpty()) {
                        insertIntoTrie(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a word into the Trie
    public static void insertIntoTrie(String word) {
        TNode current = root;

        // Traverse the Trie based on the characters of the word
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';

            // Create a new node if the current character's node is null
            if (current.children[index] == null) {
                current.children[index] = new TNode();
            }

            // Move to the next node
            current = current.children[index];
        }

        // Mark the last node as the end of a valid word
        current.isEndOfWord = true;
    }

    // Method to get word completions for a given prefix
    public static List<String> getWordCompletions(String prefix) {
        List<String> completions = new ArrayList<>();
        TNode lastNode = findLastNodeOf(prefix);

        // Utilize a recursive function to explore word completions
        if (lastNode != null) {
            getCompletionsUtil(lastNode, new StringBuilder(prefix), completions);
        }

        return completions;
    }

    // Method to find the last node of a given prefix in the Trie
    private static TNode findLastNodeOf(String prefix) {
        TNode current = root;

        // Traverse the Trie based on the characters of the prefix
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';

            // Return null if the current character's node is null
            if (current.children[index] == null) {
                return null;
            }

            // Move to the next node
            current = current.children[index];
        }

        return current;
    }

    // Recursive helper method to get word completions
    private static void getCompletionsUtil(TNode node, StringBuilder currentWord, List<String> completions) {
        if (node.isEndOfWord) {
            completions.add(currentWord.toString());
        }

        // Explore all children nodes to find completions
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char ch = (char) (i + 'a');
                currentWord.append(ch);
                getCompletionsUtil(node.children[i], currentWord, completions);
                currentWord.setLength(currentWord.length() - 1);
            }
        }
    }
}

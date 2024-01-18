package FoodPriceAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Trie_node class represents a node in the DS
class TrieNode {
    TrieNode[] chldrn; // Array to store child nodes
    boolean isEOW; // Flag is used to indicate if the end of a valid word

    // Constructor to initialize TrieNode
    TrieNode() {
        chldrn = new TrieNode[26]; // Assume only english alphabets
        isEOW = false;
    }
}

// SpellCheck class for spell checking using Trie
public class SpellCheck {
    private static TrieNode root = new TrieNode(); // Root node of the Trie

    // Main method
    public static void main(String[] args) {
        // Load words from different files into the Trie
        loadWordsIntoTrie("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\TargetData.csv");
        loadWordsIntoTrie("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\LoblawsData.csv");
        loadWordsIntoTrie("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\JioMartData2.csv");

        Scanner scanner = new Scanner(System.in);
        String wordToCheck;
        char choice;

        // Spell checking loop
        do {
            System.out.print("\nEnter a word (or '*' to exit): ");
            wordToCheck = scanner.nextLine().toLowerCase().trim();

            if(wordToCheck.charAt(0)!='*') {
            	while(!wordToCheck.matches("[a-zA-Z_]+")) {
            		System.out.println("Invalid Input, Please Enter only Aplhabets");
            		wordToCheck = scanner.nextLine().toLowerCase().trim();
            	}
            }
            
            choice = wordToCheck.charAt(0);
            
            

            if (choice != '*') {
                // Check if the entered word is in the Trie
                boolean isWordInTrie = searchInTrie(wordToCheck);

                if (isWordInTrie) {
                    System.out.println("'" + wordToCheck + "' is a valid word.");
                } else {
                    // If not in Trie, suggest corrctins
                    List<String> sggstons = suggestCorrections(wordToCheck);

                    if (sggstons.isEmpty()) {
                        System.out.println("\nNo sugestions found for '" + wordToCheck + "'");
                    } else {
                        System.out.println("\nSuggestions for '" + wordToCheck + "':");

                        // Display suggested corrections
                        for (String sggston : sggstons) {
                            System.out.println(sggston);
                        }
                    }
                }
            }

        } while (choice != '*');
    }

    // Method to load speels from a fle into the Trie
    public static void loadWordsIntoTrie(String flePaath) {
        try (BufferedReader br = new BufferedReader(new FileReader(flePaath))) {
            String liinee;

            // Read each line from the file
            while ((liinee = br.readLine()) != null) {
                String[] words = liinee.split("\\s+"); // Split by spaces

                // Process each word and insert into Trie
                for (String speelll : words) {
                    speelll = speelll.replaceAll("[^a-zA-Z]", "").toLowerCase().trim();
                    if (!speelll.isEmpty()) {
                        insertIntoTrie(speelll);
                    }
                }
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    // Function to put a spell into the TrieDS
    public static void insertIntoTrie(String wrd) {
        TrieNode current = root;

        // Traverse the Trie based on the characters of the word
        for (char ch : wrd.toCharArray()) {
            int index = ch - 'a';

            // Create a new node if the current character's node is null
            if (current.chldrn[index] == null) {
                current.chldrn[index] = new TrieNode();
            }

            // Move to the next noodee
            current = current.chldrn[index];
        }

        // Mark the last nde as the end of a corectt spell
        current.isEOW = true;
    }

    // Method to look for a spell in the TrieDS
    public static boolean searchInTrie(String wrdd) {
        TrieNode crrnt = root;

        // Traverse the Trie based on the characters of the word
        for (char ch : wrdd.toCharArray()) {
            int index = ch - 'a';

            // Return false if the current character's node is null
            if (crrnt.chldrn[index] == null) {
                return false; // Word not found in Trie
            }

            // Move to the next node
            crrnt = crrnt.chldrn[index];
        }

        // Check if the last node marks the end of a valid word
        return crrnt.isEOW;
    }

    // Function to suggest crrctions for a given wrdd
    public static List<String> suggestCorrections(String word) {
        List<String> suggestions = new ArrayList<>();

        // Utilize a recursive function to explore possible corrections
        suggestCorrectionsUtil(root, word, new StringBuilder(), suggestions, 2); // Change the maximum distance as needed

        return suggestions;
    }

    // Recursive helper method to suggest corrections
    private static void suggestCorrectionsUtil(TrieNode node, String word, StringBuilder currentWord,
            List<String> suggestions, int maxDistance) {
        if (currentWord.length() > word.length() + maxDistance) {
            return;
        }

        if (node.isEOW && currentWord.toString().length() >= word.length() - maxDistance
                && currentWord.toString().length() <= word.length() + maxDistance) {
            int dist = calculateEditDistance(currentWord.toString(), word);
            if (dist <= maxDistance) {
                suggestions.add(currentWord.toString());
            }
        }

        for (int i = 0; i < 26; i++) {
            if (node.chldrn[i] != null) {
                char ch = (char) (i + 'a');
                currentWord.append(ch);
                suggestCorrectionsUtil(node.chldrn[i], word, currentWord, suggestions, maxDistance);
                currentWord.setLength(currentWord.length() - 1);
            }
        }
    }

    // Method to calculate the edit distance between two words
    private static int calculateEditDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // Create a 2D array to store edit distances
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }

        // Give the ED of both the words
        return dp[m][n];
    }
}

package FoodPriceAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SearchFrequency {
	// creating root node
	public static TreeNode root;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		char ch01;
		do {

			System.out.print("Enter a word to find it's popularity (or '*' to quit):");
			String userInput = scanner.nextLine().trim();
			ch01 = userInput.charAt(0);
			if (ch01 != '*') {
				try {
	            	if (!userInput.matches("[a-zA-Z]+")) {
	                    throw new IllegalArgumentException("Invalid input: Please enter only alphabetic characters.");
	                }
	            	storeUserInput(userInput); // store user's input in a text file
	                root = buildBinaryTree("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\user_input.txt"); // build binary tree from file
	                int frequency = getFrequency(root, userInput); // get frequency of user's input from file
	                System.out.println(userInput + " is search " + frequency +" Times");
	            	
	            }catch(IllegalArgumentException e) { System.out.println("Exception caught: " + e.getMessage());}
			}

		} while (ch01 != '*');
	}

	// method to store user input
	public static void storeUserInput(String input) {
		// try to write content into file using filewriter
		try (FileWriter writer = new FileWriter("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\user_input.txt",
				true); // true used to append data into existing file
				BufferedWriter bufferedwriter = new BufferedWriter(writer);
				PrintWriter out = new PrintWriter(bufferedwriter)) {
			out.println(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// method to build binary tree from textfile
	public static TreeNode buildBinaryTree(String filePath) {
		TreeNode root = null;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" "); // split the line by spaces

				for (String word : words) {
					String value = word.trim();
					root = insertWord(root, value);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return root;
	}

	// method to insertword into tree

	public static TreeNode insertWord(TreeNode root, String word) {
		if (root == null) {
			return new TreeNode(word);
		}

		int compareWord = word.compareToIgnoreCase(root.data);

		if (compareWord == 0) {
			root.numberofaccurance++;
		} else if (compareWord < 0) {
			root.l = insertWord(root.l, word);
		} else {
			root.r = insertWord(root.r, word);
		}

		return root;
	}

	// get frequency of word
	public static int getFrequency(TreeNode root, String word) {
		if (root == null) {
			return 0;
		}
		// first we compare with root node if it does'not match
		int compareWord = word.compareToIgnoreCase(root.data);

		if (compareWord == 0) {
			return root.numberofaccurance;
		} else if (compareWord < 0) {
			return getFrequency(root.l, word);
		} else {
			return getFrequency(root.r, word);
		}
	}

}

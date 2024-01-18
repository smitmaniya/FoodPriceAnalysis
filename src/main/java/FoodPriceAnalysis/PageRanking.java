package FoodPriceAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PageRanking {
	public static void main(String[] args) {
		// Paths to your CSV files
		String[] fileNames = {
				"C:\\\\\\\\Users\\\\\\\\sanja\\\\\\\\OneDrive\\\\\\\\Desktop\\\\\\\\ACC\\\\\\\\Assg3\\\\\\\\TargetData.csv",
				"C:\\\\\\\\Users\\\\\\\\sanja\\\\\\\\OneDrive\\\\\\\\Desktop\\\\\\\\ACC\\\\\\\\Assg3\\\\\\\\LoblawsData.csv",
				"C:\\\\\\\\Users\\\\\\\\sanja\\\\\\\\OneDrive\\\\\\\\Desktop\\\\\\\\ACC\\\\\\\\Assg3\\\\\\\\JioMartData2.csv" };
		// created scanner obj for input
		Scanner scanner = new Scanner(System.in);
		// continue run loop until user enter * for terminating program
		while (true) {
			System.out.print("\nEnter the word to rank pages (enter * to exit): ");
			String word = scanner.nextLine().toLowerCase(); // Convert input to lowercase

			if (word.equals("*")) {
				System.out.println("Exiting...");
				break; // Exit the loop if the user inputs *
			}
			// file name TreeMap store name as String and word frequency in integer
			TreeMap<String, Integer> pages = new TreeMap<>();

			try {
				// Count word frequency in each file and store in pages TreeMap
				for (String filepath : fileNames) {
					String fileName = getFileNameWithoutExtension(filepath);
					BufferedReader reader = new BufferedReader(new FileReader(filepath));
					String line;
					int frequency = 0;
					while ((line = reader.readLine()) != null) {
						frequency += countWordFrequency(line.toLowerCase(), word); // Convert line to lowercase
					}
					pages.put(fileName, frequency);
					reader.close();
				}

				// Convert TreeMap to a list of entries and sort based on frequency
				List<Map.Entry<String, Integer>> sortedPages = new ArrayList<>(pages.entrySet());
				sortedPages.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

				// Display results with ranks
				System.out.println("Page Ranking based on word frequency:");
				int rank = 1;
				for (Map.Entry<String, Integer> entry : sortedPages) {
					System.out.println(
							"Rank " + rank + ": " + entry.getKey() + " - " + entry.getValue() + " occurrences");
					rank++;
				}
				// exception handling
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Function to count word frequency in a line
	private static int countWordFrequency(String line, String word) {
		return line.split(word, -1).length - 1;
	}

	// This function extracts the file name without the extension from the given
	// file path
	private static String getFileNameWithoutExtension(String filePath) {
		String fileName = new java.io.File(filePath).getName(); // Get file name with extension
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex == -1) {
			return fileName; // No extension found
		} else {
			return fileName.substring(0, dotIndex); // Remove extension
		}
	}

}
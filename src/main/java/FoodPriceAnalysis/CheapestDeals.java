package FoodPriceAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CheapestDeals {

	public static void main(String[] args) {

		String fp1 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\LoblawsData.csv";
		String fp2 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\JioMartData2.csv";
		String fp3 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\TargetData.csv";

		SpellCheck.loadWordsIntoTrie("C:\\\\Users\\\\sanja\\\\OneDrive\\\\Desktop\\\\ACC\\\\Assg3\\\\TargetData.csv");
		SpellCheck.loadWordsIntoTrie("C:\\\\Users\\\\sanja\\\\OneDrive\\\\Desktop\\\\ACC\\\\Assg3\\\\LoblawsData.csv");
		SpellCheck.loadWordsIntoTrie("C:\\\\Users\\\\sanja\\\\OneDrive\\\\Desktop\\\\ACC\\\\Assg3\\\\JioMartData2.csv");

		List<String[]> DataF1 = readCSV(fp1, "LoblawsData.csv");
		List<String[]> DataF2 = readCSV(fp2, "JioMartData2.csv");
		List<String[]> DataF3 = readCSV(fp3, "TargetData.csv");
		char choice01;
		
			
			do {
				System.out.println("Enter a grocery item name to find top 5 cheapest deals or (Enter * to Exit) ");
				Scanner scn = new Scanner(System.in);

				String prefix = scn.nextLine().toLowerCase().trim();
				choice01 = prefix.charAt(0);
				if (choice01 != '*') {
					try {
	            	if (!prefix.matches("[a-zA-Z]+")) {
	                    throw new IllegalArgumentException("Invalid input: Please enter only alphabetic characters.");
	                }
				
				

					// This section of code is for performing spell check and make suggestions
					boolean isWordInTrie = SpellCheck.searchInTrie(prefix);
					if (isWordInTrie) {
						System.out.println("'" + prefix + "' is a valid word.");
					} else {
						List<String> suggestions = SpellCheck.suggestCorrections(prefix);
						if (suggestions.isEmpty()) {
							System.out.println("\nNo suggestions found for '" + prefix + "'");
						} else {
							System.out.println("Suggestions for '" + prefix + "':");
							for (String suggestion : suggestions) {
								System.out.println(suggestion);
								System.out.println();

							}
							System.out.println("Please enter a correct word from the suggestions.");
							prefix = scn.nextLine();
						}
					}
					// call function finditems to output 5 cheapest deals
					List<String[]> deals1 = finditems(DataF1, prefix);
					List<String[]> deals2 = finditems(DataF2, prefix);
					List<String[]> deals3 = finditems(DataF3, prefix);

					Top5(deals1, deals2, deals3);
					} catch (Exception e) {
						System.out.println("Exception caught: " + e.getMessage());					
					}
				}
			} while (choice01 != '*');
		
	}

	// function to read from the csv file and store data
	private static List<String[]> readCSV(String fp, String fn) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fp))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(","); 
				data.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private static List<String[]> finditems(List<String[]> data, String item) {
		List<String[]> columns = new ArrayList<>();
		for (String[] row : data) {
			if (row.length > 1 && row[0].toLowerCase().contains(item.toLowerCase())) {
				columns.add(row);
			}
		}
		return columns;
	}

	private static void Top5(List<String[]> file1, List<String[]> file2, List<String[]> file3) {
		Top5ForFile(file1, "LoblawsData.csv");
		Top5ForFile(file2, "JioMartData2.csv");
		Top5ForFile(file3, "TargetData.csv");
	}

	private static void Top5ForFile(List<String[]> data, String fn) {
		data.sort(Comparator.comparingDouble(o -> {
			// replace special characters
			String priceStr = o[1].replaceAll("[^\\d.]", "");
			try {
				return Double.parseDouble(priceStr);
			} catch (NumberFormatException e) {
				return Double.MAX_VALUE;
			}
		}));

		int count = Math.min(5, data.size());
		System.out.println("\nTop 5 cheapest prices for the item from " + fn + ":");
		if (count == 0) {
			System.out.println("No matching record find.");

		}
		for (int i = 0; i < count; i++) {
			String[] row = data.get(i);
			System.out.println(row[0] + " - " + row[1]);
		}
		System.out.println();
	}
}

package FoodPriceAnalysis;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FindPatternUsingRegEx {

	public static void main(String[] args) throws IOException, InterruptedException {

		int csv_colIndex = 0;
		List<String> itm_item = func_readfile(csv_colIndex);
		Scanner scn = new Scanner(System.in);
		int nc;

		try {
			do {
				System.out.println(
						"\nDo you want to find regex based on already selected regex patterns to find : 1gal, 0.5gal, milk, etc ENTER 1 \n-OR- \nEnter a word to find products based on keywords. ENTER 2 \n-OR- \nENTER 0 to EXIT.");

				nc = scn.nextInt();

				if (nc == 1) {
					// By default static patterns used to find entries with 1gal, 0.5gal, milk,
					// lactose free and reduced fat.
					func_searchpatt(itm_item, "\\b1gal\\b");
					func_searchpatt(itm_item, "\\b0.5gal\\b");
					func_searchpatt(itm_item, "\\b(?i)milk\\b");
					func_searchpatt(itm_item, "(?i)\\blactose\\s*-?\\s*free\\b");
					func_searchpatt(itm_item, "\\b\\d+% Reduced Fat\\b");
				} else if (nc == 2) {
					char choice;

					do {

						// Taking a word from user and creating Regular expression based on that word to
						// find the entries from CSV File
						System.out.println(
								"\nEnter a word to find from the documents using regex pattern or Enter(*) to exit : ");
						Scanner scn1 = new Scanner(System.in);
						String user_word = scn1.nextLine();
						try {
							if (!user_word.matches("[a-zA-Z]+")) {
								throw new IllegalArgumentException(
										"Invalid input: Please enter only alphabetic characters.");
							}

						} catch (IllegalArgumentException e) {
							System.out.println("Exception caught: " + e.getMessage());
						}
						// creating regex patters for user inputted word
						String user_word_regexpatt = "\\b(?i)" + user_word + "\\b";

						choice = user_word.charAt(0);
						if (choice != '*') {
							// call function to find entries from csv file
							func_searchpatt(itm_item, user_word_regexpatt);
						}

					} while (choice != '*');

				} else if (nc == 0) {
					FoodPriceAnalysis.main(args);
				} else {
					System.out.println("Invalid option... ... Please select from valid options" + "");
				}
			} while (nc != '0');
		} catch (Exception e) {
			System.out.println("Exception caught: " + "Please Enter Integer Value");
			FindPatternUsingRegEx.main(args);

		}
	}

	// Function to read the files
	private static List<String> func_readfile(int csv_colIndex) {
		List<String> item_list = new ArrayList<>();

		String folderPath = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\";

		File folder = new File(folderPath);
		File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

		if (files != null) {
			for (File file : files) {
				int line_num = 1;
				try (BufferedReader obj_read = new BufferedReader(new FileReader(file))) {
					String line;

					// obj_read.readLine(); // Skip header line if present
					while ((line = obj_read.readLine()) != null) {
						String[] clmns = line.split(",");
						if (clmns.length > csv_colIndex) {
							String text = clmns[csv_colIndex];
							String[] itm_arr = text.split(",");
							String filename_line = file.getName() + " " + "line " + line_num;
							for (String itm : itm_arr) {
								item_list.add(itm + "::" + filename_line);
							}
							line_num++;

						}

					}
				} catch (IOException e) {
					e.printStackTrace();

					return null;
				}

			}
		}
		return item_list;
	}

	// function to search the entries containing based on regex pattern
	private static void func_searchpatt(List<String> item_list, String rgx_patt) {
		System.out.println("");
		List<String> str_line = new ArrayList<>();

		String string_line;
		// String l_no = "";
		boolean found = false;
		for (String item : item_list) {
			String l_no = null;
			String[] obj_readeak_str = item.split("::");

			if (obj_readeak_str.length >= 2) {
				string_line = obj_readeak_str[0].trim();
				str_line.add(string_line);
				l_no = obj_readeak_str[1];

			} else {
				System.out.println(" '::' not found.");
			}
			// compiling the regex pattern using compile class
			Pattern p = Pattern.compile(rgx_patt);
			Matcher m = p.matcher(item);

			// finding matching patterns based on regex compiled patterns
			while (m.find()) {
				String m_substr = m.group();
				if (!m_substr.equals(" ")) {
					System.out.println("Found: " + "'" + m_substr + "'" + " in " + l_no + " " + obj_readeak_str[0]);
					found = true;
				}

			}
		}
		if (!found) {
			System.out.println("No matching pattern found");

		}
	}
}

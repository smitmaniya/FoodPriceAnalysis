package FoodPriceAnalysis;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FoodPriceAnalysis {

	public static void main(String[] args) throws IOException, InterruptedException {
		int option = 0, op = 0;
		while (true) {
			{
				Scanner sc = new Scanner(System.in);
				// Asking the user to enter the choice
				System.out.println("\nInput your choice from the following menu:");
				System.out.println(
						"\n1.Scrap Data \n2.Frequency Count \n3.Search Frequency \n4.Data Validation using regex \n5.Spell Checking \n6.Word Completion \n7.Inverted Indexing \n8.Finding patterns using regex \n9.Page Ranking \n10.Cheapest Deals \n11.Exit");

				try {
					option = sc.nextInt();
					switch (option) {
					case 1:
						System.out.println(
								"Which website you want to scrap? Select from 1. Loblaws 2. Target 3. JioMart");
						try {
							op = sc.nextInt();
							if (op == 1) {
								Crawl_LobLaws.main(args);

							} else if (op == 2) {
								Crawl_Target.main(args);

							} else if (op == 3) {
								Crawl_JioMart.main(args);

							} else {
								System.out.println("Invalid choice");
							}

						} catch (Exception e) {
							System.out.println("Exception: " + e.getClass());
							break;
						}
						break;
					case 2:
						FrequencyCount.main(args);
						break;
					case 3:
						SearchFrequency.main(args);
						break;
					case 4:
						DataValidation.main(args);
						break;
					case 5:
						SpellCheck.main(args);
						break;
					case 6:
						WordCompletion.main(args);
						break;
					case 7:
						Inverted_Indexing.main(args);
						break;
					case 8:
						FindPatternUsingRegEx.main(args);
						break;
					case 9:
						PageRanking.main(args);
						break;
					case 10:
						CheapestDeals.main(args);
						break;						
					case 11:
						System.exit(0);
						break;
					default:
						System.out.println("Invalid choice");
					}
				} catch (Exception e) {
					System.out.println("Exception: " + e.getClass());
				}
			}
		}
	}
}
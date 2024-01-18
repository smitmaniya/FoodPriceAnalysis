package FoodPriceAnalysis;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class DataValidation {	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char ch;
        do {
        	
        // Prompt user for price input
        System.out.print("Enter a price: (or Enter '*' to Exit) ");
        String userInputPrice = scanner.nextLine();
        ch = userInputPrice.charAt(0);
    	if (ch != '*') {
       
        // Check if user input price is a valid format before proceeding
        validateAndCheckPrice(userInputPrice);
    	}
    	 //scanner.close();
        }while(ch!='*');
       
    }

    private static void validateAndCheckPrice(String userPrice) {
        if (isValidPriceFormat(userPrice)) {
            // Check if user input price exists in any CSV file
            checkPriceInCsvFiles(userPrice);
        } else {
            System.out.println("Invalid price format. Please enter a valid price.");
        }
    }

    private static boolean isValidPriceFormat(String input) {
        // Check if the input is a valid number format, allowing an optional dollar sign
        return input.matches("^\\$?\\d+(\\.\\d{2})?$");
    }

    private static void checkPriceInCsvFiles(String userPrice) {
        // Paths of CSV files
        String csvFilePath1 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\TargetData.csv";
        String csvFilePath2 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\LobLawsData.csv";
        String csvFilePath3 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\JioMartData2.csv";

        // Check if user input price exists in any CSV file
        if (isPriceInCsvFile(csvFilePath1, userPrice) ||
            isPriceInCsvFile(csvFilePath2, userPrice) ||
            isPriceInCsvFile(csvFilePath3, userPrice)) {
            System.out.println("Price exists in at least one CSV file.");
        } else {
            System.out.println("Price does not exist in any CSV file.");
        }
    }

    private static boolean isPriceInCsvFile(String csvFilePath, String userPrice) {
        // Read CSV file using OpenCSV
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Read each line from the CSV file
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length >= 2 && validateprice(nextLine[1], userPrice)) {
                    return true; // Price exists in this CSV file
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return false; // Price does not exist in this CSV file
    }

    // Custom validation for price (ignoring dollar sign for comparison)
    private static boolean validateprice(String priceInCsv, String userPrice) {
        return priceInCsv.replace("$", "").equals(userPrice.replace("$", ""));
    }
}
package FoodPriceAnalysis;
import java.io.*;
import java.util.*;

public class FrequencyCount {
	// creating method to count frequency of word
	  public static void processFile(String filePath, Map<String, Integer> frequencyMap) {
		  // bufferreader to read content from file and if error accure it throw exception in catch
	        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(filePath))) {
	           // to store line that read by bufferreader
	        	String line;
	        	String[] words;
	        	// it will continue untill not read all the line in file 
	            while ((line = bufferedreader.readLine()) != null) {
	            	// line's all word seprated by space and stored in to array of words
	               words = line.split("\\s+"); 
	                // for loop to travers through all word into list and add into hash map
	                for (String word : words) {
	                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim();
	                    if (!word.isEmpty()) {
	                        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
// here is method to get frequency of specific word from hash table if it exist it give frequency otherwise it give 0
	   public static int getFrequency(Map<String, Integer> frequencyMap, String word) {
	        return frequencyMap.getOrDefault(word, 0);
	    }
	// creating hash map to store key value pair of data
    public  static Map<String, Integer> TargetDataMap = new HashMap<>();
    public static Map<String, Integer> LoblowsDataMap = new HashMap<>();
    public static Map<String, Integer> JioMartData = new HashMap<>();

    public static void main(String[] args) {
    	//calling mothod to get frequency of each word in file and passing javafile and hashmap 
        processFile("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\TargetData.csv", TargetDataMap);
        processFile("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\LoblawsData.csv", LoblowsDataMap);
        processFile("C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\JioMartData2.csv", JioMartData);

        String userInput;
        char choice;

        do {
        	// scanner to get user input
            Scanner scanner = new Scanner(System.in);
        	 System.out.print("Enter a word (or '*' to quit): ");
        	 // store user input into userinput string and remove whitespace around userinout and convert into lower case
             userInput = scanner.nextLine().trim().toLowerCase();
             choice = userInput.charAt(0);
             
             if (choice != '*') {
            	 if(!userInput.isEmpty()) {

                	 //calling getfrequency method to store frequency of userinput into file 
                int freqTargetData = getFrequency(TargetDataMap, userInput);
                int freqloLoblowData = getFrequency(LoblowsDataMap, userInput);
                int freqJioMartData = getFrequency(JioMartData, userInput);

                     System.out.println("'" + userInput + "' frequency in TargetData: " + freqTargetData);
                     System.out.println("'" + userInput + "' frequency in LobLowData: " + freqloLoblowData);
                     System.out.println("'" + userInput + "' frequency in JioMartData: " + freqJioMartData);
               
            	 }else {
            		 System.out.println("Please enter something");
            	 }
             } 

             }while (choice != '*');

		} 

  
}

package FoodPriceAnalysis;

import org.apache.commons.lang3.Validate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Crawl_JioMart {
	//web driver initialization
	

	public static void main(String[] args) throws InterruptedException, IOException {
		// setting Chrome Driver
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\sanja\\Downloads\\geckodriver-v0.33.0-win64\\geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		
		WebDriver driver = new FirefoxDriver(options);
		// csv file handling from local directory
		String filepath1 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\JioMartData2.csv";
		File file1 = new File(filepath1);
		FileWriter outputfile1 = new FileWriter(file1);
		CSVWriter writer1 = new CSVWriter(outputfile1);
		String[] data1 = { "Product Name", "Price in CAD" };
		writer1.writeNext(data1);
		// Scanner for user input
		Scanner scansearch = new Scanner(System.in);
		String wordse;
		char choice01;

		Thread.sleep(2000);
		// user input and web scrapping loop
		try {

		do {
			// Taking user input for the word to search or * to exit
			System.out.println("Enter a word to search from the website or (press * to exit).");
			wordse = scansearch.nextLine();
			choice01 = wordse.charAt(0);
			if (choice01 != '*') {
				// Navigating to JioMart website
				driver.get("https://www.jiomart.com/");
				Thread.sleep(2000);
				driver.findElement(By.id("autocomplete-0-input")).click();
				Thread.sleep(2000);
				WebElement searchElem = driver.findElement(By.id("autocomplete-0-input"));
				searchElem.sendKeys(wordse);
				driver.findElement(By.id("autocomplete-0-input")).sendKeys(Keys.ENTER);
				
				Thread.sleep(10000);
				//JavascriptExecutor js = (JavascriptExecutor) driver;
				List<String> list = new ArrayList<>();
				List<String> list1 = new ArrayList<>();
				String s;
				String s1;
				// Scrolling down the page
				new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
				Thread.sleep(2000);

				new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
				Thread.sleep(4000);
				// Extracting product names and prices
				List<WebElement> product = driver.findElements(By.xpath(
						"//div[contains(@class, 'plp-card-details-name line-clamp jm-body-xs jm-fc-primary-grey-80')]"));
				List<WebElement> price = driver.findElements(By.xpath("//span[@class='jm-heading-xxs jm-mb-xxs']"));
				if(product.size()==0){
					System.out.println("No Product Found");
				}
				
				
				// Processing and printing product names and prices
				for (int ij = 0; ij < product.size(); ij++) {
					// obtain text
					s = product.get(ij).getText();
					System.out.println(ij + " " + "Product Name is: " + s);
					list.add(s);
					s1 = price.get(ij).getText();
					System.out.println(ij + " " + "Price is: " + s1);
					String priceWithoutSym = s1.replaceAll("[^\\d.]", "");
					//System.out.println(priceWithoutSym);
					double number = Double.parseDouble(priceWithoutSym);
					
					// Converting price to CAD
					
					double excrate = 0.016; 
				    double cadAmount = number * excrate;
				    
				    DecimalFormat df = new DecimalFormat("#.##");
			        String formattedValue = df.format(cadAmount);
			        list1.add(String.valueOf(formattedValue));
					String[] data2 = { s, "$"+String.valueOf(formattedValue) };
					writer1.writeNext(data2);

				}
				//writer1.close();
			} else {
				break;
			}
		
		} while (choice01 != '*');
		} catch (Exception e) {
			System.out.println("Exception: " + e.getClass());
		}
		// close chrome driver
		driver.close();
	writer1.close();
	}
	
}
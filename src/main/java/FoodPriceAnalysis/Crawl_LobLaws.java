package FoodPriceAnalysis;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//Class to crawl data from the LobLaws website
public class Crawl_LobLaws {
	// Create a ChromeDriver instance
	WebDriver driver = new ChromeDriver();
	// Main method
	public static void main(String[] args) throws InterruptedException, IOException {
		// Specify the file path for CSV output
		String filepath1 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\LoblawsData.csv";
		// Create a file and CSVWriter for writing data to the CSV file
		
		File file1 = new File(filepath1);
		FileWriter outputfile1 = new FileWriter(file1);
		CSVWriter writer1 = new CSVWriter(outputfile1);
		// Specify CSV header
		
		String[] data1 = { "Product Name", "Price in CAD" };
		writer1.writeNext(data1);
		// Set the path for geckodriver (Firefox driver)
		
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\sanja\\Downloads\\geckodriver-v0.33.0-win64\\geckodriver.exe");

		// Configure Firefox options and create a FirefoxDriver instance
		
		FirefoxOptions options = new FirefoxOptions();
		WebDriver driver = new FirefoxDriver(options);
		// Initialize variables for user input
		String wordse;
		char choice01;
		// Introduce a delay for 2 seconds
		Thread.sleep(2000);
		// Start a loop to allow multiple searches
		
		do {
			//writer1.close();
			// Set implicit wait time for 8 seconds
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			Scanner scansearch = new Scanner(System.in);
			// Prompt user to enter a word to search or '*' to exit
			
			System.out.println("Enter a word to search from the website or (press * to exit).");
			wordse = scansearch.nextLine();
			choice01 = wordse.charAt(0);
			 // Check if user wants to exit
			
			if (choice01 != '*') {
				driver.get("https://www.loblaws.ca/");
				driver.findElement(By.id("autocomplete-listbox-desktop-site-header-")).click();
				Thread.sleep(2000);
				// Perform a search
				WebElement searchElem = driver.findElement(By.id("autocomplete-listbox-desktop-site-header-"));

				searchElem.sendKeys(wordse);
				driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[3]/div/header/div[1]/div[2]/form/button"))
						.click();
				JavascriptExecutor js1 = (JavascriptExecutor) driver;
				// Introduce a delay for 4 seconds
				Thread.sleep(4000);
				// Initialize lists to store product information
				List<String> list = new ArrayList<>();
				List<String> list1 = new ArrayList<>();
				// Extract product details from the search results
				List<WebElement> product_title = driver
						.findElements(By.xpath("//span[@class='product-name__item product-name__item--brand']"));

				List<WebElement> product_description = driver
						.findElements(By.xpath("//span[@class='product-name__item product-name__item--name']"));
				List<WebElement> pricedollar = driver.findElements(By.xpath(
						"//span[@class='price__value selling-price-list__item__price selling-price-list__item__price--now-price__value']"));

				/*System.out.println(product_title.size());
				System.out.println(product_description.size());
				System.out.println(pricedollar.size());*/

				for (int ij = 0; ij < product_title.size(); ij++) {
					String s = product_title.get(ij).getText();
					String w = product_description.get(ij).getText();
					String t = s + " " + w;
					System.out.println(ij + " " + "Product Name is: " + t);
					list.add(t);
					String s1 = pricedollar.get(ij).getText();
					System.out.println(ij + " " + "Price is: " + s1);
					list1.add(s1);
					String[] data2 = { t, s1 };
					writer1.writeNext(data2);
				}

				Thread.sleep(3000);

			}
			else {
				break;
			}

		} while (choice01 != '*');
		//driver.close();
writer1.close();
	}
}
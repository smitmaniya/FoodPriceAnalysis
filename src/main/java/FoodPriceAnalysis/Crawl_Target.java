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
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
//Class to perform web scraping on the Target website

public class Crawl_Target {
	// Main method
	public static void main(String[] args) throws InterruptedException, IOException {
		// File path for CSV output
		String filepath1 = "C:\\Users\\sanja\\OneDrive\\Desktop\\ACC\\Assg3\\TargetData.csv";
		File file1 = new File(filepath1);
		FileWriter outputfile1 = new FileWriter(file1);
		CSVWriter writer1 = new CSVWriter(outputfile1);
		// Header for CSV file
		String[] data1 = { "Product Name", "Price in CAD" };
		writer1.writeNext(data1);
		// Setting up the GeckoDriver for Firefox
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\sanja\\Downloads\\geckodriver-v0.33.0-win64\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		WebDriver driver = new FirefoxDriver(options);

		String wordse;
		char choice01;
		// Pause for 2 seconds
		Thread.sleep(2000);
		try {
			// Loop to perform web scraping based on user input
			do {
				 // Setting implicit wait time for elements to load
				driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
				Scanner scansearch = new Scanner(System.in);
				System.out.println("Enter a word to search from the website or (press * to exit).");
				wordse = scansearch.nextLine();
				choice01 = wordse.charAt(0);
				if (choice01 != '*') {
					// Navigating to the Target website
					driver.get("https://www.target.com/c/grocery/-/N-5xt1a");
					Thread.sleep(2000);
					// Clicking on the search bar
					driver.findElement(By.id("search")).click();
					Thread.sleep(2000);
					// Entering the search term
					WebElement searchElem = driver.findElement(By.id("search"));
					searchElem.sendKeys(wordse);
					// Clicking on the search button
					driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/nav/div[6]/form/button")).click();
					JavascriptExecutor js1 = (JavascriptExecutor) driver;

					Thread.sleep(4000);

					List<String> list = new ArrayList<>();
					List<String> list1 = new ArrayList<>();
					// Loop to scroll through the page and extract product information
					for (int i = 1; i < 2; i++) {
						js1.executeScript("window.scrollBy(0,2000)");
						Thread.sleep(4000);
						System.out.println("Scrapping page : " + i);
						 // Extracting product names and prices
						List<WebElement> product = driver
								.findElements(By.xpath("//div[@class='styles__Truncate-sc-1wcknu2-0 iZqUcy']"));
						List<WebElement> pricedollar = driver
								.findElements(By.cssSelector("[data-test='current-price']"));
						if(product.size()==0){
							System.out.println("No Product Found");
						}
						 // Loop to process each product
						for (int ij = 0; ij < product.size(); ij++) {
							String s = product.get(ij).getText();
							System.out.println(ij + " " + "Product Name is: " + s);
							list.add(s);
							String s1 = pricedollar.get(ij).getText();
							System.out.println(ij + " " + "Price is: " + s1);
							 // Extracting and converting prices to CAD
							String priceinput = s1.trim();

							int index = priceinput.indexOf(' ');
							String fullprice;
							String priceperouch;
							 // Separate the strings
							if (index != -1) { 
								// Separate the strings
								fullprice = priceinput.substring(0, index);
								priceperouch = priceinput.substring(index + 1);

							} else {
								fullprice = s1;
								priceperouch = "";
							}
							// Removing symbols and converting to double
							String priceWithoutSym = fullprice.replaceAll("[^\\d.]", "");
							double number = Double.parseDouble(priceWithoutSym);
							// Converting to CAD with exchange rate
							double exc_rate = 1.36;
							double cadAmount = number * exc_rate;
							DecimalFormat df = new DecimalFormat("#.##");
					        String formattedValue = df.format(cadAmount);
					        // Adding product information to the list
							list1.add(String.valueOf(formattedValue));
							String[] data2 = { s, "$"+String.valueOf(formattedValue),priceperouch  };
							writer1.writeNext(data2);
						}

						Thread.sleep(3000);
						// Clicking on the next page button
						new WebDriverWait(driver, Duration.ofSeconds(10))
								.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fpXJuh"))).click();

						Thread.sleep(2000);
					}
				} else {
					break;
				}

			} while (choice01 != '*');
			 // writer1.close();
			//driver.close();
			// System.exit(0);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getClass());
		}
writer1.close();
	}

}
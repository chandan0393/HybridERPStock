package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class functionLibrary {
	public static WebDriver driver;
	public static Properties conpro;

	// method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
				{
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
				}
		else
		{
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	
	//method for launching url
	public static void OpenUrl()
	{
		driver.get(conpro.getProperty("url"));
	}
	//method for wait to any element
	public static void waitForElement(String LocatorType, String LocatorValue, String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			//wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			//wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			//wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		
	}
	//method for any text box
	public static void typeAction(String LocatorType, String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}
	//method for clicking buttons,checkboxes,radiobutton,image,links
	public static void clickAction(String LocatorType, String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	//method for validate title
	public static void validateTitle(String Expected_title)
	{
		String Actual_title = driver.getTitle();
		try {
		Assert.assertEquals(Actual_title, Expected_title, "Title is not matching");
		}
		catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method to close browser
	public static void closeBrowser()
	{
		driver.quit();
	}

	//method for dropDownAction/list boxes
	public static void dropDownAction(String LocatorType,String LocatorValue, String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			//convert test data cell into int
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			//convert test data cell into int
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			//convert test data cell into int
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);
		}
	}
	
	//method for capture stock number into notepad
	public static void captureStock(String LocatorType, String LocatorValue ) throws Throwable 
	{
		String StockNumber = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			StockNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			StockNumber = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			StockNumber = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		// writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNumber);
		bw.flush();
		bw.close();
	}
	//verify stock number in stock table
	public static void stockTable() throws Throwable
	{
		//read path of notepad file
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		//click search panel if search textbox not displayed
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//click search panel
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		//table[@class ='table ewTable']/tbody/tr[1]/td[8]/div/span/span
		String Act_Data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"  "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data," Stock number not found");
		} catch (AssertionError a) {
			System.out.println(a.getMessage());
		}
		
	}
	
	
	
	
}

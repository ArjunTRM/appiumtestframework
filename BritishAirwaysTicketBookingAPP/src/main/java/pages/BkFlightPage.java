package pages;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import utils.ExcelFetch;
import wrappers.ProximusWrapper;

/**
 * @author sundar.siva
 *
 */
public class BkFlightPage extends ProximusWrapper {
	private static Properties prop;
	public Map<String, String> capData1 = new HashMap<String, String>();

	/**
	 * @param driver
	 * @param test - extent test for reporting
	 * @param capData1 - device capabilities
	 * Constructor which sets the driver,extent test and device capabilities
	 */
	public BkFlightPage(MobileDriver driver, ExtentTest test, Map<String, String> capData1) {
		this.driver = driver;
		this.test = test;
		this.capData1 = capData1;
		prop = new Properties();
		try {
			if (capData1.get("PlatformName").equalsIgnoreCase("Android")) {
				prop.load(new FileInputStream(new File("./Locators/Android/bkflight.properties")));
			}
			else if (capData1.get("PlatformName").equalsIgnoreCase("ios")) {
				prop.load(new FileInputStream(new File("./Locators/iOS/bkflight.properties")));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @throws InterruptedException
	 * Gets the input from the excel sheet and login to the application
	 */
	public BkFlightPage BookFlight() throws InterruptedException {
	
		Map<String, String> searchMap= new ExcelFetch().getDataFromExcel(tcName, "account2");

	
		//click on from search icon
		verifyElementIsDisplayed(prop.getProperty("icon.from"));
		click(prop.getProperty("icon.from"));
		
		//select a Boarding point from the list
		verifyElementIsDisplayed(prop.getProperty("field.search"));
		enterText(prop.getProperty("field.search"),searchMap.get("from"));
		
		System.out.println(searchMap.get("from"));
		verifyElementIsDisplayed(prop.getProperty("link.lon"));
		click(prop.getProperty("link.lon"));
		
		//click on To search icon
		verifyElementIsDisplayed(prop.getProperty("icon.to"));
		click(prop.getProperty("icon.to"));
		
		//select a destination point from the list
		verifyElementIsDisplayed(prop.getProperty("field.search"));
		enterText(prop.getProperty("field.search"),searchMap.get("to"));
		verifyElementIsDisplayed(prop.getProperty("link.paris"));
		click(prop.getProperty("link.paris"));
		
		
		//select toggle button
		verifyElementIsDisplayed(prop.getProperty("button.oneway"));
		click(prop.getProperty("button.oneway"));
		
		//select choose date link
		verifyElementIsDisplayed(prop.getProperty("link.choosedate"));
		click(prop.getProperty("link.choosedate"));
		
		//select a date from date picker
		verifyElementIsDisplayed(prop.getProperty("link.selectdate"));
		click(prop.getProperty("link.selectdate"));
		verifyElementIsDisplayed(prop.getProperty("button.done"));
		click(prop.getProperty("button.done"));
		
		//click on Find button
		verifyElementIsDisplayed(prop.getProperty("button.find"));
		click(prop.getProperty("button.find"));
		
		
		
		
			
		return this;
	}

	

}

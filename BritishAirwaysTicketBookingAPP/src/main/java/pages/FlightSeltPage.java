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
public class FlightSeltPage extends ProximusWrapper {
	private static Properties prop;
	public Map<String, String> capData1 = new HashMap<String, String>();

	/**
	 * @param driver
	 * @param test - extent test for reporting
	 * @param capData1 - device capabilities
	 * Constructor which sets the driver,extent test and device capabilities
	 */
	public FlightSeltPage(MobileDriver driver, ExtentTest test, Map<String, String> capData1) {
		this.driver = driver;
		this.test = test;
		this.capData1 = capData1;
		prop = new Properties();
		try {
			if (capData1.get("PlatformName").equalsIgnoreCase("Android")) {
				prop.load(new FileInputStream(new File("./Locators/Android/flightselt.properties")));
			}
			else if (capData1.get("PlatformName").equalsIgnoreCase("ios")) {
				prop.load(new FileInputStream(new File("./Locators/iOS/flightselt.properties")));
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
	public FlightSeltPage FlightSelect() throws InterruptedException {

	//	Map<String, String> signinDetailsMap = new ExcelFetch().getDataFromExcel(tcName, "account1");

		//tap on the empty screen
		verifyElementIsDisplayed(prop.getProperty("link.empty"));
		click(prop.getProperty("link.empty"));
		
		
		//from and to verification
		verifyElementIsDisplayed(prop.getProperty("text.ft"));
		click(prop.getProperty("text.ft"));
		
		//get the ticket price 
		verifyElementIsDisplayed(prop.getProperty("link.price"));
		getText(prop.getProperty("link.price"));
		
		//select one ticket
		verifyElementIsDisplayed(prop.getProperty("link.price"));
		click(prop.getProperty("link.price"));
		
		//click on choose package button
		verifyElementIsDisplayed(prop.getProperty("button.choose"));
		click(prop.getProperty("button.choose"));


		return this;
	}



}

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
public class PsngrDtlPage extends ProximusWrapper {
	private static Properties prop;
	public Map<String, String> capData1 = new HashMap<String, String>();

	/**
	 * @param driver
	 * @param test - extent test for reporting
	 * @param capData1 - device capabilities
	 * Constructor which sets the driver,extent test and device capabilities
	 */
	public PsngrDtlPage(MobileDriver driver, ExtentTest test, Map<String, String> capData1) {
		this.driver = driver;
		this.test = test;
		this.capData1 = capData1;
		prop = new Properties();
		try {
			if (capData1.get("PlatformName").equalsIgnoreCase("Android")) {
				prop.load(new FileInputStream(new File("./Locators/Android/psngerdtl.properties")));
			}
			else if (capData1.get("PlatformName").equalsIgnoreCase("ios")) {
				prop.load(new FileInputStream(new File("./Locators/iOS/psngerdtl.properties")));
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
	public PsngrDtlPage PsngrDtl() throws InterruptedException {

		Map<String, String> PassengerDetailsMap = new ExcelFetch().getDataFromExcel(tcName, "account2");
		
		//verify the price
		verifyElementIsDisplayed(prop.getProperty("text.vprice1"));
		
		//Add passenger details
		verifyElementIsDisplayed(prop.getProperty("link.add"));
		click(prop.getProperty("link.add"));
		
		verifyElementIsDisplayed(prop.getProperty("field.title"));
		click(prop.getProperty("field.title"));
		verifyElementIsDisplayed(prop.getProperty("radio.title"));
		click(prop.getProperty("radio.title"));
		
		//enter passenger details from excel
		verifyElementIsDisplayed(prop.getProperty("field.fname"));
		enterText(prop.getProperty("field.fname"),PassengerDetailsMap.get("firstname") );
		
		verifyElementIsDisplayed(prop.getProperty("field.lname"));
		enterText(prop.getProperty("field.lname"), PassengerDetailsMap.get("lastname"));
		
		/********************************************************/
		
		verifyElementIsDisplayed(prop.getProperty("radio.No"));
		click(prop.getProperty("radio.No"));
		
		verifyElementIsDisplayed(prop.getProperty("button.Continue"));
		click(prop.getProperty("button.Continue"));
		
		
		
		
		return this;
	}



}

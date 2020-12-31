package wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.testng.asserts.SoftAssert;


import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import utils.Reporter;

/**
 * @author sundar.siva
 *
 */
public class GenericWrappers extends Reporter {

	public MobileDriver driver;
	protected Properties prop;
	public Map<String, String> capData = new HashMap<String, String>();
	public WebDriverWait wait;
	public static String productNameSearchResults="";
	public static String productPriceSearchResults="";
	public static int productQuantity;
    public static boolean offerProduct=false;
	

	public GenericWrappers(MobileDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public GenericWrappers() {

	}
	

	/**
	 * Starts the extent test report by giving name and decription for test case
	 * Assigns author name and category
	 */
	public void startTestReport() {
		test = startTestCase(testCaseName, testDescription);
		test.assignCategory(category);
		test.assignAuthor(authors);
	}


	/**
	 * Closes the application
	 */
	public void closeApp() {
		try {
			driver.quit();
		} catch (Exception e) {

		}

	}
	public void swipeFullFromRightToLeft() {
		org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
		int startx = (int) (scrnSize.width * 0.8);
		int endx = (int) (1);
		int endy = (int) (scrnSize.height / 2);
		((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 500);
	}

	/**
	 * Launch the application(Driver Initialization)
	 */
	public void invokeApp() {

		System.out.println("*************** Launching APP ***************");

		URL urlObj = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
	

		try {
			String dir = System.getProperty("user.dir");
			urlObj = new URL("http://" + "127.0.0.1" + ":" + capData.get("Port") + "/wd/hub");

			switch (capData.get("PlatformName").toLowerCase()) {
			case "android":
				if(capData.get("Automation").equalsIgnoreCase("real"))
				{
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capData.get("DeviceName"));
					capabilities.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
					capabilities.setCapability("automationName", "UiAutomator2");
					capabilities.setCapability("systemPort", capData.get("systemPort"));
					capabilities.setCapability("newCommandTimeout", 9999);
					capabilities.setCapability("skipServerInstallation", true);
					capabilities.setCapability(MobileCapabilityType.APP, "app/android/MyProximus-be.belgacom.hello-47902-v5.10.0.apk");
					
					urlObj = new URL("http://" + "127.0.0.1" + ":" + capData.get("Port") + "/wd/hub");

					driver = new AndroidDriver<MobileElement>(urlObj, capabilities);
				}
				else if(capData.get("Automation").equalsIgnoreCase("cloud"))
				{
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capData.get("DeviceName"));
					capabilities.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
					capabilities.setCapability("automationName", "UiAutomator2");
					capabilities.setCapability("newCommandTimeout", 9999);
					capabilities.setCapability("testobject_app_id", "1");
				    capabilities.setCapability("name","British Airways");
	            	capabilities.setCapability("testobject_api_key", "E8BE781F47BC4A15AFBE8F7C1820E1BC");

				    urlObj=new URL("https://us1.appium.testobject.com/wd/hub");
				    
					driver = new AndroidDriver<MobileElement>(urlObj, capabilities);
				}
				
				break;
				
			case "ios":
				if(capData.get("Automation").equalsIgnoreCase("real"))
				{
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capData.get("DeviceName"));
					capabilities.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
					capabilities.setCapability("automationName", "XCUITest");
					capabilities.setCapability("systemPort", capData.get("systemPort"));
					capabilities.setCapability("newCommandTimeout", 9999);
					capabilities.setCapability("skipServerInstallation", true);
					capabilities.setCapability(MobileCapabilityType.APP, "app/ios/MyProximus.ipa");
					
					urlObj = new URL("http://" + "127.0.0.1" + ":" + capData.get("Port") + "/wd/hub");

					driver = new IOSDriver<MobileElement>(urlObj, capabilities);
				}
				else if(capData.get("Automation").equalsIgnoreCase("cloud"))
				{
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capData.get("DeviceName"));
					capabilities.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
					capabilities.setCapability("automationName", "UiAutomator2");
					capabilities.setCapability("newCommandTimeout", 9999);
					capabilities.setCapability("testobject_app_id", "1");
				    capabilities.setCapability("name","Login to British Airway and book flight");
	            	capabilities.setCapability("testobject_api_key", "93BC50DD4E194DCA9847D6DCB01AC008");

				    urlObj=new URL("https://us1.appium.testobject.com/wd/hub");
				    
					driver = new AndroidDriver<MobileElement>(urlObj, capabilities);
				}
				
				break; 

			default:
				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param property
	 * Clicks an element
	 */
	public void click(String property) {
		By byProperty = getLocator(property);
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(byProperty);
			driver.findElement(byProperty).click();
			System.out.println("Element is Clicked");

		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}

	}


	/**
	 * @param property
	 * @param data
	 * Sends the balue to an input field 
	 */
	public void enterText(String property, String data) {
		MobileElement element = null;
		MobileElement element2 = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			element.clear();
			element2 = (MobileElement) driver.findElement(getLocator(property));
			element2.sendKeys(data);
			System.out.println("Data is entered to the required Field");
		}
		catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}

	}

	/**
	 * @param property
	 * @param text
	 * @return
	 * Compares the given text with the element text in UI 
	 */
	public boolean verifyText(String property, String text) {
		MobileElement element = null;
		String sText = "";
		boolean val=false;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			sText = element.getText();
			if (sText.equalsIgnoreCase(text)) {
				val= true;
			} 
		}
			 catch (Exception e) {
			//verifyStep(e.getMessage(), "FAIL");
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertTrue(false, e.getMessage());
		}
		return val;
	}


	/**
	 * @param property
	 * Check whether the element is displayed with wait time
	 */
	public void verifyElementIsDisplayed(String property) {
		MobileElement element=null;
		try {
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			element = (MobileElement) driver.findElement(getLocator(property));
			element.isDisplayed();
		}catch (Exception e) {
			Assert.assertTrue(false, "Element not displayed\n" + e.getMessage());
		} 
	}


	/**
	 * @param property
	 * @return
	 * Check whether the element is displayed or not (returns boolean value)
	 */
	public boolean verifyIsDisplayed(String property) {
		boolean present=false;
		MobileElement element=null;
		try {
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			element = (MobileElement) driver.findElement(getLocator(property));
			element.isDisplayed();
			present=true;
			
		}catch (Exception e) {
			present=false;		} 
		return present;
	}
	

	/**
	 * @param property
	 * @param timeoutInSecs
	 * Verifies whether value is present or not
	 */
	public void verifyElementIsPresent(String property, long timeoutInSecs){
		try{
			long startTime = System.currentTimeMillis();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while (System.currentTimeMillis() < (startTime + (timeoutInSecs * 1000))) {
				if (verifyIsDisplayed(property)){
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					return;
				}
			}
			Assert.assertTrue(false, property + " is displayed or not validation");
		}
		catch (ElementNotFoundException e)
		{
			Assert.assertTrue(false, property + "Element not found\n" + e.getMessage());
		}

		catch (TimeoutException e) {
			Assert.assertTrue(false, property + "Time out error\n" + e.getMessage());
		}
		catch (ElementNotSelectableException e){
			Assert.assertTrue(false, getLocator(property) + "Element not Selectable\n" + e.getMessage());
		}
		catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, property + "Element not Visible\n" + e.getMessage());
		}
		catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, property + "Element not Interatable\n" + e.getMessage());
		}
		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	/**
	 * @param property
	 * @param timeoutInSecs
	 * Verfies element not present in the UI
	 */
	public void verifyElementIsNotPresent(String property, long timeoutInSecs) {
		try{
			long startTime = System.currentTimeMillis();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while (System.currentTimeMillis() < (startTime + (timeoutInSecs * 1000))) {
				if (verifyIsDisplayed(property)) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					return;
				}
			}
			Assert.assertTrue(true, property + " is displayed or not validation");

		} catch (ElementNotFoundException e) {
			Assert.assertTrue(true, property + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, property + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, getLocator(property) + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, property + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, property + "Element not Interatable\n" + e.getMessage());
		}

		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}

	}

 
	/**
	 * @param property
	 * @return
	 * Gets the text from the UI 
	 */
	public String getText(String property) {
		String bReturn = "";
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			return element.getText();
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		}
		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
		return bReturn;
	}

	
	/**
	 * @param property
	 * @param attribute
	 * @return
	 * Gets the attribute value for the element
	 */
	public String getAttribute(String property, String attribute) {
		String bReturn = "";

		MobileElement element = null;

		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			return element.getAttribute(attribute);
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {

			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {

			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		}

		catch (Exception e) {

			Assert.assertTrue(false, e.getMessage());
		}

		return bReturn;
	}

	/* (non-Javadoc)
	 * @see utils.Reporter#takeSnap()
	 * takes screenshot
	 */
	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File("./reports/images/" + number + ".jpg"));
		} catch (IOException e) {
		} catch (Exception e) {

			System.out.println("The app has been closed.");
		}
		return number;
	}


	

	/**
	 * @param property
	 * @return
	 * Gets the locator from property file and extracts it based on id,name,xpath, etc..
	 */
	public By getLocator(String property) {
		String locator = property;

		String locatorType = locator.split("===")[0];
		String locatorValue = locator.split("===")[1];

		if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return By.className(locatorValue);
		else if (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);

		else
			return null;
	}

	/**
	 * @param property
	 * @return
	 * checks whether the checkbox, radio button is selected or not
	 */
	public boolean isSelected(String property) {
		boolean value = false;
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			value = driver.findElement(getLocator(property)).isSelected();

		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		}

		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	
			return value;

	}
	/**/

	/**
	 * @param property
	 * closes the keyboard
	 */
	public void keypadDown() {
		driver.hideKeyboard();
	}

  

	/**
	 * @param property
	 * clears the text from the input field
	 */
	public void clearElement(String property) {
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			element.clear();
		}
		catch (ElementNotFoundException e){
			Assert.assertTrue(false,element + "Element not found\n" + e.getMessage());
		}
		catch (TimeoutException e) {
			Assert.assertTrue(false,element + "Time out error\n" + e.getMessage());
		}
		catch (ElementNotSelectableException e)	{
			Assert.assertTrue(false,element + "Element not Selectable\n" + e.getMessage());
		}
		catch (ElementNotVisibleException e) {
			Assert.assertTrue(false,element + "Element not Visible\n" + e.getMessage());
		}
		catch (ElementNotInteractableException e) {
			Assert.assertTrue(false,element + "Element not Interatable\n" + e.getMessage());
		}
		catch (Exception e) {
			Assert.assertTrue(false,e.getMessage());
		}

	}
  
	/**
	 * @param pfName
	 * swipes on the screen from bottom to top that is swipe down
	 */
	public void swipeFullFromBottomToTop(String pfName) {
		System.out.println("Swiping......");
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int starty = (int) (scrnSize.height*0.3);
			int endy = (int) (scrnSize.height*0.8);
			if (pfName.equalsIgnoreCase("android")) {
				((AndroidDriver<WebElement>) driver).swipe(startx, endy, startx, starty, 1000);
			} 

		} catch (InterruptedException e) {
			Assert.assertTrue(false,e.getMessage());
		}
	}
	/**
	 * @param property
	 * @return
	 * verifies whether element present or not
	 */
	public boolean verifyElement(String property) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		boolean present = true;
		try {

			
			driver.findElement(getLocator(property));
			return present;

		} catch (Exception e) {
			present = false;
			return present;
		}

	}
	
	/**
	 * @param pfName
	 * @param property
	 * Swipes to the element on the screen
	 */
	public void swipeToElement(String pfName, String property) {
		while (true) {
			if (verifyElement(property)) {
				break;
			}
			swipeFullFromBottomToTop(pfName);
		}
	}

  
	/**
	 * @param pfName
	 * swipes on the screen from top to bottom that is swipe down
	 */
	public void swipeFullFromTopToBottom(String pfName) {

		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int endy = (int) (scrnSize.height - 1);
			int starty = (int) (scrnSize.height * 0.2);
			// int endx = (int) (scrnSize.width /2);
			if (pfName.equalsIgnoreCase("android")) {

				((AndroidDriver<WebElement>) driver).swipe(startx, starty, startx, endy, 3000);
			} 
			
		} catch (InterruptedException e) {
			Assert.assertTrue(false,e.getMessage());
		}

	}
	/**
	 * @param pfName
	 * @param property
	 * Swipes to the given elemet in upward direction
	 */
	public void swipeToElementUpwards(String pfName, String property) {
		while (true) {
			if (verifyElement(property)) {
				break;
			}
			swipeFullFromTopToBottom(pfName);
		}
		
	}
	/**
	 * Launch the app on the device
	 */
	public void launchApp() {
		System.out.println("Launching the app");
		driver.launchApp();
	}

	/**
	 * clicks on the android back button
	 */
	public void clickAndroidBack() {

		((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);

	}
	
	public void swipeToRefresh(String pfName) {
		org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
		int startx = (int) (50);
		int starty = (int) (scrnSize.height * 0.3);
		int endy = (int) (scrnSize.height * 0.9);
		if (pfName.equalsIgnoreCase("android")) {
			((AndroidDriver<WebElement>) driver).swipe(startx, starty, startx, endy, 3000);
		} else {
			((IOSDriver<WebElement>) driver).swipe(startx, starty, startx, endy, 3000);
		}
	}

	public void swipeFullFromRightToLeft(String pfName) {
		org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
		int startx = (int) (scrnSize.width * 0.8);
		int endx = (int) (1);
		int endy = (int) (scrnSize.height / 2);
		if (pfName.equalsIgnoreCase("android")) {
			((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 500);
		} else {
			
			System.out.println("("+startx+","+endy+")");
			System.out.println("("+endx+","+endy+")");
			((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 2000);
		}
	}

	public void swipeFromRightToLeft(String pfName) {
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width * 0.6);
			int endx = (int) (scrnSize.width * 0.3);
			int endy = (int) (scrnSize.height / 2);
			if (pfName.equals("android")) {
				((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
			} else {
				((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
			}

		} catch (InterruptedException e) {

		}
	}

	public void swipeFullFromLeftToRight(String pfName) {
		// System.out.println("Swiping full from left to right");
		org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
		int startx = (int) (scrnSize.width * 0.2);
		int endx = (int) (scrnSize.width * 0.8);
		int endy = (int) (scrnSize.height / 2);
		if (pfName.equals("android")) {
			((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 500);
		} else {
			((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 500);

		}

	}

	public void swipeFullFromLToRFromAPoint(String pfName, int y) {

		org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
		int endx = (int) (scrnSize.width - 1);
		int startx = (int) (scrnSize.width * 0.2);
		int endy = y;
		if (pfName.equals("android")) {
			((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
		} else {
			((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);

		}

	}

	public void swipeFullFromRToLFromAPoint(String pfName, int y) {
		org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
		int startx = (int) (scrnSize.width * 0.8);
		int endx = (int) (1);
		int endy = y;
		if (pfName.equalsIgnoreCase("android")) {
			((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
		} else {
			((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
		}
	}

	public void swipeToOpenHamburgarMenu(String pfName) {
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int endx = (int) (scrnSize.width - 1);
			int startx = (int) (1);
			int endy = (int) (scrnSize.height / 2);
			if (pfName.equals("android")) {
				((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
			} else {
				((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
			}

		} catch (InterruptedException e) {

		}
	}

	public void tapOnRightSideOfScreen(String pfName) {
		try {

			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			Thread.sleep(2000);

			if (pfName.equalsIgnoreCase("Android")) {
				((AndroidDriver<WebElement>) driver).tap(1, (int) (scrnSize.width * 0.9), scrnSize.height / 2, 1000);

			} else {
				((IOSDriver<WebElement>) driver).tap(1, (int) (scrnSize.width * 0.9), 300, 1000);
			}

		} catch (InterruptedException e) {

		}
	}

	public void swipeFromBottomToTop(String pfName) {
		try {
			// Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int starty = (int) (scrnSize.height * 0.4);
			int endy = (int) (scrnSize.height * 0.7);
			if (pfName.equals("android")) {
				((AndroidDriver<WebElement>) driver).swipe(startx, endy, startx, starty, 1000);
			} else {
				((IOSDriver<WebElement>) driver).swipe(startx, endy, startx, starty, 1000);
			}
			Thread.sleep(1000);
		} catch (Exception e) {

		}
	}

	

	public void swipeFromCoordinates(String pfName, int x1, int y1, int x2, int y2) {
		System.out.println("Swiping......");
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = x1;
			int endx = x2;
			int starty = y1;
			int endy = y2;

			if (pfName.equalsIgnoreCase("android")) {

				((AndroidDriver<WebElement>) driver).swipe(startx, starty, endx, endy, 3000);
			} else {
				((IOSDriver<WebElement>) driver).swipe(startx, (int) (scrnSize.height * 0.9), startx, starty, 3000);

				((IOSDriver<WebElement>) driver).swipe(startx, (int) (scrnSize.height * 0.3), startx,
						(int) (scrnSize.height * 0.1), 3000);
			}

		} catch (InterruptedException e) {

		}
	}

	
	
	public void swipeToElementFromRight(String pfName, String property) {
		while (true) {
			if (verifyElement(property)) {
				break;
			}
			else
			{
				swipeFullFromRightToLeft(pfName);

			}
		}
		Point p = driver.findElement(getLocator(property)).getLocation();
		System.out.println("Element Found");
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width * 0.8);
			int endx = (int) (1);
			int endy = (int) (scrnSize.height / 2);
			if (pfName.equals("android")) {

				((AndroidDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
				
			
			} else {
				if (p.y > 600) {
					((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
				} else {

					((IOSDriver<WebElement>) driver).swipe(startx, endy, endx, endy, 3000);
				}
			}
		} catch (InterruptedException e) {

		}

	}
	

	
	
	public void customPageWait(long timeSec) {
		try {
			Thread.sleep(timeSec);
		} catch (InterruptedException e) {

		}
	}

	public void customWaitUntil(String property) {

		FluentWait<MobileDriver> fluentWait = new FluentWait<>(driver).withTimeout(45, TimeUnit.SECONDS)
				.pollingEvery(200, TimeUnit.MILLISECONDS);
		fluentWait.until(ExpectedConditions.presenceOfElementLocated(getLocator(property)));
		String url = driver.getCurrentUrl();
		String page = url.substring(url.lastIndexOf("/") + 1);

	}

	public void waitUntilVisibilityOf(String property) {
		MobileElement element = (MobileElement) driver.findElement(getLocator(property));

		wait = new WebDriverWait(driver, 45 /* timeout in seconds */);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void waitUntilVisibilityOf(String property, int index) {
		MobileElement element = (MobileElement) driver.findElements(getLocator(property)).get(index);

		wait = new WebDriverWait(driver, 45);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void waitUntilElementExist(String property, String text) {
		MobileElement element = (MobileElement) driver.findElement(getLocator(property));

		wait = new WebDriverWait(driver, 45);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));

	}

	public void waitUntilElementExist(String property, int index, String text) {
		MobileElement element = (MobileElement) driver.findElements(getLocator(property)).get(index);

		wait = new WebDriverWait(driver, 45);

		wait.until(ExpectedConditions.textToBePresentInElement(element, text));

	}

	public void waitUntilStaleElementExist(String property) {
		MobileElement element = (MobileElement) driver.findElement(getLocator(property));

		wait = new WebDriverWait(driver, 45);

		wait.until(ExpectedConditions.stalenessOf(element));

	}
	
	public List<MobileElement> getListOfElements(String locator) {
		List<MobileElement> elementList = new ArrayList<MobileElement>();
		elementList = driver.findElements(getLocator(locator));

		return elementList;
	}

	public void selectItemFromAnItemListUsingText(String locator, String text) {
		List<WebElement> elementsList = driver.findElements(getLocator(locator));
		if (elementsList.size() == 0) {
			try {
				Assert.fail("No such elements found . List is not populated.");
			} catch (Exception e) {

			}
		} else {
			Boolean noFound = true;
			for (WebElement e : elementsList) {
				MobileElement element = (MobileElement) e;
				String elementText = element.getText();
				if (elementText.equals(text)) {
					element.click();
					System.out.println("Elemet with text : " + elementText + " is clicked");
					noFound = false;
					break;
				}
			}
			if (noFound) {
				try {
					Assert.fail("Desired Element with text : " + text + " is not found the provided list");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void verifyItemsPresentFromAnItemList(String locator) {
		System.out.println("Locator provided: " + locator);
		List<WebElement> elementsList = driver.findElements(getLocator(locator));
		System.out.println("List of Elements size: " + elementsList.size());
		if (elementsList.size() == 0) {
			try {
				Assert.fail("No such elements found . List is not populated.");
			} catch (Exception e) {

			}
		} else {

			for (WebElement e : elementsList) {
				MobileElement element = (MobileElement) e;
				String elementText = element.getText();
				System.out.println("Element text is : " + elementText);

			}

		}
	}

	public void writeStatusToReportExcel(String pfName, String testCaseName, String description, String status) {
		System.out.println("Started Writing Test Status to Excel File......");
		try {
			FileInputStream file = new FileInputStream(new File("./reports/Test_Results_Status.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = null;
			if (pfName.equalsIgnoreCase("android")) {
				sheet = wb.getSheet("ANDROID");
			} else if (pfName.equalsIgnoreCase("iOS")){
				sheet = wb.getSheet("IOS");
			}

			Iterator<Row> iterator = sheet.iterator();
			int count = 0;
			Boolean check = false;
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				count++;
				if (nextRow.getCell(0).equals(null)) {
					break;
				} else if (nextRow.getCell(0).getStringCellValue().equals(testCaseName)) {
					check = true;
					break;
				}

			}
			if (check) {
				sheet.getRow(count - 1).getCell(2).setCellValue(status);
			} else {
				sheet.createRow(count).createCell(0).setCellValue(testCaseName);
				sheet.getRow(count).createCell(1).setCellValue(description);
				sheet.getRow(count).createCell(2).setCellValue(status);
			}

			file.close();
			FileOutputStream fileOut = new FileOutputStream(new File("./reports/Test_Results_Status.xlsx"));
			wb.write(fileOut);
			wb.close();

			fileOut.flush();

			fileOut.close();
			System.out.println("DONE !!! Writing Test Status to Excel File......");

		} catch (Exception e) {
			System.out.println("Failed !!! Writing Test Status to Excel File...... Error Message: " + e.getMessage());

		}

	}

	public void sendAppToBackgroundForSomeTime(int seconds) {
		System.out.println("Running the app in background for " + seconds + " seconds");
		driver.runAppInBackground(seconds);
		System.out.println("Background....");
		driver.launchApp();
	}

	
	public void lockDevice(String pfName) throws InterruptedException {
		if (pfName.equalsIgnoreCase("Android")) {

			((AndroidDriver<WebElement>) driver).lockDevice();
			System.out.println("Device is Locked");
			Thread.sleep(5000);
			((AndroidDriver<WebElement>) driver).unlockDevice();
			System.out.println("Device is Unlocked");
		} else {
			System.out.println("Device is Locked");
			((IOSDriver<WebElement>) driver).lockDevice(5);
			System.out.println("Device is Unlocked");

		}

	}

	public void unlockDevice() throws IOException {
		((AndroidDriver<WebElement>) driver).unlockDevice();

		System.out.println("Device is unlocked");

	}

	public void minimizeApp() throws InterruptedException {

		((AndroidDriver) driver).runAppInBackground(10);

		System.out.println("Minimize App");
	}

	public void maximizeApp() throws IOException, InterruptedException {

		System.out.println("App Opened again from background");

	}
	/**
	 * switches off Wifi
	 */
	public void swithWifiOff() {

		((AndroidDriver) driver).setConnection(Connection.NONE);

		System.out.println("Wifi OFF");
	}
	/**
	 * switches on Wifi
	 */
	public void swithWifiON() {

		((AndroidDriver<WebElement>) driver).setConnection(Connection.WIFI);

		System.out.println("Wifi ON");
	}

	
	/**
	 * uninstall app iOS
	 */
	public void uninstallAppiOS(String bundleId) {
		driver.removeApp(bundleId);
	}
	/**
	 * install app android
	 */
	public void installAppAndroid() {
		invokeApp();
	}
	/**
	 * change the orientation of the screen to portrait
	 */
	public void orientationPortrait() {

		driver.rotate(ScreenOrientation.PORTRAIT);
		System.out.println("Setting Screen Orientation to Portrait");
	}
	/**
	 * change the orientation of the screen to landscape
	 */
	public void orientationLandscape() {
		driver.rotate(ScreenOrientation.LANDSCAPE);

		System.out.println("Setting Screen Orientation to Landscape");
	}
	/**
	 * get the battery percentage of mobile
	 */
	public String getBatteryPercentage() throws Exception {
		List<List<Object>> data = ((AndroidDriver<WebElement>) driver).getPerformanceData("", "batteryinfo", 1);
		String battery = String.valueOf(data.get(1));
		return battery;
	}
	/**
	 * Tap on the center of the screen
	 */
	public void tapCenterOfScreen(String pfName) {
		try {

			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			Thread.sleep(2000);
			if (pfName.equalsIgnoreCase("Android")) {
				((AndroidDriver<WebElement>) driver).tap(1, scrnSize.width / 2, scrnSize.height / 2, 1000);

			} else {
				((IOSDriver<WebElement>) driver).tap(1, scrnSize.width / 2, scrnSize.height / 2, 1000);
			}

		} catch (InterruptedException e) {

		}
	}
	/**
	 * Tap on coordinates on the screen by x and y coordinates
	 */
	public void tapOnCoordinates(String pfName, int x, int y) {
		try {

			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			Thread.sleep(2000);
			if (pfName.equalsIgnoreCase("Android")) {
				((AndroidDriver<WebElement>) driver).tap(1, x, y, 1000);

			} else {
				((IOSDriver<WebElement>) driver).tap(1, x, y, 1000);
			}

		} catch (InterruptedException e) {

		}
	}
	/**
	 * Click oniOS search button in keyboard
	 */
	public void search(String pfName) {
		if (pfName.equalsIgnoreCase("Android")) {
			((AndroidDriver) driver).pressKeyCode(66);

		} else {
			click("xpath===//XCUIElementTypeButton[@name='Search' or @name='Return']");
		}
	}
	/**
	 * return the mobile element
	 */
	public MobileElement getMobileElement(String pfName, String property) {
		MobileElement element = null;
		By byProperty = getLocator(property);
		if (pfName.equalsIgnoreCase("Android")) {
			element = (MobileElement) driver.findElement(byProperty);
		}
		return element;
	}
	/**
	 * splits the string text
	 */
	public List<String> getSplitTrimString(String string) {
		ArrayList<String> list = new ArrayList<String>();
		String temp = "";
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == ' ') {
				if (temp.length() != 0 && (!list.contains(temp))) {
					list.add(temp);
				}
				temp = "";
			} else {
				temp = temp + string.charAt(i);
			}
		}
		if (!(temp.equals("")) && (!list.contains(temp))) {
			list.add(temp);
		}
		return list;
	}
	/**
	 * Swipe the screen to make it active
	 */
	public void swipeToActive() {
		try {
			Thread.sleep(5000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int starty = (int) (scrnSize.height * 0.3);
			int endy = (int) (scrnSize.height * 0.4);

			((AndroidDriver<WebElement>) driver).swipe(startx, endy, startx, starty, 100);
			((AndroidDriver<WebElement>) driver).swipe(startx, starty, startx, endy, 100);

		} catch (InterruptedException e) {

		}
	}
	/**
	 * get the element location in Point
	 */

	public Point getLocation(String property) {
		Point point = new Point(0, 0);
		try {
			point = driver.findElement(getLocator(property)).getLocation();
		} catch (Exception e) {

		}

		return point;
	}
	/**
	 * get the element size in Dimension
	 */
	public Dimension getElementSize(String property) {
		Dimension dimension = null;

		MobileElement element = (MobileElement) driver.findElement(getLocator(property));
		dimension = element.getSize();

		return dimension;
	}
	/**
	 * Select GPS location
	 */
	public void setLocationGPS(){
		try{
		((IOSDriver<WebElement>)driver).setLocation(new Location(27, 153, 0));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}

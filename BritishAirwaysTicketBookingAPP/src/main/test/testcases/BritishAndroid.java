package testcases;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.BkFlightPage;
import pages.FlightSeltPage;
import pages.FlightSumryPage;
import pages.HomePage;
import pages.Paymentpage;
import pages.PrsnPayingPage;
import pages.PsngrDtlPage;
import pages.PsngrVerifyPage;
import wrappers.ProximusWrapper;

/**
 * @author sundar.siva
 *
 */
public class BritishAndroid extends ProximusWrapper {

	/**
	 * Gets the input from the testng.xml file which has the device capabilities
	 * @param automation - automationName capability in appium
	 * @param platformName - platformName capability in appium
	 * @param platformVersion - platformVersion capability in appium
	 * @param deviceName - deviceName capability in appium
	 * @param udid - udid capability in appium
	 * @param application - application capability in appium
	 * @param port - port capability in appium
	 * @param systemPort - systemPort capability in appium
	 */
	@BeforeClass
	@Parameters({ "Automation", "PlatformName", "PlatformVersion", "DeviceName", "udid", "Application", "Port", "systemPort" })
	public void setData(String automation, String platformNam, String platformVer, String deviceNam, String udid,
			String application, String port, String systemPort) {
		testCaseName = "Test";
		tcName = testCaseName;
		testDescription = "Started";
		category = "Functional";
		dataSheetName = "DataPool.xlsx";
		applicationType = application;
		authors = "Sundar";
		capData.put("Automation", automation);
		capData.put("Port", port);
		capData.put("PlatformName", platformNam);
		capData.put("PlatformVersion", platformVer);
		capData.put("DeviceName", deviceNam);
		capData.put("udid", udid);
		capData.put("TestCaseName", testCaseName);
		capData.put("systemPort", systemPort);

	}

	

	/**
	 * TestNG test annotated method which has all the steps to execute. First step of the test case
	 */
	@Test(priority = 1, enabled = true)
	public void BritishAirways_Passenger_Details_Scenario() {

		try {
			testCaseName = "BritishAirways_Passenger_Details_Scenario";
			tcName = testCaseName;
			testDescription = "Launch British Airways App and book a ticket ";
			startTestReport();
			
			HomePage launch = new HomePage(driver, test, capData);
			launch.LaunchApp();
			
			BkFlightPage bookflight = new BkFlightPage(driver, test, capData);
			bookflight.BookFlight();
			
			FlightSeltPage flightselt=new FlightSeltPage(driver, test, capData);
			flightselt.FlightSelect();
			
			FlightSumryPage flightsummary=new FlightSumryPage(driver, test, capData);
			flightsummary.FlightSummary();
			
			PsngrDtlPage psngrdtl=new PsngrDtlPage(driver, test, capData);
			psngrdtl.PsngrDtl();
			
			PsngrVerifyPage psngrverify=new PsngrVerifyPage(driver, test, capData);
			psngrverify.PsngrVerify();
			
			PrsnPayingPage prsnpaying=new PrsnPayingPage(driver, test, capData);
			prsnpaying.PrsnPaying();
			
			Paymentpage payment=new Paymentpage(driver, test, capData);
			payment.Payment();
		
				
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	
	
}

package wrappers;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestListener;
import org.testng.ITestResult ;	
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

/**
 * @author sundar.siva
 *
 */
public class ProximusWrapper extends GenericWrappers implements ITestListener {

	public String dataSheetName;
	public String applicationType;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		startResult();
		
	}
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		invokeApp();

	}
	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		if(capData.get("Automation").equalsIgnoreCase("cloud")) {
			((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
		}
		if(!result.isSuccess())
		{
		    String testResult = String.valueOf(result.getThrowable());
			verifyStep(testResult, "FAIL");
		}
		endTestcase();
	}
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
			endResult();
			closeApp();

	}

}

package testPackage;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basePackage.MultiBrowser;
import basePackage.ProjectBaseTwo;
import facebookPagesPackage.locators.xpathMain;


/*
 * Important things to be noted 
 * 
 * Remember to giver driver path, It is not added because size issues.
 */

/*
 * Things to be Done:
 * 
 * Add and complete Highlight wherever possible.
 * Add Multiple Tab handling.
 * Add Methods for linktext, partial link text.
 * Add Comments to all Existing Testcases.
 */


public class SampleTests extends ProjectBaseTwo {

	public String b2, b1;

	@DataProvider(name = "browserDecider")
	public String[][] browser() {
		b1 = MultiBrowser.First_Execution_BrowserName;
		b2 = MultiBrowser.Second_Execution_BrowserName;
		return new String[][] { { b2 }, };
	}
	
	
	/*
	 * Below Testcase Execution order
	 * TestName is to Identity Uniquely in the Report.
	 * 1. Warning and Properties are Logged Using warnings() Method. If User Need this can add to any Testcases.
	 * 2. Handle Browser used to Pass Name of the Browser to Execute.
	 * 3. openURL - User can pass any URL or can give it in the Project Settings.
	 * 4. quitbrowser Helps us to Quit the Browser.
	 * 
	 * Purpose : To Ensure the Given URL is Launching Or Not and Returning 200 Status Code to verify URL Responds Correctly.
	 * 
	 * Can be Improved With : 
	 * add screenshot to capture website is launched. Whenever new navigation to next page capture and add to test step.
	 */

	@Test(priority = 0, dataProvider = "browserDecider")
	public void ensureURL(String browser) throws Exception {
		warnings();
		testName("Ensure URL Working " + "[" + browser + "]");
		handleBrowser(browser); 
		openURL();
		quitBrowser(browser);
	}

	@Test(priority = 1, dataProvider = "browserDecider")
	public void detailsOfWebpage(String browser) throws Exception {
		testName("Details of WebPage " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		getTitle();
		countAllElements();
		seperateElements();
		logCountAllElements();
		// add if for displaying some log statements if it is present in the webpage
		// then display log statement in report.
		logDetailsOthers();
		logDetailsHeadingTag();
		// count and display more type of tag Eg: script, iframe..
		quitBrowser(browser);
	}

	@Test(priority = 2, dataProvider = "browserDecider")
	public void performOperations(String browser) throws Exception {
		testName("List of Anchor Tags " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		countAllElements();
		seperateElements();
		performOperationOnAnchor();
		quitBrowser(browser);
	}

	@Test(priority = 3, dataProvider = "browserDecider")
	public void refreshCheck(String browser) throws Exception {
		testName("Verify No.of Elements when Launched == After Refresh Page" + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		int beforeRefresh = countAllElements();
		refreshPage();
		Thread.sleep(2000);
		int afterRefresh = countAllElements();
		if (beforeRefresh == afterRefresh) {
			logPass("No.of Elements when Launched - [" + beforeRefresh + "] == After Refresh Page - [" + afterRefresh
					+ "]");
		} else {
			logFail("No.of Elements After Refresh Page - [" + afterRefresh
					+ "] is not Equal with No.of Elements when Launched -[" + beforeRefresh + "]");
		}
		quitBrowser(browser);
	}

	@Test(priority = 4, dataProvider = "browserDecider")
	public void inputCheck(String browser) throws Exception {
		testName("Input field Check " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		countAllElements();
		seperateElements();
		seperateInput();
		// seperate for each input if number send number
		// add screenshot to capture input fields are accessible
		// if possible send valid data/ invalid data Eg: more than character limit from
		// excel
		// If no input found end the test case with no input found
		quitBrowser(browser);
	}
	
	@Test(priority = 5, dataProvider = "browserDecider")
	public void sortElementsCheck(String browser) throws Exception {
		testName("Sort Elements Check " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		doBasicThingsforNewPage();
		sortElements("h2");
		highLighterMethod(driver.findElement(By.id("email")));
		clickUsingClassName("_8esh","Button Clicked");
		quitBrowser(browser);
	}

	@Test(priority = 5, dataProvider = "browserDecider")
	public void navigate(String browser) throws Exception {
		try {
			testName("Navigate Check " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			doBasicThingsforNewPage();
			//clickUsingClass("a","_8esh");  // 154 and 155 do same operation
			goToNextPage("create/");
			doBasicThingsforNewPage();
			clickElementUsingXpath(xpathMain.CreateClass,"Element is Clicked"); 
			navigateBack();
		} catch (Exception e) {
			logError(e.getMessage());
		} finally {
			quitBrowser(browser);
		}
	}
	
	
	@Test
	public void ClickElements() throws Exception {
		testName("Clicking Elements Test " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		clickElementUsingAttribute("data-testid", "royal_login_button");		//Click he element of given attribute or id or class.
		GetElementUsingAttribute("data-testid", "royal_login_button");        // Returns the element of given attribute or id or class.
		quitBrowser(browser);
	}
	
	public void alertcheck() {
		// give a alert to website and handle that alert.
		clickElementUsingAttribute("data-testid", "royal_login_button");
	}
	
	public void scrollTillLastElementCheck() {
		// with help of last element scroll that element
	}
	
	public void take_H_tag_text() {
		// after page landing take all text of H1 to h6 and P tag, Store it in text file or log in extent report.
	}
	
	public void loginPageCheck() {
		// create a method for sendkeys two args 1. xpath 2. what we need to send to that field
	}
	

}

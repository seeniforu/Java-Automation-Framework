package testPackage;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
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
 * Add Methods for linktext, partial link text and CSS Selector.
 * Add Assertion
 * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes - Upgrade with detailed status codes.
 * Input field positive and negative check. // if possible send valid data/ invalid data Eg: more than character limit.
 * inputfield(String locator, String type = positive | negative, Total no.of chracters or numbers can be inserted)
 * if frame tag switch to frame and count and log elements.
 * add screen resolution in project settings with capabilities. 
 */


/*
 * Websites used to Test this tool and Default Testcases.
 * 
 * https://www.facebook.com/
 * https://www.amazon.com/
 * https://www.apple.com/
 * https://www.w3schools.com/TAgs/default.asp
 * https://www.makemytrip.com/
 * https://www.verizon.com/
 * https://www.way2automation.com/demo.html
 * https://demoqa.com/
 * https://www.netflix.com/in/
 * https://www.hotstar.com/in
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

	/*
	 * Below Testcase Execution order
	 * TestName is to Identity Uniquely in the Report.
	 * 1. Handle Browser used to Pass Name of the Browser to Execute.
	 * 2. openURL - User can pass any URL or can give it in the Project Settings.
	 * 3. getTitle to get title of the website launched.
	 * 4. BasicForEachPageElementsLogDetails - categorize and count all elements in a page launched.
	 * 5. logDetailsPrimaryTags - logs the 5th step details wherever user needs in the report.
	 * 6. logDetailsHeadingTag - logs heading tags details in the report.
	 * 7. quitbrowser Helps us to Quit the Browser.
	 * 
	 * Purpose : To Catagorize and Log all elements of a webpage in the report.
	 */
	
	@Test(priority = 1, dataProvider = "browserDecider")
	public void detailsOfWebpage(String browser) throws Exception {
		testName("Details of WebPage " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		getTitle();
		BasicForEachPageElementsLogDetails();
		logDetailsPrimaryTags();
		logDetailsHeadingTag();
		quitBrowser(browser);
	}
	
	/*
	 * Below Testcase Execution order
	 * TestName is to Identity Uniquely in the Report.
	 * 1. Handle Browser used to Pass Name of the Browser to Execute.
	 * 2. openURL - User can pass any URL or can give it in the Project Settings.
	 * 3. BasicForEachPageElementsLogDetails - categorize and count all elements in a page launched.
	 * 4. performOperationOnAnchor - It'll take all Anchor Tag in the webpage Launched and verify all Links in Anchor tag returns Successful Status code.
	 * 5. quitbrowser Helps us to Quit the Browser.
	 * 
	 * Purpose : 
	 * To verify all Links in the webpage are working fine and returning Successful Response for Naviagtion. Everything is Logged in Report.
	 * 
	 * Can be Improved With : 
	 * Detailed Status report [https://en.wikipedia.org/wiki/List_of_HTTP_status_codes] 
	 * Upgrade with detailed status codes which is very useful when testing a application under development.
	 */

	@Test(priority = 2, dataProvider = "browserDecider")
	public void performOperations(String browser) throws Exception {
		testName("List of Anchor Tags " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		BasicForEachPageElementsLogDetails();
		performOperationOnAnchor();
		quitBrowser(browser);
	}
	
	/*
	 * Below Testcase Execution order
	 * TestName is to Identity Uniquely in the Report.
	 * 1. Handle Browser used to Pass Name of the Browser to Execute.
	 * 2. openURL - User can pass any URL or can give it in the Project Settings.
	 * 3. beforeRefresh - count number of elements when a webpage is launched.
	 * 4. refreshPage - refreshes the current page.
	 * 5. afterRefresh - count number of elements once refresh is done.
	 * 6. Verifying both count is equal or not and logging Respective Statement in the Report.
	 * 7. quitbrowser Helps us to Quit the Browser.
	 * 
	 * Purpose : To Verify the Stability of the Webpage launched, Useful when a web application is in Development.
	 * 
	 * If it is a Dynamic website and loads everytime according to user data, this testcase may fail.
	 */

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
	
	/*
	 * Below Testcase Execution order
	 * TestName is to Identity Uniquely in the Report.
	 * 1. Handle Browser used to Pass Name of the Browser to Execute.
	 * 2. openURL - User can pass any URL or can give it in the Project Settings.
	 * 3. BasicForEachPageElementsLogDetails - categorize and count all elements in a page launched.
	 * 4. sendInputData - can send inputs as per User need.
	 * 5. sendInputUsingId, sendInputUsingXpath, sendInputUsingClassname are some additional methods to send data differently.
	 * 6. quitbrowser Helps us to Quit the Browser.
	 * 
	 * Purpose : To send inputs as per User need to verify maximum and minimum characters for all input field in a webpage Launched.
	 */

	@Test(priority = 4, dataProvider = "browserDecider")
	public void inputCheck(String browser) throws Exception {
		testName("Input field Check " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		BasicForEachPageElementsLogDetails();
		sendInputData("123@gmail.com"); // Sending Raw input for all input fields in the Loaded page. can send positive and negative values for all input fields.
		//sendInputUsingId("email", "seeniforu");  // Send input using Locators.
		quitBrowser(browser);
	}
	
	/*
	 * Below Testcase Execution order
	 * TestName is to Identity Uniquely in the Report.
	 * 1. Handle Browser used to Pass Name of the Browser to Execute.
	 * 2. openURL - User can pass any URL or can give it in the Project Settings.
	 * 3. BasicForEachPageElementsLogDetails - categorize and count all elements in a page launched.
	 * 4. sortElements - sort the elements according to tagname and logs the count of those elements.
	 * 5. quitbrowser Helps us to Quit the Browser.
	 * 
	 * Purpose : To Sort Elements accroding to argument passed by user.
	 */
	
	@Test(priority = 5, dataProvider = "browserDecider")
	public void sortElementsCheck(String browser) throws Exception {
		testName("Sort Elements Check " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		BasicForEachPageElementsLogDetails();
		sortElements("meta");
		quitBrowser(browser);
	}
	
	// ------------------------------------ Above All cases are Website Independent ------------------------------------------------------------------

	// ------------------------------------ Below are Particular Website based testcase for Reference -------------------------------------------------
	
	@Test(priority = 6, dataProvider = "browserDecider")
	public void navigate(String browser) throws Exception {   // Website specific Testcase for reference.
		try {													// https://www.facebook.com/ - is used.
			testName("Navigate Check " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			BasicForEachPageElementsLogDetails();
			//clickUsingClass("a","_8esh");                // Next two lines does same operation with one using class and another using part of URL.
			goToNextPage("create/");                       // Navigating with help of part of URL.
			BasicForEachPageElementsLogDetails();
			logDetailsPrimaryTags();
			clickElementUsingXpath(xpathMain.CreateClass,"Element is Clicked"); 
			navigateBack();
		} catch (Exception e) {
			logError(e.getMessage());
		} finally {
			quitBrowser(browser);
		}
	}
	
	//@Test
	public void ClickElements() throws Exception {                    // Website specific Testcase for reference.										
		testName("Clicking Elements Test " + "[" + browser + "]");     // https://www.facebook.com/ - is used.
		handleBrowser(browser);
		openURL();
		clickElementUsingAttribute("data-testid", "royal_login_button");		//Clicks the element of given attribute or id or class.
		GetElementUsingAttribute("data-testid", "royal_login_button");        // Returns the element of given attribute or id or class.
		quitBrowser(browser);
	}
	
	public void alertcheck() throws Exception {
		// give a alert to website and handle that alert.
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

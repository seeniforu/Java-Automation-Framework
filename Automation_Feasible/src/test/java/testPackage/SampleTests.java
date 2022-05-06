package testPackage;

import org.testng.annotations.Test;

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
 * create Empty Testcase shows all important methods and how to use it.
 * update readme.
 * Add Methods for linktext, partial link text
 * Add Assertion
 * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes - Upgrade with detailed status codes.
 * Input field positive and negative check. // if possible send valid data/ invalid data Eg: more than character limit.
 * inputfield(String locator, String type = positive | negative, Total no.of chracters or numbers can be inserted)
 * add screen resolution in project settings with capabilities. 
 * try adding page speed insights in headless mode.
 * try adding extension and run tests.
 * add scroll webpage
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
 * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/iframe
 * http://www.maths.surrey.ac.uk/explore/nigelspages/frame2.htm
 * https://www.w3schools.com/html/html_iframe.asp
 */



public class SampleTests extends ProjectBaseTwo {
	
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

	@Test(priority = 0)
	public void ensureURL() {
		try {
			warningsAndProperties();
			testName("Ensure URL Working " + "[" + browser + "]");
			handleBrowser(browser);
			openURL("http://ringtonecutter.com");
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
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
	 * Purpose : To Catagorize and Log all elements of a webpage in the report.	 */
	
	@Test(priority = 1)
	public void detailsOfWebpage() throws Exception {
		try {
			testName("Details of WebPage " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			getTitle();
			DetailedElementsCount();
			logDetailsPrimaryTags();
			logDetailsHeadingTag();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
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

	@Test(priority = 2)
	public void performOperations() throws Exception {
		try {
			testName("List of Anchor Tags " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			BasicForEachPageElementsLogDetails();
			performOperationOnAnchor();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
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

	@Test(priority = 3)
	public void refreshCheck() throws Exception {
		try {
			testName("Verify No.of Elements when Launched == After Refresh Page" + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			int beforeRefresh = countAllElements();
			refreshPage();
			Thread.sleep(2000);
			int afterRefresh = countAllElements();
			if (beforeRefresh == afterRefresh) {
				logPass("No.of Elements when Launched - [" + beforeRefresh + "] == After Refresh Page - ["
						+ afterRefresh + "]");
			} else {
				logFail("No.of Elements After Refresh Page - [" + afterRefresh
						+ "] is not Equal with No.of Elements when Launched -[" + beforeRefresh + "]");
			}
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
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

	@Test(priority = 4)
	public void inputCheck() throws Exception {
		try {
			testName("Input field Check " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			BasicForEachPageElementsLogDetails();
			sendInputData("123@gmail.com"); // Sending Raw input for all input fields in the Loaded page. can send positive and negative values for all input fields.
			//sendInputUsingId("email", "seeniforu");  // Send input using Locators.
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}finally {
			quitBrowser();
		}
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
	
	@Test(priority = 5)
	public void sortElementsCheck() throws Exception {
		try {
			testName("Sort Elements Check " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			BasicForEachPageElementsLogDetails();
			sortElements("meta");
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}finally {
			quitBrowser();
		}
	}
	
	// ------------------------------------ Above All cases are Website Independent ------------------------------------------------------------------

	// ------------------------------------ Below are Particular Website based testcase for Reference -------------------------------------------------
	
	//@Test
	public void navigate() throws Exception {   // Website specific Testcase for reference.
		try {													// https://www.facebook.com/ - is used.
			testName("Navigate Check " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			BasicForEachPageElementsLogDetails();
			//clickUsingClass("a","_8esh");                // Next two lines does same operation with one using class and another using part of URL.
			goToNextPage("create/");                       // Navigating with help of part of URL.
			BasicForEachPageElementsLogDetails();
			logDetailsPrimaryTags();
			clickUsingXpath(xpathMain.CreateClass,"Element is Clicked"); 
			navigateBack();
		} catch (Exception e) {
			logError(e.getMessage());
		} finally {
			quitBrowser();
		}
	}
	
	//@Test
	public void ClickElements() throws Exception {                    // Website specific Testcase for reference.										
		try {
			testName("Clicking Elements Test " + "[" + browser + "]");     // https://www.facebook.com/ - is used.
			handleBrowser(browser);
			openURL();
			clickElementUsingAttribute("data-testid", "royal_login_button");		//Clicks the element of given attribute or id or class.
			GetElementUsingAttribute("data-testid", "royal_login_button");        // Returns the element of given attribute or id or class.
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}finally {
			quitBrowser();
		}
	}
	
	//@Test
	public void accessingElements() throws Exception {                                 // Website specific Testcase for reference.
		try {
			testName("Accessing Elements Test " + "[" + browser + "]");
			handleBrowser(browser);                         
			openURL();                                                             // https://www.facebook.com/ - is used.
			clickOrVerifyWithCssSelector("Verify", "input#email", "Element is Visible", "Element is not Visible");
			getCssValueUsingId("email", "font-size", "CSS value is Logged");
			getLocationUsingId("email", "Location value is logged");
			getSizeUsingId("email", "Size of element is Logged");
			verifyUsingId("email", "Element is Displayed", "Element is Not Displayed");
			System.out.println(getPageSource());
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	//@Test
	public void frameSwitchingCheck() {
		try {
			testName("Ensure Frame Switching " + "[" + browser + "]");
			handleBrowser(browser);
			openURL("https://www.w3schools.com/html/html_iframe.asp");
			addAssertionForStringVerification(getTitle(), "HTML Iframe", "Title Verification Done", "Title is Not Matching");
			//DetailedElementsCount();    // This method is useful when user needs to switch to a Frame in website to count elements inside it.
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			reportFlush();
			quitBrowser();
			warningsAndProperties();
		}
	}
	
	public void alertCheck() {
		// give a alert to website and handle that alert.
	}
	
	public void scrollTillLastElementCheck() {
		// with help of last element scroll that element
	}
	
	public void take_H_tag_text() {
		// after page landing take all text of H1 to h6 and P tag, Store it in text file or log in extent report.
	}
	

}

package testPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import basePackage.ProjectBaseTwo;
import io.qameta.allure.Description;

/*
 * Things to be Done:
 * 
 * need to update methods for opera browser.
 * Add Assertion 
 * all browser proxy needs to be fixed. https://ipleak.net and www.lagado.com/proxy-test
 * add waits for attribute methods etc.., (By passing as parameter)
 * add methods to select from xpath index.
 * properties file EanbleAllNewpageScreenshot - whnever directs to new page capture screenshot option. for logging
 * phantomjs error, fix issue in proxy firefox browser..
 * Input field positive and negative check. // if possible send valid data/ invalid data Eg: more than character limit.
 * inputfield(String locator, String type = positive | negative, Total no.of chracters or numbers can be inserted)
 * try adding page speed insights in headless mode.
 * try adding extension and run tests.
 * add scroll webpage
 * add javascript executor.
 */

/*
 * Websites used to Test this Framework with Default Testcases.
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
 * https://startup.lwmeta.com/2020/07/17/sap-web-ide-personal-edition-setup-and-create-sapui5-application-for-beginner/ - 503
 */


public class SampleTests extends ProjectBaseTwo {
	
	private WebElement Element;
	private String XpathLocater;
	private String AlternateXpathLocater;
	private static final By locater = By.xpath("//*[@id='email']");
	
	protected void UsageOfExisitingMethods() {
		
		//-------------------------------   Screenshot methods -------------------------------------------//
		screenshotWithCustomName("Name of the Screenshot");
		
		 //This Method comes inbuilt with Verification Methods. No need to call seperately.
		screenshotVerification();    
		
		//ScreenshotError() is a private method used to only capture screenshot of error page wherever Error log is called in catch blocks.
		
		//this method captures Screenshot of particular element passed as parameter.
		capturePartialScreenshotUsingElement(Element);
		
		//this method captures Screenshot of particular element where user can pass xpath as parameter.
		capturePartialScreenshotUsingXpath(XpathLocater);
		
		//this method captures Screenshot of particular element where user can pass By Locater where it can be modified as id, class etc.
		takeScreenshotOfElement(locater);
		
		// --------------------------------- Get Locater Methods -----------------------------------------------//
		
		//[By Default these methods have Waits, Try/catches, Warnings, Highlightelement etc..]
		
		// These methods accepts string xpath as parameter same applicable for id, classname etc.,
		getElementUsingXpath(XpathLocater);                              // will return element.
		getElementsInListUsingXpath(XpathLocater);                         // will return a list of elements.
		
		// These methods accepts string xpath as parameter same applicable for id, classname etc.,
		getTextUsingXpath(XpathLocater);                                  // will return text of the element.
		getTextUsingXpath(XpathLocater, "Pass Log Statement");           // will return text of the element with custom log statement.
		 // will return text of the element, if fails it'll try with alternate xpath with custom log statement.
		getTextUsingXpath(XpathLocater, AlternateXpathLocater, "Pass Log Statement");
		
		// These methods accepts string xpath as parameter same applicable for id, classname etc.,
		getCssValueUsingXpath(XpathLocater, "property of the element", "Pass Log Statement");   // will return css value with custom log statement.
		getSizeUsingXpath(XpathLocater, "Pass Log Statement");                      // will return Size of element with custom log statement.
		getLocationUsingXpath(XpathLocater, "Pass Log Statement");                // will return Location of element with custom logstatement.
		
		// --------------------------------- Locater Action Methods ----------------------------------------//
		
		// these are click methods accepts string using xpath, same applicable for id,classname etc.,
		clickUsingXpath(XpathLocater);                                    // Just clicks the element.
		clickUsingXpath(XpathLocater, "Pass Log Statement");              // clicks the element with custom pass statement
		// clicks the element, if failes to click using primary it'll try with alternate xpath locater with custom pass statement
		clickUsingXpath(XpathLocater, AlternateXpathLocater, "Pass Log Statement");  
		
		//these are send keys methods accepts string xpath and data as parameter same applicable for id,classname etc., [By default it has clear method]
		sendKeysUsingXpath(XpathLocater, "enter Data to send");            // sends data according to user need
		sendKeysUsingXpath(XpathLocater, "enter Data to send", "Pass Log Statement");       // sends data according to user need with custom log statement.
		// sends data, if unable to locate element with primary it'll try with alternate xpath with custom log statement.
		sendKeysUsingXpath(XpathLocater, AlternateXpathLocater, "enter Data to send", "Pass Log Statement");
		
		// --------------------------------- Verification methods ------------------------------------------//
		
		// these are verification methods to check element is dispalyed or not, same applicable for id,classname etc., 
		// [By defeult it has assert and screenshot method for both pass and fail cases]
		verifyUsingXpath(XpathLocater);
		verifyUsingXpath(XpathLocater, "Pass Log Statement", "Log Statement If Failed");     
		verifyUsingXpath(XpathLocater, AlternateXpathLocater, "Pass Log Statement", "Log Statement If Failed");
		
		// --------------------------------- By Locater Methods --------------------------------------------//
		
		// --------------------------------- Special Methods -----------------------------------------------//
		
		//getusing attribute
		
		// --------------------------------- New Try Methods -----------------------------------------------//
		
		//containsand others
		
		// --------------------------------- Some Common Methods -------------------------------------------//
		
		// Helps you to highlight the element you are accessing. user can use custom color by changing in properties file.
		highLighterMethod(Element);
		
		//this method uses javascript executor waits until page is completely loaded.
		waitForPageToBeReady();
		
		
		
		
	}

	// ------------------------------------ Below Testcases are Website Independent ------------------------------------------------------------------
	
	@Description("Test Description: Ensure URL Working")
	@Test(priority = 1)
	private void ensureURLWorkingSampleTestcase() {
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
		try {
			testNameWithBrowserName("Ensure URL Working ", "Firefox");
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	@Test(priority = 2)
	private void ColletingDetailsOfWebpageSampleTest() throws Exception {
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
		try {
			testNameWithBrowserName("Details of WebPage ", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			getTitle();
			basicForEachPageElements();
			logDetailsPrimaryTags();
			logDetailsHeadingTag();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	public void DetailsOfaPage() {
		getTitle();
		basicForEachPageElements();
		logDetailsPrimaryTags();
		logDetailsHeadingTag();
	}
	
	public void DetailsOfaPageIncludingFrame() {
		getTitle();
		DetailedElementsCount();
	}

	@Test(priority = 2)
	private void performOperationsWithAnchorTagSampleTest() throws Exception {
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
		try {
			testNameWithBrowserName("List of Anchor Tag Link", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			basicForEachPageElements();
			performOperationOnAnchor("Normal");
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	public void pageAnchorsUrlResponseCheck(String NormalOrDetailed) {
		basicForEachPageElements();
		performOperationOnAnchor(NormalOrDetailed);  // when using in another method pass parameter as Normal for basic response code check.
		//Detailed for all response code check.
	}

	@Test(priority = 3)
	private void refreshCheckSampleTestcase() throws Exception {
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
		try {
			testNameWithBrowserName("Verify No.of Elements when Launched == After Refresh Page", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			pageRefreshCheck();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	public void pageRefreshCheck() {
		try {
			int beforeRefresh = countAllElements();
			refreshPage();
			waitForPageToBeReady();
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
		}
	}

	//@Test(priority = 4)
	private void inputFieldsCheckSampleTestcase() throws Exception {
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
		try {
			testNameWithBrowserName("Input field Check ", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			basicForEachPageElements();
			//sendInputData("123@gmail.com"); // Sending Raw input for all input fields in the Loaded page. can send positive and negative values for all input fields.
			//sendInputUsingId("email", "seeniforu");  // Send input using Locators.
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}finally {
			quitBrowser();
		}
	}
	
	protected void dummy() { 
		try { // Don't use this method.
			inputFieldsCheckSampleTestcase();
			ClickElementsUsingAttributeSampleTest();
			accessingElementsSampleTest();
			frameSwitchingCheckSampleTest();
			byLocatersTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 5)
	private void sortElementsCheckSampleTestcase() throws Exception {
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
		try {
			testNameWithBrowserName("Sort Elements Check ", "Edge");
			handleBrowser("Edge");
			openURL();
			basicForEachPageElements();
			sortElements("meta");
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}finally {
			quitBrowser();
		}
	}
	
	public void sortElementUsingTagName(String TagName) {
		basicForEachPageElements();
		sortElements(TagName);
	}

	// ------------------------------------ Below are Particular Website based testcase for Reference -------------------------------------------------
	
	@Test
	private void navigateUsingDifferentMethodSampleTest() throws Exception {   
		/*
		 * // Website specific Testcase for reference.
		 * // https://www.facebook.com/ - is used.
		 */
		try {													
			testNameWithBrowserName("Navigate Check ", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			basicForEachPageElements();
			//clickUsingClass("a","_8esh");                // Next two lines does same operation with one using class and another using part of URL.
			goToNextPage("create/");                       // Navigating with help of part of URL.
			basicForEachPageElements();
			logDetailsPrimaryTags();
			//clickUsingXpath(xpathMain.CreateClass,"Element is Clicked"); 
			navigateBack();
		} catch (Exception e) {
			logError(e.getMessage());
		} finally {
			quitBrowser();
		}
	}
	
	//@Test
	private void ClickElementsUsingAttributeSampleTest() throws Exception {    
		/*
		 * Website specific Testcase for reference.	
		 * https://www.facebook.com/ - is used.									
		 */
		try {
			testNameWithBrowserName("Clicking Elements Test Using Attribute", useBrowserSpecifiedInProperties);     
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			GetElementUsingAttribute("data-testid", "royal_login_button");        // Returns the element of given attribute or id or class.
			clickElementUsingAttribute("data-testid", "royal_login_button");	//Clicks the element of given attribute or id or class.
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}finally {
			quitBrowser();
		}
	}
	
	//@Test
	private void accessingElementsSampleTest() throws Exception {   
		/*
		 *  Website specific Testcase for reference.
		 *  https://www.facebook.com/ - is used.
		 *  Does not work in Mobile view as Id changed.
		 */
		try {
			testNameWithBrowserName("Accessing Elements Test ", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);                         
			openURL();                                                             
			clickOrVerifyWithCssSelector("Verify", "input#email", "Element is Visible", "Element is not Visible");
			getCssValueUsingId("email", "font-size", "CSS value is Logged");
			getLocationUsingId("email", "Location value is logged");
			getSizeUsingId("email", "Size of element is Logged");
			verifyUsingId("email", "Element is Displayed", "Element is Not Displayed");
			screenshotWithCustomName("Dummy SS");
			System.out.println(getPageSource());
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	//@Test
	private void frameSwitchingCheckSampleTest() {  
		/*
		 *    Website specific Testcase for reference.
		 *    Website which contains frames can use DetailedElementsCount();
		 *    website used is - https://www.w3schools.com/html/html_iframe.asp
		 */
		try {																	
			testNameWithBrowserName("Ensure Frame Switching ", useBrowserSpecifiedInProperties);
			handleBrowser(useBrowserSpecifiedInProperties);
			openURL();
			//addAssertionForStringVerification(getTitle(), "HTML Iframe", "Title Verification Done", "Title is Not Matching");
			DetailedElementsCount();    // This method is useful when user needs to switch to a Frame in website to count elements inside it.
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	@Test
	private void mobileExecutionTest() {   
		/*
		 * Website specific Testcase for reference.
		 * Chrome is Used. Only works in chrome. Properties set as "Yes" in MobileExecution and "iPhone SE" in Mobile model.
		 * https://www.facebook.com/ - is used.
		 */
		try {
			testNameWithAssignAuthor("Mobile Execution Test", prop.getProperty("MobileModel"), "Srinivasan");   
			handleBrowserMobileView(useBrowserSpecifiedInProperties);
			openURL();	
			getElementUsingXpath("//*[@id='m_login_email']");
			verifyUsingXpath("//*[@id='m_login_email']", "Input Field is Verified", "Input Field is Not Verified"); // for Passing Test
			//verifyUsingXpath("//input[@name='lsd']", "Input Field is Verified", "Input Field is Not Verified");  // for Failing Test
			sendKeysUsingXpath("//*[@id='m_login_email']","hello");
			screenshotWithCustomName("Mobile view Screenshot");
			//capturePartialScreenshotUsingElement(findElement("//*[@id='signup-button']")); // User can use getElementUsing id,classname etc.. to capture particular element.
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}
	
	
	
	//@Test
	private void byLocatersTest() {   // facebook is used to test By Locaters with waits.
		WebElement element = null;
		testNameWithBrowserName("Find Element Try ", useBrowserSpecifiedInProperties);
		handleBrowser(useBrowserSpecifiedInProperties);
		openURL();
		try {
			element = getElement(locater);
			highLighterMethod(element);
			element = findElement(locater,3);
			highLighterMethod(element);
			sendKeys(element, "seeniforu");
			verifyElement(locater,8, "Element verfied", "not Verified");
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		finally {
			quitBrowser();
		}
	}
	

	public void alertCheck() {
		// give a alert to website and handle that alert.
		clickUsingContainsWithAttribute("name", "btn");
	}
	
	public void take_H_tag_text() {
		// after page landing take all text of H1 to h6 and P tag, Store it in text file or log in extent report.

	}
	
	public void pageSpeedInsightsTest() {
		// pageSpeedInsights or pagelocity in headless mode to generate reports.
	}
	
	public void clickUsingId(String Id, String altAttributeName, String altAttributeValue, String LogStatement) {
		// Create Methods like this for click, send, get, verify - for classname, xpath etc.,
	}	
	
	@Test
	public void findelement() {
		            //Working, 1st catch and 2nd catch are slow
		WebElement element = null;
		testNameWithBrowserName("Find Element Try ", useBrowserSpecifiedInProperties);
		handleBrowser(useBrowserSpecifiedInProperties);
		openURL();
		try {
			element = getElement(locater);
			highLighterMethod(element);
		} catch (Exception e) {
			try {
			element = getElement(locater,4);
			highLighterMethod(element);
			}catch(Exception r) {
				element = findElement(locater);
				highLighterMethod(element);
			}
		}
		finally {
			quitBrowser();
		}
	}
}

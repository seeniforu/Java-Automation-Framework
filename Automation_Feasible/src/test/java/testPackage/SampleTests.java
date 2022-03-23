package testPackage;

import java.io.IOException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basePackage.MultiBrowser;
import basePackage.ProjectBaseTwo;

/*
 * Important things to be noted 
 * 
 * Remember to giver driver path, It is not added because size issues.
 */


public class SampleTests extends ProjectBaseTwo {

	public String b2, b1;

	@BeforeMethod
	public void beforeMethod() {
		try {
			setUp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@DataProvider(name = "browserDecider")
	public String[][] browser() {
		b1 = MultiBrowser.First_Execution_BrowserName;
		b2 = MultiBrowser.Second_Execution_BrowserName;
		return new String[][] { { b2 }, };
	}

	@AfterMethod
	public void afterMethod() {
		// closeBrowser();
		reportFlush();
	}

	@Test(priority = 0, dataProvider = "browserDecider")
	public void ensureURL(String browser) throws Exception {
		warnings();
		testName("Ensure URL Working " + "[" + browser + "]");
		handleBrowser(browser); // firefox browser method needs to be changed.
		openURL(); // change to - getCurrentUrl after launching
		quitBrowser(browser);
		// if possible get status code 200 to verify and add screenshot to capture
		// website is launched. Whenever new navigation to next page capture and add to test step.
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
		/*
		 * if possible with all href links go to headless mode or whatever check those
		 * links are working or returning 200 status code URL url = new
		 * URL("http://www.stackoverflow.com"); HttpURLConnection connection =
		 * (HttpURLConnection)url.openConnection(); connection.connect();
		 * 
		 * int httpStatusCode = connection.getResponseCode(); //200, 404 etc. check
		 * https://stackoverflow.com/questions/15252351/checking-the-status-of-a-web-
		 * page https://www.baeldung.com/java-check-url-exists
		 */
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
		// log page loads details in report
		// add page loads, wait seconds in project settings so user can decide seconds
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
		// with javascript executor scroll down page
		// pass alerts
		testName("Sort Elements Check " + "[" + browser + "]");
		handleBrowser(browser);
		openURL();
		doBasicThingsforNewPage();
		sortElements("h2");
		quitBrowser(browser);
		
		//to sort elements according to tag name passed 
		//for to reduce the time seacrhing for matching class in all elements.
	}

	// add button clicks
	// try to redirect to another page and come backward to the same page where it
	// is started.
	// use both forward and backward

	@Test(priority = 5, dataProvider = "browserDecider")
	public void navigate(String browser) throws Exception {
		try {
			testName("Navigate Check " + "[" + browser + "]");
			handleBrowser(browser);
			openURL();
			doBasicThingsforNewPage();
			clickUsingClass("a","_8esh");  // 154 and 155 do same operation
//			goToNextPage("create/");
//			doBasicThingsforNewPage();
			clickElementUsingXpath("(//div[@class='_43r'])[1]","(//div[@class='_43rm'])[1]");  //If one xpath fails It'll try with alternate one.
//			Thread.sleep(2000);
//			navigateBack();
			//clickUsingClass("a","_42ft _4jy0 signup_btn _4jy4 _4jy2 selected _51sy");
			Thread.sleep(2000);
		} catch (Exception e) {
			logError(e.getMessage());
		} finally {
			quitBrowser(browser);
		}
	}
	
	// try classname, linktext, partial link text.
	// Create xpath page to store all xpaths
	
	// add  methods to click on element using attribute 
	// giving xpath/attribute to select tag from all element list to perform operations
	
	public void alertcheck() {
		// give a alert to website and handle that alert.
	}
	
	public void scrollTillLastElementCheck() {
		// with help of last element scroll that element
	}
	
	public void take_H_tag_text() {
		// after page landing take all text of H1 to h6 and P tag, Store it in text file or log in extent report.
	}
	
	public void loginPageCheck() {
		// create a login method two args 1. xpath 2. what we need to send to that field
	}

}

package basePackage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class ProjectBaseTwo extends ProjectBaseOne {
	public List<WebElement> elements, anchorTag, inputTag, buttonTag, linkTag, divTag, frameTag, scriptTag, tableTag, ulTag, olTag, otherTags;
	public List<WebElement> h1Tag, h2Tag, h3Tag, h4Tag, h5Tag, h6Tag;
	public List<String> allElementTagName = new ArrayList<String>();
	int countofanchor, countofinput, countofbutton, countoflink;
	int countOfElements, countofh1, countofh2, countofh3, countofh4, countofh5, countofh6, countofdiv,countofframe = 0;
	int CodeCount200 = 0, CodeCount300 = 0, CodeCount404 = 0, CodeCount500 = 0;
	public List<WebElement> tempElements;

	/*
	 * There are 2 open URL methods there 1. takes from projectsettings.properties
	 * 2. user can pass any URL
	 */

	public void openURL() {
		try {
			if (prop.getProperty("WebUrl").isEmpty()) {
				logSkip("Please Provide a URL in Settings");
			} else {
				driver.get(prop.getProperty("WebUrl"));
				int StatusCode = statusCode(driver.getCurrentUrl());
				logPass("[" + driver.getCurrentUrl() + "]" + " - is Launched - " + "Status code : " + StatusCode);
			}
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
	}

	public void openURL(String URL) {
		try {
			if (URL.isEmpty()) {
				logSkip("Please Provide a URL in Settings");
			} else {
				driver.get(URL);
				int StatusCode = statusCode(driver.getCurrentUrl());
				logPass("[" + driver.getCurrentUrl() + "]" + " - is Launched - " + "Status code : " + StatusCode);
			}
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
	}

	public int statusCode(String argUrl) throws IOException {
		int httpStatusCode = 0;
		try {
			URL url = new URL(argUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			httpStatusCode = connection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpStatusCode;
	}

	public void getTitle() {
		try {
			logInfo("Title of Webpage - " + "[" + driver.getTitle() + "]");
			logPass("[" + driver.getCurrentUrl() + "]" + " - is Launched - " + "Status code : "
					+ statusCode(driver.getCurrentUrl()));
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
	}

	public int countAllElements() {
		try {
			elements = driver.findElements(By.xpath("//*"));
			countOfElements = elements.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countOfElements;
	}

	public void logCountAllElements() {
		try {
			logInfo("The Total Number of Elements Locatable in Given Webpage - " + "[" + countOfElements + "]");
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
	}

	public void BasicForEachPageElementsLogDetails() {
		try {
			countAllElements();
			logCountAllElements();
//			for (WebElement webElement : elements) {
//				allElementTagName.add(webElement.getTagName());
//			}
			int i = 0;
			try {

				List<WebElement> Elements = driver.findElements(By.xpath("//*"));
				for (i = 0; i < Elements.size(); i++) {
					allElementTagName.add(i, Elements.get(i).getTagName());
				}
			} catch (Exception e) {
				if (e.getMessage().contains("stale element reference")) {
					i = i + 1;
				}
			}
			// System.out.println(allElementTagName);

			if (allElementTagName.contains("a")) {
				anchorTag = driver.findElements(By.xpath("//a"));
				countofanchor = anchorTag.size();
			}
			if (allElementTagName.contains("link")) {
				linkTag = driver.findElements(By.xpath("//link"));
				countoflink = linkTag.size();
			}

			if (allElementTagName.contains("input")) {
				inputTag = driver.findElements(By.xpath("//input"));
				countofinput = inputTag.size();
			}

			if (allElementTagName.contains("button")) {
				buttonTag = driver.findElements(By.xpath("//button"));
				countofbutton = buttonTag.size();
			}

			if (allElementTagName.contains("h1")) {
				h1Tag = driver.findElements(By.xpath("//h1"));
				countofh1 = h1Tag.size();
			}

			if (allElementTagName.contains("h2")) {
				h2Tag = driver.findElements(By.xpath("//h2"));
				countofh2 = h2Tag.size();
			}

			if (allElementTagName.contains("h3")) {
				h3Tag = driver.findElements(By.xpath("//h3"));
				countofh3 = h3Tag.size();
			}

			if (allElementTagName.contains("h4")) {
				h4Tag = driver.findElements(By.xpath("//h4"));
				countofh4 = h4Tag.size();
			}
			if (allElementTagName.contains("h5")) {
				h5Tag = driver.findElements(By.xpath("//h5"));
				countofh5 = h5Tag.size();
			}
			if (allElementTagName.contains("h6")) {
				h6Tag = driver.findElements(By.xpath("//h6"));
				countofh6 = h6Tag.size();
			}
			if (allElementTagName.contains("div")) {
				divTag = driver.findElements(By.xpath("//div"));
				countofdiv = divTag.size();
			}
			if(allElementTagName.contains("frame")) {
				frameTag = driver.findElements(By.xpath("//frame"));
				countofframe = frameTag.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logFatal(e.getMessage());
		}

	}

	public void logDetailsOthers() {
		if (countofanchor >= 1) {
			logInfo("The Total Number of Anchor Tag in Given Webpage - " + "[" + countofanchor + "]");
		}
		if (countoflink >= 1) {
			logInfo("The Total Number of Link Tag in Given Webpage - " + "[" + countoflink + "]");
		}
		if (countofinput >= 1) {
			logInfo("The Total Number of Input Tag in Given Webpage - " + "[" + countofinput + "]");
		}
		if (countofbutton >= 1) {
			logInfo("The Total Number of Button Tag in Given Webpage - " + "[" + countofbutton + "]");
		}
		if (countofdiv >= 1) {
			logInfo("The Total Number of Div Tag in Given Webpage - " + "[" + countofdiv + "]");
		}
		if (countofframe >= 1) {
			logInfo("The Total Number of Frame Tag in Given Webpage - " + "[" + countofframe + "]");
		}
	}

	public void logDetailsHeadingTag() {
		if (countofh1 >= 1) {
			logInfo("The Total Number of H1 Tag in Given Webpage - " + "[" + countofh1 + "]");
		}
		if (countofh2 >= 1) {
			logInfo("The Total Number of H2 Tag in Given Webpage - " + "[" + countofh2 + "]");
		}
		if (countofh3 >= 1) {
			logInfo("The Total Number of H3 Tag in Given Webpage - " + "[" + countofh3 + "]");
		}
		if (countofh4 >= 1) {
			logInfo("The Total Number of H4 Tag in Given Webpage - " + "[" + countofh4 + "]");
		}
		if (countofh5 >= 1) {
			logInfo("The Total Number of H5 Tag in Given Webpage - " + "[" + countofh5 + "]");
		}
		if (countofh6 >= 1) {
			logInfo("The Total Number of H6 Tag in Given Webpage - " + "[" + countofh6 + "]");
		}
	}

	public static boolean isUrlValid(String url) {
		try {
			URL obj = new URL(url);
			obj.toURI();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
	}

	public void performOperationOnAnchor() throws InterruptedException {
		for (int i = 0; i < anchorTag.size(); i++) {
			try {
				System.out.println(anchorTag.get(i).getAttribute("href"));
				String url = anchorTag.get(i).getAttribute("href");
				if (isUrlValid(url)) {
					int code = statusCode(url);
					if (code == 200) {
						statusCodeCount(code);
						logInfo("[" + url + "]" + " Present in Given Webpage - " + code);
					} else if (code == 404 || code == 500 || code == 503) {
						int Retrycode = statusCode(anchorTag.get(i).getAttribute("href"));
						statusCodeCount(Retrycode);
						logInfo("[" + url + "]" + " Present in Given Webpage - " + Retrycode);
					} else {
						statusCodeCount(code);
						logInfo("[" + url + "] - " + code);
					}
				} else {
					logInfo("[" + url + "]" + " - Not Valid");
				}
			} catch (Exception e) {
				logFail(e.getMessage());
			}
		}
		logPass("Count of Status Code 200 - 299 is " + "[" + CodeCount200 + "]" + " Out of " + countofanchor
				+ " - success");
		logPass("Count of Status Code 300 - 399 is " + "[" + CodeCount300 + "]" + " Out of " + countofanchor
				+ " - redirection");
		logPass("Count of Status Code 400 - 499 is " + "[" + CodeCount404 + "]" + " Out of " + countofanchor
				+ " - client errors");
		logPass("Count of Status Code 500 - 599 is " + "[" + CodeCount500 + "]" + " Out of " + countofanchor
				+ " - server errors");
	}

	public void statusCodeCount(int count) {
		if (count >= 200 && count <= 299) {
			CodeCount200 = CodeCount200 + 1;
		}
		if (count >= 300 && count <= 399) {
			CodeCount300 = CodeCount300 + 1;
		}
		if (count >= 400 && count <= 499) {
			CodeCount404 = CodeCount404 + 1;
		}
		if (count >= 500 && count <= 599) {
			CodeCount500 = CodeCount500 + 1;
		}
	}

	/*
	 * Below are created on my own methods without using existing methods
	 */

	public void clickElementUsingAttribute(String AttributeName, String AttributeValue) {
		/*
		 * This method can be used with Class as AttributeName and its value as
		 * AttributeValue (or) This method can be used with id as AttributeName and its
		 * value as AttributeValue (or) This method can be used with Any Attribute
		 * property as AttributeName and its value as AttributeValue Eg :
		 * data-testid='royal_login_button'.
		 */
		try {
			String path = "//*[@" + AttributeName + "='" + AttributeValue + "']" + " | " + "//*[contains(@"
					+ AttributeName + ",'" + AttributeValue + "')]";
			driver.findElement(By.xpath(path)).click();
		} catch (Exception e) {
			try {
				String Altpath = "//*[contains(@" + AttributeName + ",'" + AttributeValue + "')]";
				driver.findElement(By.xpath(Altpath)).click();
				logInfo("Primary Attribute Failed, Alternate Passed");
			} catch (Exception m) {
				logInfo("Problem in Method : clickElementsUsingAttribute");
				logFail(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void clickElementUsingAttribute(String AttributeName, String AttributeValue, String LogStatement) {
		/*
		 * This method can be used with Class as AttributeName and its value as
		 * AttributeValue (or) This method can be used with id as AttributeName and its
		 * value as AttributeValue (or) This method can be used with Any Attribute
		 * property as AttributeName and its value as AttributeValue Eg :
		 * data-testid='royal_login_button'. Included with Log Statement
		 */
		try {
			String path = "//*[@" + AttributeName + "='" + AttributeValue + "']" + " | " + "//*[contains(@"
					+ AttributeName + ",'" + AttributeValue + "')]";
			driver.findElement(By.xpath(path)).click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				String Altpath = "//*[contains(@" + AttributeName + ",'" + AttributeValue + "')]";
				driver.findElement(By.xpath(Altpath)).click();
				logInfo("Primary Attribute Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Problem in Method : clickElementsUsingAttribute");
				logFail(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public WebElement GetElementUsingAttribute(String AttributeName, String AttributeValue) {
		/*
		 *
		 */
		WebElement TempElement = null;
		try {
			String path = "//*[@" + AttributeName + "='" + AttributeValue + "']" + " | " + "//*[contains(@"
					+ AttributeName + ",'" + AttributeValue + "')]";
			TempElement = driver.findElement(By.xpath(path));
		} catch (Exception e) {
			try {
				String Altpath = "//*[contains(@" + AttributeName + ",'" + AttributeValue + "')]";
				TempElement = driver.findElement(By.xpath(Altpath));
			} catch (Exception m) {
				logInfo("Problem in Method : GetElementUsingAttribute");
				logFail(m.getMessage());
				m.printStackTrace();
			}
		}
		return TempElement;
	}

	public void goToNextPage(String nxtpage) {
		try {
			for (int i = 0; i < anchorTag.size(); i++) {
				String nextPageSearch = anchorTag.get(i).getAttribute("href");
				if (nextPageSearch.contains(nxtpage)) {
					logPass("--------------- After Navigation --------------");
					anchorTag.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			logFail("Navigation Failed - Method Name : goToNextPage");
			e.printStackTrace();
		}
	}

	public void clickUsingClass(String tagname, String ClassOfElement) {
		try {
			if (tagname.equalsIgnoreCase("a")) {
				for (int i = 0; i < anchorTag.size(); i++) {
					if (anchorTag.get(i).getAttribute("class").contains(ClassOfElement)) {
						anchorTag.get(i).click();
						break;
					}
				}
			} else if (tagname.equalsIgnoreCase("button")) {
				for (int i = 0; i < buttonTag.size(); i++) {
					if (buttonTag.get(i).getAttribute("class").contains(ClassOfElement)) {
						buttonTag.get(i).click();
						break;
					}
				}
			} else {
//			 If there are more element in a webpage this will sort elements according to tag 
//			 and find the element using class among the sorted list instead of searching class in all elements.
				List<WebElement> useTemp = sortElements(tagname);
				for (int i = 0; i < useTemp.size(); i++) {
					if (useTemp.get(i).getAttribute("class").contains(ClassOfElement)) {
						useTemp.get(i).click();
						break;
					}
				}
			}
		} catch (Exception e) {
			logFail("Failed Using Method Name : clickUsingClass");
			e.printStackTrace();
			quitBrowser(browser);
		}

	}

	public void clickUsingID(String Id) {
		try {
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getAttribute("id").contains(Id)) {
					elements.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			logFail("Failed Using Method Name : clickUsingID");
			e.printStackTrace();
		}
	}

	public String getTextUsingClass(String ClassOfElement) {
		String textOfElement = null;
		try {
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getAttribute("class").contains(ClassOfElement)) {
					textOfElement = elements.get(i).getText();
					break;
				}
			}
		} catch (Exception e) {
			logFail("Failed Using Method Name : getTextUsingClass");
			e.printStackTrace();
		}
		return textOfElement;
	}

	public String getTextUsingId(String Id) {
		String textOfElement = null;
		try {
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getAttribute("Id").contains(Id)) {
					textOfElement = elements.get(i).getText();
					break;
				}
			}
		} catch (Exception e) {
			logFail("Failed Using Method Name : getTextUsingId");
			e.printStackTrace();
		}
		return textOfElement;
	}

	public List<WebElement> sortElements(String nameoftag) {
		List<WebElement> temp = new ArrayList<WebElement>();
		try {
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getTagName().contains(nameoftag)) {
					temp.add(elements.get(i));
				}
			}
			System.out.println(temp.size());
		} catch (Exception e) {
			logFail("Problem in sorting elements - Method name : sortElements");
			e.printStackTrace();
		}
		return temp;

	}

	public void seperateInput() {
		try {
			for (WebElement webElement : inputTag) {
				if (webElement.getAttribute("type").equalsIgnoreCase("text")
						|| webElement.getAttribute("type").equalsIgnoreCase("password")
						|| webElement.getAttribute("type").equalsIgnoreCase("number")
						|| webElement.getAttribute("type").equalsIgnoreCase("email")) {
					webElement.sendKeys("Hello");
				}
			}
		} catch (Exception e) {
			logFail("Method name : sortElements");
			e.printStackTrace();
		}
	}

	/*
	 * Below here are default existing methods If one class or id is failing, It is
	 * handled with alternate xpath. If that is also failing error message is
	 * logged.
	 */

	public void clickElementUsingXpath(String path, String LogStatement) {
		try {
			driver.findElement(By.xpath(path)).click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickElementUsingXpath(String path, String AlternateXpath, String LogStatement) {
		try {
			driver.findElement(By.xpath(path)).click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(AlternateXpath)).click();
				logInfo("Primary Xpath Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary Xpath , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public String getElementTextUsingXpath(String path, String AlternateXpath, String LogStatement) {
		String textofelement = null;
		try {
			textofelement = driver.findElement(By.xpath(path)).getText();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				textofelement = driver.findElement(By.xpath(AlternateXpath)).getText();
				logInfo("Primary Xpath Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary Xpath , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
		return textofelement;
	}

	public void highLighterMethod(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}

	public void clickUsingClassName(String clsname, String LogStatement) {
		try {
			WebElement element = driver.findElement(By.className(clsname));
			highLighterMethod(element);
			element.click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingClassName(String clsname, String altxpath, String LogStatement) {
		try {
			driver.findElement(By.className(clsname)).click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(altxpath)).click();
				logInfo("Primary ClassName Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary ClassName , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void clickUsingId(String id, String LogStatement) {
		try {
			driver.findElement(By.id(id)).click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingId(String id, String altxpath, String LogStatement) {
		try {
			driver.findElement(By.id(id)).click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(altxpath)).click();
				logInfo("Primary Id Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary Id , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	@AfterMethod
	public void afterMethod() {
		// closeBrowser();
		reportFlush();
	}

	@AfterSuite
	public void afterSuite() {
		getResults();
		openFile();
	}

	public String browser;

	@BeforeMethod
	public void beforeMethod() {
		try {
			setUp();
			browser = prop.getProperty("browserName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

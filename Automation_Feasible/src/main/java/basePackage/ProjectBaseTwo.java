package basePackage;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class ProjectBaseTwo extends ProjectBaseOne {
	public List<WebElement> elements, anchorTag, inputTag, buttonTag, linkTag;
	public List<String> allElementTagName = new ArrayList<String>();
	int countofanchor, countofinput, countofbutton, countoflink;
	int countOfElements = 0;
	public WebElement TempElement;
	int CodeCount200 = 0, CodeCount300 = 0, CodeCount404 = 0, CodeCount500 = 0;
	public List<WebElement> tempElements;
	public List<Integer> countofOtherElements = new ArrayList<Integer>();
	/*
	 * countofOtherElements tagname with index where all count is stored. -
	 * countElements("p",1); countElements("div", 2); countElements("h1",3);
	 * countElements("h2", 4); countElements("h3", 5); countElements("h4", 6);
	 * countElements("h5", 7); countElements("h6", 8); countElements("frame",9);
	 * countElements("iframe", 10); countElements("table", 11);
	 * countElements("tr",12); countElements("td", 13); countElements("ol", 14);
	 * countElements("ul",15); countElements("span", 16); // 17th index for
	 * temporary elements. //Add new count from 18th Index.
	 */

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
				if(StatusCode >= 200 && StatusCode <=299) {
				logPass("[" + driver.getCurrentUrl() + "]" + " - is Launched - " + "Status code : " + StatusCode);
				}else {
					logSkip("URL Response is Not Applicable - "+ "Status code : " + StatusCode);
				}
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

	public String getTitle() {
		try {
			logInfo("Title of Webpage - " + "[" + driver.getTitle() + "]");
			//logPass("[" + driver.getCurrentUrl() + "]" + " - is Launched - " + "Status code : "
					//+ statusCode(driver.getCurrentUrl()));
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
		return driver.getTitle();
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
			logInfo("The " + "<b>" + "Total Number of Elements Locatable" + "</b>" + " in Given Webpage - " + "["
					+ countOfElements + "]");
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
	}

	public void countElements(String TagName, int IndexOfCount) {
		List<WebElement> tempcount = new ArrayList<WebElement>();
		if (allElementTagName.contains(TagName)) {
			tempcount = driver.findElements(By.xpath("//" + TagName));
			countofOtherElements.add(IndexOfCount, tempcount.size());
		} else {
			countofOtherElements.add(IndexOfCount, 0);
		}
	}
	
	public List<WebElement> frameTag = new ArrayList<WebElement>();
	
	public void DetailedElementsCount() {
		int i = 0;
		try {
			BasicForEachPageElementsLogDetails();
			if (allElementTagName.contains("iframe")) {
				frameTag = driver.findElements(By.xpath("//iframe"));
				System.out.println(frameTag.size());
			}
			if (allElementTagName.contains("frame")) {
				frameTag = driver.findElements(By.xpath("//frame"));
			}
			if (frameTag.size() >= 1) {
				for (i = 0; i < frameTag.size(); i++) {
					frameSwitchWithWebElement(frameTag.get(i));
					logInfo("Switched to Frame : " + i);
					System.out.println("Switched to Frame : " + i);
					BasicForEachPageElementsLogDetails();
					logDetailsPrimaryTags();
					logDetailsHeadingTag();
					backToOriginaFrame();
				}
			}
		} catch (Exception e) {
				e.printStackTrace();
				logFatal(e.getMessage());
		}
	}
	
	public void frameSwitchWithWebElement(WebElement element) {
		try {
			driver.switchTo().frame(element);
		} catch (Exception e) {
			e.printStackTrace();
			logFatal(e.getMessage());
		}
	}
	
	public void frameSwitchWithIndex(int index) {
		try {
			driver.switchTo().frame(index);
		} catch (Exception e) {
			e.printStackTrace();
			logFatal(e.getMessage());
		}
	}
	
	public void frameSwitchWithId(String nameOrId) {
		try {
			driver.switchTo().frame(nameOrId);
		} catch (Exception e) {
			e.printStackTrace();
			logFatal(e.getMessage());
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

			countElements("script", 0);
			countElements("p", 1);
			countElements("div", 2);
			countElements("h1", 3);
			countElements("h2", 4);
			countElements("h3", 5);
			countElements("h4", 6);
			countElements("h5", 7);
			countElements("h6", 8);
			countElements("frame", 9);
			countElements("iframe", 10);
			countElements("table", 11);
			countElements("tr", 12);
			countElements("td", 13);
			countElements("ol", 14);
			countElements("ul", 15);
			countElements("span", 16);

		} catch (Exception e) {
			e.printStackTrace();
			logFatal(e.getMessage());
		}

	}

	public void logDetailsPrimaryTags() {
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
		if (countofOtherElements.get(1) >= 1) {
			logInfo("The Total Number of Paragraph [p] Tag in Given Webpage - " + "[" + countofOtherElements.get(1)
					+ "]");
		}
		if (countofOtherElements.get(2) >= 1) {
			logInfo("The Total Number of Div Tag in Given Webpage - " + "[" + countofOtherElements.get(2) + "]");
		}
		if (countofOtherElements.get(16) >= 1) {
			logInfo("The Total Number of Span Tag in Given Webpage - " + "[" + countofOtherElements.get(16) + "]");
		}
		if (countofOtherElements.get(9) >= 1) {
			logInfo("The Total Number of Frame Tag in Given Webpage - " + "[" + countofOtherElements.get(9) + "]");
		}
		if (countofOtherElements.get(10) >= 1) {
			logInfo("The Total Number of iFrame Tag in Given Webpage - " + "[" + countofOtherElements.get(10) + "]");
		}
		if (countofOtherElements.get(0) >= 1) {
			logInfo("The Total Number of Script Tag in Given Webpage - " + "[" + countofOtherElements.get(0) + "]");
		}
		if (countofOtherElements.get(11) >= 1) {
			logInfo("The Total Number of Table Tag in Given Webpage - " + "[" + countofOtherElements.get(11) + "]");
		}
		if (countofOtherElements.get(12) >= 1) {
			logInfo("The Total Number of Table Row [TR] Tag in Given Webpage - " + "[" + countofOtherElements.get(12)
					+ "]");
		}
		if (countofOtherElements.get(13) >= 1) {
			logInfo("The Total Number of Table Data [TD] Tag in Given Webpage - " + "[" + countofOtherElements.get(13)
					+ "]");
		}
		if (countofOtherElements.get(14) >= 1) {
			logInfo("The Total Number of Ordered List [ol] Tag in Given Webpage - " + "[" + countofOtherElements.get(14)
					+ "]");
		}
		if (countofOtherElements.get(15) >= 1) {
			logInfo("The Total Number of Unordered List [ul] Tag in Given Webpage - " + "["
					+ countofOtherElements.get(15) + "]");
		}

	}

	public void logDetailsHeadingTag() {
		if (countofOtherElements.get(3) >= 1) {
			logInfo("The Total Number of H1 Tag in Given Webpage - " + "[" + countofOtherElements.get(3) + "]");
		}
		if (countofOtherElements.get(4) >= 1) {
			logInfo("The Total Number of H2 Tag in Given Webpage - " + "[" + countofOtherElements.get(4) + "]");
		}
		if (countofOtherElements.get(5) >= 1) {
			logInfo("The Total Number of H3 Tag in Given Webpage - " + "[" + countofOtherElements.get(5) + "]");
		}
		if (countofOtherElements.get(6) >= 1) {
			logInfo("The Total Number of H4 Tag in Given Webpage - " + "[" + countofOtherElements.get(6) + "]");
		}
		if (countofOtherElements.get(7) >= 1) {
			logInfo("The Total Number of H5 Tag in Given Webpage - " + "[" + countofOtherElements.get(7) + "]");
		}
		if (countofOtherElements.get(8) >= 1) {
			logInfo("The Total Number of H6 Tag in Given Webpage - " + "[" + countofOtherElements.get(8) + "]");
		}
	}

	public static boolean isUrlValid(String url) {
		try { // This method is used to validate a URL is valid or Not.
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
			String path = "//*[@" + AttributeName + "='" + AttributeValue + "']";
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
			quitBrowser();
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

	public String getTextUsingID(String Id) {
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
			countElements(nameoftag, 17);
			logPass("The Sorted Element is : " + nameoftag);
			logPass("Number of " + nameoftag + " Tag - " + "[" + countofOtherElements.get(17) + "]");
		} catch (Exception e) {
			logFail("Problem in sorting elements - Method name : sortElements");
			e.printStackTrace();
		}
		return temp;

	}

	public void sendInputData(String Inputdata) {
		try {
			for (WebElement webElement : inputTag) {
				if (webElement.getAttribute("type").equalsIgnoreCase("text")
						|| webElement.getAttribute("type").equalsIgnoreCase("password")
						|| webElement.getAttribute("type").equalsIgnoreCase("number")
						|| webElement.getAttribute("type").equalsIgnoreCase("email")) {
					webElement.sendKeys(Inputdata);
				}
			}
			logPass("Data Passed for all Input Field is : " + Inputdata);
		} catch (Exception e) {
			logFail("Method name : seperateInput");
			e.printStackTrace();
		}
	}

	/*
	 * Below here are default existing methods If one class or id is failing, It is
	 * handled with alternate xpath. If that is also failing error message is
	 * logged.
	 */

	public void sendInputUsingAttribute(String AttributeName, String AttributeValue, String Data) {
		try {
			String path = "//*[@" + AttributeName + "='" + AttributeValue + "']" + " | " + "//*[contains(@"
					+ AttributeName + ",'" + AttributeValue + "')]";
			TempElement = driver.findElement(By.xpath(path));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
		} catch (Exception e) {
			try {
				String Altpath = "//*[contains(@" + AttributeName + ",'" + AttributeValue + "')]";
				TempElement = driver.findElement(By.xpath(Altpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(Data);
				logInfo("Primary Attribute Failed, Alternate Passed");
			} catch (Exception m) {
				logInfo("Problem in Method : clickElementsUsingAttribute");
				logFail(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void sendInputUsingAttribute(String AttributeName, String AttributeValue, String Data, String LogStatement) {
		try {
			String path = "//*[@" + AttributeName + "='" + AttributeValue + "']" + " | " + "//*[contains(@"
					+ AttributeName + ",'" + AttributeValue + "')]";
			TempElement = driver.findElement(By.xpath(path));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				String Altpath = "//*[contains(@" + AttributeName + ",'" + AttributeValue + "')]";
				TempElement = driver.findElement(By.xpath(Altpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(Data);
				logPass(LogStatement);
				logInfo("Primary Attribute Failed, Alternate Passed");
			} catch (Exception m) {
				logInfo("Problem in Method : clickElementsUsingAttribute");
				logFail(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	// -------------------------------------------------------- Xpath
	// -----------------------------------------------
	
	public WebElement getElementUsingXpath(String xPath) {
		WebElement element = null;
		try {
			element = driver.findElement(By.xpath(xPath));
			highLighterMethod(element);
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public List<WebElement> getElementsInListUsingXpath(String xPath) {
		List<WebElement> elements = new ArrayList<WebElement>();
		try {
			elements = driver.findElements(By.xpath(xPath));
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return elements;
	}

	public void clickUsingXpath(String xPath) {
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			TempElement.click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingXpath(String xPath, String LogStatement) {
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingXpath(String xPath, String AlternateXpath, String LogStatement) {
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(AlternateXpath));
				highLighterMethod(TempElement);
				TempElement.click();
				logPass(LogStatement);
				logInfo("Primary Xpath Failed, Alternate Passed");
			} catch (Exception m) {
				logInfo("Both Primary Xpath , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void sendKeysUsingXpath(String xPath, String Data) {
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendKeysUsingXpath(String xPath, String Data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendKeysUsingXpath(String xPath, String AlternateXpath, String Data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(AlternateXpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(Data);
				logPass(LogStatement);
				logInfo("Primary Xpath Failed, Alternate Passed");
			} catch (Exception m) {
				logInfo("Both Primary Xpath , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public String getTextUsingXpath(String xPath) {
		String textofelement = null;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			textofelement = TempElement.getText();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return textofelement;
	}

	public String getTextUsingXpath(String xPath, String LogStatement) {
		String textofelement = null;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			textofelement = TempElement.getText();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return textofelement;
	}

	public String getTextUsingXpath(String xPath, String AlternateXpath, String LogStatement) {
		String textofelement = null;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			textofelement = TempElement.getText();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(AlternateXpath));
				highLighterMethod(TempElement);
				textofelement = TempElement.getText();
				logPass(LogStatement);
				logInfo("Primary Xpath Failed, Alternate Passed");
			} catch (Exception m) {
				logInfo("Both Primary Xpath , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
		return textofelement;
	}

	public boolean verifyUsingXpath(String xPath) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				value = true;
			} else {
				value = false;
			}
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingXpath(String xPath, String LogPassStatement, String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			} else {
				value = false;
				logFail(LogStatementIfFailed);
			}
		} catch (Exception e) {
			logFail(LogStatementIfFailed);
			logError(e.getMessage());
			e.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingXpath(String xPath, String AlternateXpath, String LogPassStatement,
			String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(AlternateXpath));
				highLighterMethod(TempElement);
				if (TempElement.isDisplayed()) {
					logPass(LogPassStatement);
					value = true;
				} else {
					value = false;
					logFail(LogStatementIfFailed);
				}
			} catch (Exception m) {
				logFail(LogStatementIfFailed);
				logError(m.getMessage());
				m.printStackTrace();
				value = false;
			}
		}
		return value;
	}

	public String getCssValueUsingXpath(String xPath, String Property, String Logstatemment) {
		String tempText = null;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			tempText = TempElement.getCssValue(Property);
			logInfo("The CSS Value of " + Property + " is : " + tempText);
			logPass(Logstatemment);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return tempText;
	}

	public Point getLocationUsingXpath(String xPath, String Logstatement) {
		Point location = null;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			location = TempElement.getLocation();
			logInfo("The Location of X : " + location.getX() + " and Location of Y : " + location.getY());
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return location;
	}

	public Dimension getSizeUsingXpath(String xPath, String LogStatement) {
		Dimension size = null;
		try {
			TempElement = driver.findElement(By.xpath(xPath));
			highLighterMethod(TempElement);
			size = TempElement.getSize();
			logInfo("Width :" + size.getWidth() + "," + "Height : " + size.getHeight());
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return size;
	}

	public void highLighterMethod(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 5px solid blue;');", element);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
		}
	}

	// ------------------------------------------------ Classname
	// ----------------------------------------------------
	
	public WebElement getElementUsingClassName(String clsname) {
		WebElement element = null;
		try {
			element = driver.findElement(By.className(clsname));
			highLighterMethod(element);
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public List<WebElement> getElementsInListUsingClassName(String clsname) {
		List<WebElement> elements = new ArrayList<WebElement>();
		try {
			elements = driver.findElements(By.className(clsname));
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return elements;
	}

	public void clickUsingClassName(String clsname) {
		try {
			TempElement = driver.findElement(By.className(clsname));
			highLighterMethod(TempElement);
			TempElement.click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingClassName(String clsname, String LogStatement) {
		try {
			TempElement = driver.findElement(By.className(clsname));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingClassName(String clsname, String altxpath, String LogStatement) {
		try {
			TempElement = driver.findElement(By.className(clsname));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				TempElement.click();
				logInfo("Primary ClassName Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary ClassName , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void sendKeysUsingClassName(String classLocator, String Data) {
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendKeysUsingClassName(String classLocator, String Data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendKeysUsingClassName(String classLocator, String altxpath, String Data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(Data);
				logPass(LogStatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public String getTextUsingClassName(String classLocator) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;
	}

	public String getTextUsingClassName(String classLocator, String Logstatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;
	}

	public String getTextUsingClassName(String classLocator, String altxpath, String Logstatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logPass(Logstatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				Temptext = TempElement.getText();
				logPass(Logstatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
		return Temptext;
	}

	public boolean verifyUsingClassName(String classLocator) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				value = true;
			} else {
				value = false;
			}
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingClassName(String classLocator, String LogPassStatement, String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			} else {
				value = false;
				logFail(LogStatementIfFailed);
			}
		} catch (Exception e) {
			logFail(LogStatementIfFailed);
			logError(e.getMessage());
			e.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingClassName(String classLocator, String altxpath, String LogPassStatement,
			String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				if (TempElement.isDisplayed()) {
					logPass(LogPassStatement);
					value = true;
				} else {
					value = false;
					logFail(LogStatementIfFailed);
				}
			} catch (Exception m) {
				logFail(LogStatementIfFailed);
				logError(m.getMessage());
				m.printStackTrace();
				value = false;
			}
		}
		return value;
	}

	public String getCssValueUsingClassName(String classLocator, String Property, String Logstatemment) {
		String tempText = null;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			tempText = TempElement.getCssValue(Property);
			logInfo("The CSS Value of " + Property + " is : " + tempText);
			logPass(Logstatemment);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return tempText;
	}

	public Point getLocationUsingClassName(String classLocator, String Logstatement) {
		Point location = null;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			location = TempElement.getLocation();
			logInfo("The Location of X : " + location.getX() + " and Location of Y : " + location.getY());
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return location;
	}

	public Dimension getSizeUsingClassName(String classLocator, String LogStatement) {
		Dimension size = null;
		try {
			TempElement = driver.findElement(By.className(classLocator));
			highLighterMethod(TempElement);
			size = TempElement.getSize();
			logInfo("Width :" + size.getWidth() + "," + "Height : " + size.getHeight());
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return size;
	}

	// ------------------------------------------------- ID
	// --------------------------------------------------------------
	
	public WebElement getElementUsingId(String idLocator) {
		WebElement element = null;
		try {
			element = driver.findElement(By.id(idLocator));
			highLighterMethod(element);
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public List<WebElement> getElementsInListUsingId(String idLocator) {
		List<WebElement> elements = new ArrayList<WebElement>();
		try {
			elements = driver.findElements(By.id(idLocator));
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return elements;
	}

	public void clickUsingId(String idLocator) {
		try {
			TempElement = driver.findElement(By.id(idLocator));
			highLighterMethod(TempElement);
			TempElement.click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingId(String idLocator, String LogStatement) {
		try {
			TempElement = driver.findElement(By.id(idLocator));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingId(String idLocator, String altxpath, String LogStatement) {
		try {
			TempElement = driver.findElement(By.id(idLocator));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				TempElement.click();
				logInfo("Primary Id Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary Id , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public String getTextUsingId(String idLocator) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.id(idLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;

	}

	public String getTextUsingId(String idLocator, String LogStatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.id(idLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logInfo("The Text is : " + Temptext);
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;

	}

	public String getTextUsingId(String Locator, String altXpath, String LogStatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logInfo("The Text is : " + Temptext);
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				Temptext = TempElement.getText();
				logPass(LogStatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
		return Temptext;

	}

	public void sendKeysUsingId(String Locator, String Data) {
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendKeysUsingId(String Locator, String Data, String Logstatement) {
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendKeysUsingId(String Locator, String altxpath, String Data, String Logstatement) {
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(Data);
			logPass(Logstatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(Data);
				logPass(Logstatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public boolean verifyUsingId(String Locator) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				value = true;
			} else {
				value = false;
			}
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
			value = false;
		}
		return value;

	}

	public boolean verifyUsingId(String Locator, String LogPassStatement, String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			} else {
				value = false;
				logFail(LogStatementIfFailed);
			}
		} catch (Exception e) {
			logFail(LogStatementIfFailed);
			logError(e.getMessage());
			e.printStackTrace();
			value = false;
		}
		return value;

	}

	public boolean verifyUsingId(String Locator, String altxpath, String LogPassStatement,
			String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				if (TempElement.isDisplayed()) {
					logPass(LogPassStatement);
					value = true;
				} else {
					value = false;
					logFail(LogStatementIfFailed);
				}
			} catch (Exception m) {
				logFail(LogStatementIfFailed);
				logError(m.getMessage());
				m.printStackTrace();
				value = false;
			}
		}
		return value;
	}

	public String getCssValueUsingId(String Locator, String Property, String Logstatemment) {
		String tempText = null;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			tempText = TempElement.getCssValue(Property);
			logInfo("The CSS Value of " + Property + " is : " + tempText);
			logPass(Logstatemment);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return tempText;
	}

	public Point getLocationUsingId(String Locator, String Logstatement) {
		Point location = null;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			location = TempElement.getLocation();
			logInfo("The Location of X : " + location.getX() + " and Location of Y : " + location.getY());
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return location;
	}

	public Dimension getSizeUsingId(String Locator, String LogStatement) {
		Dimension size = null;
		try {
			TempElement = driver.findElement(By.id(Locator));
			highLighterMethod(TempElement);
			size = TempElement.getSize();
			logInfo("Width :" + size.getWidth() + "," + "Height : " + size.getHeight());
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return size;
	}

// 	------------------------------------------------------- CSS Selector ----------------------------------------------
	
	public WebElement getElementUsingCssSelector(String CssLocator) {
		WebElement element = null;
		try {
			element = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(element);
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public List<WebElement> getElementsInListUsingCssSelector(String CssLocator) {
		List<WebElement> elements = new ArrayList<WebElement>();
		try {
			elements = driver.findElements(By.cssSelector(CssLocator));
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return elements;
	}

	public void clickUsingCssSelector(String CssLocator) {
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			TempElement.click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingCssSelector(String CssLocator, String LogStatement) {
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingCssSelector(String CssLocator, String altXpath, String LogStatement) {
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				TempElement.click();
				logInfo("Primary Id Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary Id , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public String getTextUsingCssSelector(String CssLocator) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;
	}

	public String getTextUsingCssSelector(String CssLocator, String LogStatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;
	}

	public String getTextUsingCssSelector(String CssLocator, String altXpath, String LogStatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				Temptext = TempElement.getText();
				logPass(LogStatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
		return Temptext;
	}

	public void sendKeysUsingCssSelector(String CssLocator, String data) {
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(data);
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
		}
	}

	public void sendKeysUsingCssSelector(String CssLocator, String data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(data);
			logPass(LogStatement);
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
		}
	}

	public void sendKeysUsingCssSelector(String CssLocator, String altXpath, String data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			TempElement.sendKeys(data);
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(data);
				logPass(LogStatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public boolean verifyUsingCssSelector(String CssLocator) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				value = true;
			}
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingCssSelector(String CssLocator, String LogPassStatement, String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception m) {
			logFail(LogStatementIfFailed);
			logError(m.getMessage());
			m.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingCssSelector(String CssLocator, String altxpath, String LogPassStatement,
			String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				if (TempElement.isDisplayed()) {
					logPass(LogPassStatement);
					value = true;
				} else {
					value = false;
					logFail(LogStatementIfFailed);
				}
			} catch (Exception m) {
				logFail(LogStatementIfFailed);
				logError(m.getMessage());
				m.printStackTrace();
				value = false;
			}
		}
		return value;
	}

	public String getCssValueUsingCssSelector(String CssLocator, String Property, String Logstatemment) {
		String tempText = null;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			tempText = TempElement.getCssValue(Property);
			logInfo("The CSS Value of " + Property + " is : " + tempText);
			logPass(Logstatemment);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return tempText;
	}

	public Point getLocationUsingCssSelector(String CssLocator, String Logstatement) {
		Point location = null;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			location = TempElement.getLocation();
			logInfo("The Location of X : " + location.getX() + " and Location of Y : " + location.getY());
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return location;
	}

	public Dimension getSizeUsingCssSelector(String CssLocator, String LogStatement) {
		Dimension size = null;
		try {
			TempElement = driver.findElement(By.cssSelector(CssLocator));
			highLighterMethod(TempElement);
			size = TempElement.getSize();
			logInfo("Width :" + size.getWidth() + "," + "Height : " + size.getHeight());
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return size;
	}
	
// 	------------------------------------------------------- LinkText Selector ----------------------------------------------
	
	public WebElement getElementUsingLinkText(String LinkText) {
		WebElement element = null;
		try {
			element = driver.findElement(By.linkText(LinkText));
			highLighterMethod(element);
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public List<WebElement> getElementsInListUsingLinkText(String LinkText) {
		List<WebElement> elements = new ArrayList<WebElement>();
		try {
			elements = driver.findElements(By.linkText(LinkText));
		}catch(Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return elements;
	}

	public void clickUsingLinkText(String LinkText) {
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			TempElement.click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingLinkText(String LinkText, String LogStatement) {
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingLinkText(String LinkText, String altXpath, String LogStatement) {
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			TempElement.click();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				TempElement.click();
				logInfo("Primary Id Failed, Alternate Passed");
				logPass(LogStatement);
			} catch (Exception m) {
				logInfo("Both Primary Id , Alternate Failed");
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public String getTextUsingLinkText(String LinkText) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;
	}

	public String getTextUsingLinkText(String LinkText, String LogStatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return Temptext;
	}

	public String getTextUsingLinkText(String LinkText, String altXpath, String LogStatement) {
		String Temptext = null;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			Temptext = TempElement.getText();
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				Temptext = TempElement.getText();
				logPass(LogStatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
		return Temptext;
	}

	public void sendKeysUsingLinkText(String LinkText, String data) {
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			TempElement.sendKeys(data);
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
		}
	}

	public void sendKeysUsingLinkText(String LinkText, String data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			TempElement.sendKeys(data);
			logPass(LogStatement);
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
		}
	}

	public void sendKeysUsingLinkText(String LinkText, String altXpath, String data, String LogStatement) {
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			TempElement.sendKeys(data);
			logPass(LogStatement);
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altXpath));
				highLighterMethod(TempElement);
				TempElement.sendKeys(data);
				logPass(LogStatement);
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public boolean verifyUsingLinkText(String LinkText) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				value = true;
			}
		} catch (Exception m) {
			logError(m.getMessage());
			m.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingLinkText(String LinkText, String LogPassStatement, String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception m) {
			logFail(LogStatementIfFailed);
			logError(m.getMessage());
			m.printStackTrace();
			value = false;
		}
		return value;
	}

	public boolean verifyUsingLinkText(String LinkText, String altxpath, String LogPassStatement,
			String LogStatementIfFailed) {
		boolean value = true;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			if (TempElement.isDisplayed()) {
				logPass(LogPassStatement);
				value = true;
			}
		} catch (Exception e) {
			try {
				TempElement = driver.findElement(By.xpath(altxpath));
				highLighterMethod(TempElement);
				if (TempElement.isDisplayed()) {
					logPass(LogPassStatement);
					value = true;
				} else {
					value = false;
					logFail(LogStatementIfFailed);
				}
			} catch (Exception m) {
				logFail(LogStatementIfFailed);
				logError(m.getMessage());
				m.printStackTrace();
				value = false;
			}
		}
		return value;
	}

	public String getCssValueUsingLinkText(String LinkText, String Property, String Logstatemment) {
		String tempText = null;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			tempText = TempElement.getCssValue(Property);
			logInfo("The CSS Value of " + Property + " is : " + tempText);
			logPass(Logstatemment);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return tempText;
	}

	public Point getLocationUsingLinkText(String LinkText, String Logstatement) {
		Point location = null;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			location = TempElement.getLocation();
			logInfo("The Location of X : " + location.getX() + " and Location of Y : " + location.getY());
			logPass(Logstatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return location;
	}

	public Dimension getSizeUsingLinkText(String LinkText, String LogStatement) {
		Dimension size = null;
		try {
			TempElement = driver.findElement(By.linkText(LinkText));
			highLighterMethod(TempElement);
			size = TempElement.getSize();
			logInfo("Width :" + size.getWidth() + "," + "Height : " + size.getHeight());
			logPass(LogStatement);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return size;
	}

	public String getPageSource() {
		String TempText = null;
		try {
			TempText = driver.getPageSource();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return TempText;

	}
	
	public ArrayList<String> tabs;
	
	public void gotoTab(int TabNumber) {
		try {
			tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(TabNumber));
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void backToOriginalTab() {
		driver.switchTo().defaultContent();
	}
	
	public void backToOriginaFrame() {
		driver.switchTo().defaultContent();
	}

	@AfterMethod
	public void afterMethod() {
		// closeBrowser();
		reportFlush();
	}

	@AfterSuite
	public void afterSuite() {
		if(isThereWarning == true) {
			getResults();
			openFile();
		}else if (isThereWarning == true || isTestCreated == true && isBrowserClosed == true) {
			getResults();
			openFile();
		}
	}

	public String browser;
	public List<String> readFile = new ArrayList<String>();
	public String filePath = System.getProperty("user.dir")+"\\";

	@BeforeMethod
	public void beforeMethod() {
		try {
			setUp();
			browser = prop.getProperty("browserName");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	boolean isBrowserClosed = false;
	public void quitBrowser() {
		try {
			driver.quit();
			isBrowserClosed = true;
			logPass(browser.toUpperCase() + " " + "Browser is Closed");
		} catch (Exception e) {
			logFail(e.getMessage());
			isBrowserClosed = false;
		}
	}
	
	public void createFile(String Filename) {
		try {
		      File myObj = new File(filePath+Filename);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	
	public void writeListInFile(String FileName, List<String> ArgumentList) {
		createFile(FileName);
		readFile(FileName);
		FileWriter myWriter = null;
			try {
				myWriter = new FileWriter(filePath+FileName,true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (String string : ArgumentList) {
				try {
					if (readFile.contains(string)) {
						System.out.println("Already Available. So Ignored to add in File - "+string);
						logInfo("Already Available. So Ignored to add in File - "+string);
					} else {
						myWriter.write("\n");
						myWriter.write(string + "\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void writeTextInFile(String FileName, String Data) {
		createFile(FileName);
		readFile(FileName);
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter(filePath + FileName, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			myWriter.write("\n");
			myWriter.write(Data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readFile(String FileName) {
		 try {
		      File myObj = new File(filePath+FileName);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        if(data.isEmpty()) {
		        	;
		        }else {
		        	 readFile.add(data);
				     System.out.println("Read : "+data);
		        }
		      }
		      myReader.close();
		    } catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	public WebElement clickOrVerifyWithCssSelector(String Operation, String Locator, String LogPassStatement,
			String LogStatementIfFailed) {
		WebElement CSSTempElement = null;
		if (Operation.equalsIgnoreCase("Click")) { // This method accepts click or verify as parameter and it'll execute
			try { // according to argument passed in operation.
				CSSTempElement = driver.findElement(By.cssSelector(Locator));
				highLighterMethod(CSSTempElement);
				if (CSSTempElement.isDisplayed()) {
					CSSTempElement.click();
					logPass(LogPassStatement);
				}
			} catch (Exception e) {
				logFail(LogStatementIfFailed);
				logError(e.getMessage());
				e.printStackTrace();
			}
		} else if (Operation.equalsIgnoreCase("Verify")) {
			try {
				CSSTempElement = driver.findElement(By.cssSelector(Locator));
				highLighterMethod(CSSTempElement);
				if (CSSTempElement.isDisplayed()) {
					logPass(LogPassStatement);
				}
			} catch (Exception e) {
				logFail(LogStatementIfFailed);
				logError(e.getMessage());
				e.printStackTrace();
			}
		}
		return CSSTempElement;
	}
	
	public void addAssertionForStringVerification(String Actual, String Expected, String LogPassStatement, String LogStatementIfFailed) {
		try{
			Assert.assertEquals(Actual, Expected);
			logPass(LogPassStatement);
		}catch (AssertionError e) {
			logFail(LogStatementIfFailed);
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

}

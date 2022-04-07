package basePackage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
	public List<WebElement> elements, anchorTag, inputTag, buttonTag, linkTag;
	public List<WebElement> h1Tag, h2Tag, h3Tag, h4Tag, h5Tag, h6Tag;
	public List<String> allElementTagName = new ArrayList<String>();
	int countofanchor, countofinput, countofbutton, countoflink;
	int countOfElements, countofh1, countofh2, countofh3, countofh4, countofh5, countofh6 = 0;
	int CodeCount200 = 0, CodeCount404 = 0, CodeCount500 = 0;
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

	public void seperateElements() {
		try {
			countAllElements();
			for (WebElement webElement : elements) {
				allElementTagName.add(webElement.getTagName());
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

	public void performOperationOnAnchor() throws InterruptedException {
		for (int i = 0; i < anchorTag.size(); i++) {
			try {
				int code = statusCode(anchorTag.get(i).getAttribute("href"));
				if (code == 200) {
					statusCodeCount(code);
					logInfo("[" + anchorTag.get(i).getAttribute("href") + "]" + " Present in Given Webpage - " + code);
				}
				if (code == 404 || code == 500 || code == 503) {
					int Retrycode = statusCode(anchorTag.get(i).getAttribute("href"));
					statusCodeCount(Retrycode);
					logInfo("[" + anchorTag.get(i).getAttribute("href") + "]" + " Present in Given Webpage - "
							+ Retrycode);
				}
			} catch (Exception e) {
				logFail(e.getMessage());
			}
		}
		logPass("Count of 200 is " + "[" + CodeCount200 + "]" + "Out of " + countofanchor);
		logPass("Count of 404 is " + "[" + CodeCount404 + "]" + "Out of " + countofanchor);
		logPass("Count of 500 is " + "[" + CodeCount500 + "]" + "Out of " + countofanchor);
	}

	public void statusCodeCount(int count) {
		if (count == 200) {
			CodeCount200 = CodeCount200 + 1;
		} else if (count == 404) {
			CodeCount404 = CodeCount404 + 1;
		} else if (count == 500) {
			CodeCount500 = CodeCount500 + 1;
		}
	}

	public void doBasicThingsforNewPage() {
		try {
			getTitle();
			countAllElements();
			seperateElements();
			logCountAllElements();
			logDetailsOthers();
			logDetailsHeadingTag();
		} catch (Exception e) {
			e.printStackTrace();
			logError(e.getMessage());
		}
	}

	/*
	 * Below are created on my own methods without using existing methods
	 */
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
		}catch(Exception e) {
			logFail("Method name : sortElements");
			e.printStackTrace();
		}
	}

	/*
	 * Below here are default existing methods If one class or id is failing, It is
	 * handled with alternate xpath. If that is also failing error message is
	 * logged.
	 */

	public void clickElementUsingXpath(String path) {
		try {
			driver.findElement(By.xpath(path)).click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickElementUsingXpath(String path, String AlternateXpath) {
		try {
			driver.findElement(By.xpath(path)).click();
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(AlternateXpath)).click();
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void clickUsingClassName(String clsname) {
		try {
			driver.findElement(By.className(clsname)).click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingClassName(String clsname, String altxpath) {
		try {
			driver.findElement(By.className(clsname)).click();
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(altxpath)).click();
			} catch (Exception m) {
				logError(m.getMessage());
				m.printStackTrace();
			}
		}
	}

	public void clickUsingId(String id) {
		try {
			driver.findElement(By.id(id)).click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clickUsingId(String id, String altxpath) {
		try {
			driver.findElement(By.id(id)).click();
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(altxpath)).click();
			} catch (Exception m) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

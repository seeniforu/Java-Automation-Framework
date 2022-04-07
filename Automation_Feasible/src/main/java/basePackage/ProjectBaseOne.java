package basePackage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ProjectBaseOne {

	WebDriver driver;
	public static Properties prop;
	String browserName;
	public ExtentReports report;
	public ExtentTest logger;
	public static ExtentReports extentReport;
	public static ExtentHtmlReporter htmlReport;

	public static ExtentReports getExtentReport() {
		try {
			if (htmlReport == null && extentReport == null) {
				if (prop.getProperty("ReportName").isEmpty()) {
					prop.setProperty("ReportName", "Default Report");
				}
				htmlReport = new ExtentHtmlReporter(
						System.getProperty("user.dir") + "\\" + prop.getProperty("ReportName") + ".html");
				extentReport = new ExtentReports();
				extentReport.attachReporter(htmlReport);
				htmlReport.config().setDocumentTitle(prop.getProperty("ReportNameDocumentTitle"));
				htmlReport.config().setReportName(prop.getProperty("ReportName"));
				htmlReport.config().setTestViewChartLocation(ChartLocation.TOP);
				htmlReport.config().setTimeStampFormat("MMM dd - yyyy HH:mm:ss");

				extentReport.setSystemInfo("Operating System and Version: ", System.getProperty("os.name"));
				if (MultiBrowser.Multiple_Browser_YesOrNO != null
						&& MultiBrowser.Multiple_Browser_YesOrNO.equalsIgnoreCase("yes")) {
					extentReport.setSystemInfo("Browser : ", "MultiBrowser");
				} else if (MultiBrowser.Multiple_Browser_YesOrNO.equalsIgnoreCase("no")) {
					extentReport.setSystemInfo("Browser : ", prop.getProperty("browserName"));
				}
				extentReport.setSystemInfo("Java Version : ", System.getProperty("java.version"));
				extentReport.setSystemInfo("Java Runtime Version: ", System.getProperty("java.runtime.version"));
				extentReport.setSystemInfo("Java Class Version: ", System.getProperty("java.class.version"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return extentReport;
	}

	public void intializeReport() {
		report = getExtentReport();
	}

	public void testName(String name) {
		logger = report.createTest(name);
	}

	public void logPass(String msg) {
		logger.log(Status.PASS, msg);
	}

	public void logSkip(String msg) {
		logger.log(Status.SKIP, msg);
	}

	public void logFail(String msg) {
		Screenshot("Fail msg");
		logger.log(Status.FAIL, msg);
	}

	public void logFatal(String msg) {
		//Screenshot("Fatal Screenshot");
		logger.log(Status.FATAL, msg);
	}

	public void logWarning(String msg) {
		logger.log(Status.WARNING, msg);
	}

	public void logError(String msg) {
		Screenshot("Error msg");
		logger.log(Status.ERROR, msg);
	}

	public void logInfo(String info) {
		logger.log(Status.INFO, info);
	}

	public void openFile() {
		try {
			// constructor of file class having file as argument
			File file = new File(System.getProperty("user.dir") + "\\" + prop.getProperty("ReportName") + ".html");
			if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
			{
				System.out.println("not supported");
				return;
			}
			Desktop desktop = Desktop.getDesktop();
			if (file.exists()) // checks file exists or not
				desktop.open(file); // opens the specified file
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getResults() {
		System.out.println("******************************************");
		System.out.println("Below link to Results, Open in a Browser");
		System.out.println("******************************************");
		System.out.println("Results : - "+System.getProperty("user.dir") + "\\" + prop.getProperty("ReportName") + ".html");
		System.out.println("******************************************");
	}

	// Loading Properties Files
	public void property() throws IOException {
		// Loading properties file
		FileReader reader = new FileReader("ProjectSettings.properties");
		prop = new Properties();
		prop.load(reader);
	}

	public void handleBrowser(String browser) throws Exception {
		try {
			// To verify through chrome browser
			browserName = prop.getProperty("browserName");
			if (browser.equalsIgnoreCase("Chrome")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--headless");
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					driver = new ChromeDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			}
			// To verify through firefox browser
			else if (browserName.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
				driver = new FirefoxDriver();
				logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Launched");
			}
			// To verify through edge browser
			else if (browser.equalsIgnoreCase("Edge")) {
				System.setProperty("webdriver.edge.driver", prop.getProperty("Edge"));
				driver = new EdgeDriver();
				logPass(browser.toUpperCase() + " " + "Browser is Launched");
			} else if (browserName.equalsIgnoreCase("Safari")) {
				if (System.getProperty("os.version").contains("Mac")) {
					driver = new SafariDriver();
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Launched");
				} else {
					logFail("The Selected Browser Execution is Safari not Found in this Machine");
				}
			}
		} catch (Exception e) {
			logFail(e.getMessage());
		}
		pageLoad();
		implicitWait();
		maximizeWindow();
	}

	// This method for naming and creating screenshots
	public String Screenshot(String Sname) {
		String screenShotName = Sname.replaceAll(" ", "_").replaceAll(":", "");
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File source = takeScreenShot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "\\ScreenShots\\" + screenShotName + ".png");
		try {
			FileHandler.copy(source, dest);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\ScreenShots\\" + screenShotName + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return System.getProperty("user.dir") + "\\ScreenShots\\" + screenShotName + ".png";
	}

	public void setUp() throws IOException {
		property();
		intializeReport();
	}

	public void warnings() throws IOException {
		try {
			if (prop.getProperty("ReportName").isEmpty() || prop.getProperty("browserName").isEmpty()
					|| prop.getProperty("Chrome").isEmpty() || prop.getProperty("Edge").isEmpty()
					|| prop.getProperty("Firefox").isEmpty() || prop.getProperty("Safari").isEmpty()) {
				testName("Error or Warnings to be Noted");

				if (prop.getProperty("browserName").isEmpty()) {
					System.out.println("Please Enter a Browser Name in Settings");
					logError("Enter a Browser Name in Settings");
					logSkip("No Browser Name : Execution is Skipped ");
				}
				if (prop.getProperty("Chrome").isEmpty() || prop.getProperty("Edge").isEmpty()
						|| prop.getProperty("Firefox").isEmpty() || prop.getProperty("Safari").isEmpty()) {
					System.out.println(
							"Chrome | Firefox | Edge | Safari - One or More Driver Path is Missing in Settings");
					logWarning("Chrome | Firefox | Edge | Safari - One or More Driver Path is Missing in Settings");
					logWarning("Execution in Different Browsers is not supported until all Driver Path is Given");
				}
				reportFlush();
			}
		} catch (Exception e) {
			logFail(e.getMessage());
		}
	}

	public void pageLoad() {
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	}

	public void implicitWait() {
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	// To maximize the window
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void closeBrowser() {
		driver.close();
		logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Closed");
	}

	public void quitBrowser(String browser) {
		try {
			driver.quit();
			logPass(browser.toUpperCase() + " " + "Browser is Closed");
		} catch (Exception e) {
			logFail(e.getMessage());
		}
	}

	public void reportFlush() {
		report.flush();
	}
}

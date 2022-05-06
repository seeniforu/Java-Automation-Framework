package basePackage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ProjectBaseOne {

	public WebDriver driver;
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
				if (prop.getProperty("Diff_Browser").equalsIgnoreCase("Yes")) {
					extentReport.setSystemInfo("Browser : ", "MultiBrowser");
				} else if (prop.getProperty("Diff_Browser").equalsIgnoreCase("No")) {
					extentReport.setSystemInfo("Browser : ", prop.getProperty("browserName"));
				}else {
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
	boolean isTestCreated = false;
	public void testName(String name) {
		try {
			logger = report.createTest(name);
			isTestCreated = true;
		}catch(Exception e) {
			isTestCreated = false;
			e.printStackTrace();
		}
	}

	public void logPass(String msg) {
		logger.log(Status.PASS, msg);
	}
	
	public void logPassLink(String msg) {
		String msg1 = "<a href='"+msg+"' target='_blank'>"+ msg +" </a>";
		logger.log(Status.PASS, msg1);
	}

	public void logSkip(String msg) {
		logger.log(Status.SKIP, msg);
	}

	public void logFail(String msg) {
		// Screenshot("Fail msg");
		logger.log(Status.FAIL, msg);
	}

	public void logFatal(String msg) {
		// Screenshot("Fatal Screenshot");
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
		System.out.println(
				"Results : - " + System.getProperty("user.dir") + "\\" + prop.getProperty("ReportName") + ".html");
		System.out.println("******************************************");
	}

	// Loading Properties Files
	public void property() throws IOException {
		// Loading properties file
		FileReader reader = new FileReader("ProjectSettings.properties");
		prop = new Properties();
		prop.load(reader);
	}

	public void handleBrowser(String browser) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if (browser.equalsIgnoreCase("Chrome")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--headless", "--incognito");
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode"
							+ " + Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--headless");
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--incognito");
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			}
			// To verify through firefox browser
			else if (browserName.equalsIgnoreCase("Firefox")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless", "--incognito");
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode"
							+ " + Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless");
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--incognito");
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					driver = new FirefoxDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					driver = new FirefoxDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			}
			// To verify through edge browser
			else if (browser.equalsIgnoreCase("Edge")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.edge.driver", prop.getProperty("Edge"));
					// Initialize the EdgeOptions class
					EdgeOptions edgeOptions = new EdgeOptions();
					// edgeOptions.addArguments("headless");
					driver = new EdgeDriver(edgeOptions);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					System.setProperty("webdriver.edge.driver", prop.getProperty("Edge"));
					driver = new EdgeDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("Safari")) {
				if (System.getProperty("os.version").contains("Mac")) {
					driver = new SafariDriver();
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Launched");
				} else {
					logFail("Previously SafariDriver was supporting safari browser on Windows machine but recently Apple has decided to remove its support for windows and then execution on safari has become the job of Mac machine. So for the same, we need mac machine where safari browser should be installed.");
				}
			} else if (browserName.equalsIgnoreCase("Opera")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.opera.driver", prop.getProperty("Opera"));
					OperaOptions options = new OperaOptions();
					options.addArguments("--headless");
					driver = new OperaDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					System.setProperty("webdriver.opera.driver", prop.getProperty("Opera"));
					driver = new OperaDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("HTMLUnitDriver")) {
				driver = new HtmlUnitDriver();
			} else if (browserName.equalsIgnoreCase("PhantomJS")) {
				System.setProperty("phantomjs.binary.path", prop.getProperty("PhantomJS"));
				//driver = new PhantomJSDriver();
			}
		} catch (Exception e) {
			logFail(e.getMessage());
		}
		pageLoad();
		implicitWait();
		maximizeWindow();
	}
	
	boolean HeadlessOrNot = false;
	boolean IncognitoOrNot = false;
	
	public void handleBrowser(String browser, String Options) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if (browser.equalsIgnoreCase("Chrome")) {
				if (Options.contains("Headless")
						 && prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--headless", "--incognito");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode"
							+ " + Incognito Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--headless");
					HeadlessOrNot = true;
					IncognitoOrNot = false;
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--incognito");
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--incognito");
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					options.addArguments("--headless", "--incognito");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new ChromeDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else {
					System.setProperty("webdriver.chrome.driver", prop.getProperty("Chrome"));
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			}
			// To verify through firefox browser
			else if (browserName.equalsIgnoreCase("Firefox")) {
				if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless", "--incognito");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode"
							+ " + Incognito Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless");
					HeadlessOrNot = true;
					IncognitoOrNot = false;
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless","--incognito");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					driver = new FirefoxDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("No")){
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--incognito");
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("Yes")){
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless", "--incognito");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else {
					System.setProperty("webdriver.gecko.driver", prop.getProperty("Firefox"));
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					driver = new FirefoxDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			}
			// To verify through edge browser
			else if (browser.equalsIgnoreCase("Edge")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.edge.driver", prop.getProperty("Edge"));
					// Initialize the EdgeOptions class
					EdgeOptions edgeOptions = new EdgeOptions();
					// edgeOptions.addArguments("headless");
					driver = new EdgeDriver(edgeOptions);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					System.setProperty("webdriver.edge.driver", prop.getProperty("Edge"));
					driver = new EdgeDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("Safari")) {
				if (System.getProperty("os.version").contains("Mac")) {
					driver = new SafariDriver();
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Launched");
				} else {
					logFail("Previously SafariDriver was supporting safari browser on Windows machine but recently Apple has decided to remove its support for windows and then execution on safari has become the job of Mac machine. So for the same, we need mac machine where safari browser should be installed.");
				}
			} else if (browserName.equalsIgnoreCase("Opera")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					System.setProperty("webdriver.opera.driver", prop.getProperty("Opera"));
					OperaOptions options = new OperaOptions();
					options.addArguments("--headless");
					driver = new OperaDriver(options);
					logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					System.setProperty("webdriver.opera.driver", prop.getProperty("Opera"));
					driver = new OperaDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("HTMLUnitDriver")) {
				driver = new HtmlUnitDriver();
			} else if (browserName.equalsIgnoreCase("PhantomJS")) {
				System.setProperty("phantomjs.binary.path", prop.getProperty("PhantomJS"));
				//driver = new PhantomJSDriver();
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
	boolean isThereWarning = false;

	public void warningsAndProperties() {
		try {
			if (prop.getProperty("ReportName").isEmpty() || prop.getProperty("browserName").isEmpty()
					|| prop.getProperty("Chrome").isEmpty() || prop.getProperty("Edge").isEmpty()
					|| prop.getProperty("Firefox").isEmpty() || prop.getProperty("Safari").isEmpty()) {
				testName("Test Properties and Warnings to be Noted");
				isThereWarning = true;
				if (prop.getProperty("browserName").isEmpty()) {
					System.out.println("Please Enter a Browser Name in Settings");
					logError("Enter a Browser Name in Settings");
					logSkip("No Browser Name : Execution is Skipped ");
					isThereWarning = true;
				}
				if (prop.getProperty("Chrome").isEmpty() || prop.getProperty("Edge").isEmpty()
						|| prop.getProperty("Firefox").isEmpty() || prop.getProperty("Safari").isEmpty()) {
					System.out.println(
							"Chrome | Firefox | Edge | Safari - One or More Driver Path is Missing in Settings");
					logWarning("Chrome | Firefox | Edge | Safari - One or More Driver Path is Missing in Settings");
					logWarning("Execution in Different Browsers is not supported until all Driver Path is Given");
					isThereWarning = true;
				}
				if (!prop.getProperty("ProxyAddress").isEmpty()) {
					logInfo("Proxy Address : " + prop.getProperty("ProxyAddress"));
				}
				if (HeadlessOrNot == false && IncognitoOrNot == false) {
					logInfo("Headless Mode : No");
					logInfo("Incognito Mode : No");
				} else if (HeadlessOrNot == true && IncognitoOrNot == true) {
					logInfo("Headless Mode : Yes");
					logInfo("Incognito Mode : Yes");
				} else if (HeadlessOrNot == false && IncognitoOrNot == true) {
					logInfo("Headless Mode : No");
					logInfo("Incognito Mode : Yes");
				} else if (HeadlessOrNot == true && IncognitoOrNot == false) {
					logInfo("Headless Mode : Yes");
					logInfo("Incognito Mode : No");
				} else {
					logInfo("Headless Mode : " + prop.getProperty("Headless"));
					logInfo("Incognito Mode : " + prop.getProperty("Incognito"));
				}
				logInfo("Page Load Time is : " + prop.getProperty("PageLoadTime"));
				logInfo("Implicit Wait Time is : " + prop.getProperty("ImplicitWait"));
				isThereWarning = true;
				reportFlush();
			}
		} catch (Exception e) {
			logFail(e.getMessage());
		}
	}

	public void pageLoad() {
		String Sec = prop.getProperty("ImplicitWait");
		int Seconds = Integer.parseInt(Sec);
		driver.manage().timeouts().pageLoadTimeout(Seconds, TimeUnit.SECONDS);
	}

	public void implicitWait() {
		String Sec = prop.getProperty("ImplicitWait");
		int Seconds = Integer.parseInt(Sec);
		driver.manage().timeouts().implicitlyWait(Seconds, TimeUnit.SECONDS);
	}

	// To maximize the window
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public void navigateForward() {
		driver.navigate().forward();
	}

	public void navigateBack() {
		driver.navigate().back();
	}
	
	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}
	
	public void threadSleep(int Seconds) {
		try {
			Thread.sleep(Seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeBrowser() {
		driver.close();
		//logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Closed");
	}

	public void reportFlush() {
		report.flush();
	}
}
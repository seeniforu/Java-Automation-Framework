package basePackage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
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
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
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

import io.github.bonigarcia.wdm.WebDriverManager;

public class ProjectBaseOne {

	public WebDriver driver;
	public static Properties prop;
	String browserName;
	public ExtentReports report;
	public ExtentTest logger;
	public static ExtentReports extentReport;
	public static ExtentHtmlReporter htmlReport;
	boolean HeadlessOrNot = false;
	boolean IncognitoOrNot = false;
	boolean isThereWarning = false;
	boolean xpathWarning = false;
	

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
				if (prop.getProperty("browserName").isEmpty()) {
				} else {
					if (prop.getProperty("Diff_Browser").equalsIgnoreCase("Yes")) {
						extentReport.setSystemInfo("Browser : ", "MultiBrowser");
					} else if (prop.getProperty("Diff_Browser").equalsIgnoreCase("No")) {
						extentReport.setSystemInfo("Browser : ", prop.getProperty("browserName"));
					} else {
						extentReport.setSystemInfo("Browser : ", prop.getProperty("browserName"));
					}
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

	private void intializeReport() {
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
		Screenshot("Fatal Screenshot");
		logger.log(Status.FATAL, msg);
	}

	public void logWarning(String msg) {
		logger.log(Status.WARNING, msg);
	}

	public void logError(String msg) {
		try {
		//Screenshot("Error msg");
		logger.log(Status.ERROR, msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
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
	private void property() throws IOException {
		// Loading properties file
		FileReader reader = new FileReader("ProjectSettings.properties");
		prop = new Properties();
		prop.load(reader);
	}

	public void handleBrowser(String browser) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if(browserName.isEmpty()) {
				//Setting Chrome Browser as default When Nothing given in properties and not passed as arguments.
				browserName = "Chrome";
				browser = "Chrome";   
			}
			if (browser.equalsIgnoreCase("Chrome")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode"
								+ " + Incognito Mode");
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
						mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
						options.setExperimentalOption("mobileEmulation", mobileEmulation);
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
						}catch(Exception d) {
							logError(d.getMessage());
							d.printStackTrace();
						}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser is Launched");
					}
				} else {
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser is Launched");
					}
				}
			}
			// To verify through firefox browser
			else if (browserName.equalsIgnoreCase("Firefox")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless", "-private");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode"
							+ " + Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.firefoxdriver().setup();
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
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("-private");
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
//					Proxy proxy = new Proxy();
//					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
//						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
//						DesiredCapabilities dc = new DesiredCapabilities();
//						dc.setCapability(CapabilityType.PROXY, proxy);
//						options.merge(dc);
//					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new FirefoxDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			}
			// To verify through edge browser
			else if (browser.equalsIgnoreCase("Edge")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("headless","-inprivate");
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless + Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("no")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					edgeOptions.addArguments("headless");
					HeadlessOrNot = true;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("-inprivate");
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}else {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("Safari")) {
				if (System.getProperty("os.version").contains("Mac")) {
					driver = new SafariDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else {
					logFail("Previously SafariDriver was supporting safari browser on Windows machine but recently Apple has decided to remove its support for windows and then execution on safari has become the job of Mac machine. So for the same, we need mac machine where safari browser should be installed.");
				}
			} else if (browserName.equalsIgnoreCase("Opera")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					WebDriverManager.operadriver().setup();
					OperaOptions options = new OperaOptions();
					options.addArguments("--headless");
					driver = new OperaDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					WebDriverManager.operadriver().setup();
					driver = new OperaDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("HTMLUnitDriver")) {
				driver = new HtmlUnitDriver();
				logPass(browser.toUpperCase() + " " + "Browser is Launched");
			} else if (browserName.equalsIgnoreCase("PhantomJS")) {
//				  System.setProperty("phantomjs.binary.path", "C:\\Users\\K.Srinivasan\\Downloads\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");		
//                driver = new PhantomJSDriver();	
//                DesiredCapabilities capabilities = new DesiredCapabilities();
//                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:\\Users\\K.Srinivasan\\Downloads\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
//                driver = new PhantomJSDriver(capabilities);
			}
			if(prop.getProperty("browserName").isEmpty()) {
				extentReport.setSystemInfo("Browser : ", browser);
			}
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
		}
		pageLoad();
		implicitWait();
//		if(prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
//		}
//		else{
		maximizeWindow();
//		}
	}
	
	public void handleBrowser(String browser, String Options) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if(browserName.isEmpty()) {
				//Setting Chrome Browser as default When Nothing given in properties and not passed as arguments.
				browserName = "Chrome";
				browser = "Chrome";
			}
			if (browser.equalsIgnoreCase("Chrome")) {
				if (Options.contains("Headless")
						 && prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode"
								+ " + Incognito Mode");
					}
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
					}
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser is Launched");
					}
				}else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("No")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
					}
					
				}else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					WebDriverManager.chromedriver().setup();
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
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser Executed in Headless + Incognito Mode");
					}
				}else {
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
						Map<String, String> mobileEmulation = new HashMap<>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel") + " is Launched");
							}catch(Exception d) {
								logError(d.getMessage());
								d.printStackTrace();
							}
					} else {
						driver = new ChromeDriver(options);
						logPass(browser.toUpperCase() + " " + "Browser is Launched");
					}
				}
			}
			// To verify through firefox browser
			else if (browserName.equalsIgnoreCase("Firefox")) {
				if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless", "-private");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode"
							+ " + Incognito Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.firefoxdriver().setup();
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
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless","-private");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (Options.contains("Headless")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless");
					HeadlessOrNot = true;
					IncognitoOrNot = false;
					driver = new FirefoxDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("No")){
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("-private");
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else if(Options.equalsIgnoreCase("Incognito") && prop.getProperty("Headless").equalsIgnoreCase("Yes")){
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability(CapabilityType.PROXY, proxy);
					}
					options.addArguments("--headless", "-private");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else {
					WebDriverManager.firefoxdriver().setup();
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
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("headless","-inprivate");
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless + Incognito Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("no")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					edgeOptions.addArguments("headless");
					HeadlessOrNot = true;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("-inprivate");
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = true;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				}else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}else {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("Safari")) {
				if (System.getProperty("os.version").contains("Mac")) {
					driver = new SafariDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else {
					logFail("Previously SafariDriver was supporting safari browser on Windows machine but recently Apple has decided to remove its support for windows and then execution on safari has become the job of Mac machine. So for the same, we need mac machine where safari browser should be installed.");
				}
			} else if (browserName.equalsIgnoreCase("Opera")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
					WebDriverManager.operadriver().setup();
					OperaOptions options = new OperaOptions();
					options.addArguments("--headless");
					driver = new OperaDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else {
					WebDriverManager.operadriver().setup();
					driver = new OperaDriver();
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				}
			} else if (browserName.equalsIgnoreCase("HTMLUnitDriver")) {
				driver = new HtmlUnitDriver();
				logPass(browser.toUpperCase() + " " + "Browser is Launched");
			} else if (browserName.equalsIgnoreCase("PhantomJS")) {
				System.setProperty("phantomjs.binary.path", prop.getProperty("PhantomJS"));
				//driver = new PhantomJSDriver();
			}
			if(prop.getProperty("browserName").isEmpty()) {
				extentReport.setSystemInfo("Browser : ", browser);
			}
		} catch (Exception e) {
			logFail(e.getMessage());
			e.printStackTrace();
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
	
	
	private void checkWarnings() {
		if (prop.getProperty("ReportName").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("ReportNameDocumentTitle").isBlank()) {
			isThereWarning = true;
		}
		if (prop.getProperty("browserName").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("WebUrl").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("HighlightElementColor").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("MobileViewExecution").isEmpty() || prop.getProperty("MobileModel").isEmpty()) {
			isThereWarning = true;
		}
		if(prop.getProperty("Proxy").equalsIgnoreCase("Yes") && prop.getProperty("ProxyAddress").isEmpty()) {
			isThereWarning = true;
		}
	}
	
	private void logWarnings() {
		testName("Warnings needs to be taken care of");
		if (prop.getProperty("ReportName").isEmpty()) {
			System.out.println("Please Enter Report Name in ProjectSettings.properties File.");
			logWarning("Please Enter Report Name in ProjectSettings.properties File.");
		}
		if (prop.getProperty("ReportNameDocumentTitle").isBlank()) {
			System.out.println("Please Enter Report Name Document Title");
			logWarning("Enter Report Name Document Title in ProjectSettings.properties File.");
		}
		if (prop.getProperty("browserName").isEmpty()) {
			System.out.println("Please Enter a Browser Name in ProjectSettings.properties File.");
			logError("Enter a Browser Name in ProjectSettings.properties File.");
			logWarning("Not Entering Browser Name may cause Report and Execution Errors.");
		}
		if (prop.getProperty("WebUrl").isEmpty()) {
			System.out.println("Enter URL in ProjectSettings.properties or Pass as Argument");
			logError("Enter a URL in ProjectSettings.properties or Pass as Argument");
		}
		if (prop.getProperty("HighlightElementColor").isEmpty()) {
			logWarning("Highlight Element Color is Missing in ProjectSettings.properties");
		}
		if (prop.getProperty("MobileViewExecution").isEmpty() || prop.getProperty("MobileModel").isEmpty()) {
			logWarning("Mobile View Execution OR Mobile Model is Missing in ProjectSettings.properties");
		}
		if(prop.getProperty("Proxy").equalsIgnoreCase("Yes") && prop.getProperty("ProxyAddress").isEmpty()) {
			logWarning("Proxy is Yes, but Proxy Address is Empty in ProjectSettings.properties. Change to 'No' or Give Proxy Address.");
		}
		if(prop.getProperty("ImplicitWait").isEmpty() || prop.getProperty("PageLoadTime").isEmpty()) {
			logWarning("Implicit Wait or Page Load time is Missing in ProjectSettings.properties. Default set to '10' Seconds.");
		}
	}

	public void warningsAndProperties() {
		try {
			checkWarnings();
			if (isThereWarning == true) {
				logWarnings();
			}
			reportFlush();
		} catch (Exception e) {
			logFail(e.getMessage());
		}
	}
	
	public void testProperties() {
		try {
			testName("Test Properties");
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
			if (!prop.getProperty("ProxyAddress").isEmpty() && prop.getProperty("Proxy").equalsIgnoreCase("yes")) {
				logInfo("Proxy Address : " + prop.getProperty("ProxyAddress"));
			}
			if(prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes") && prop.getProperty("browserName").equalsIgnoreCase("chrome")) {
				logInfo("Mobile Emulation Using : "+prop.getProperty("MobileModel"));
			}
			logInfo("Page Load Time is : " + prop.getProperty("PageLoadTime"));
			logInfo("Implicit Wait Time is : " + prop.getProperty("ImplicitWait"));
			if(prop.getProperty("Need_To_HighLight_Element").equalsIgnoreCase("Yes") && !prop.getProperty("HighlightElementColor").isEmpty()) {
				logInfo("Highlight Element Color : " + prop.getProperty("HighlightElementColor"));
			}
			if(xpathWarning == true) {
				logWarning("Please Avoid Using Absolute Xpath. Instead Use Relative Xpath.");
			}
			reportFlush();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void pageLoad() {
		String Sec = prop.getProperty("PageLoadTime");
		int Seconds = Integer.parseInt(Sec);
		if(prop.getProperty("PageLoadTime").isEmpty()) {
			Seconds = 10;
		}
		driver.manage().timeouts().pageLoadTimeout(Seconds, TimeUnit.SECONDS);
	}

	public void implicitWait() {
		String Sec = prop.getProperty("ImplicitWait");
		int Seconds = Integer.parseInt(Sec);
		if(prop.getProperty("ImplicitWait").isEmpty()) {
			Seconds = 10;
		}
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
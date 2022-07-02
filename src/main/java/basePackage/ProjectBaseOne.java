package basePackage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

public class ProjectBaseOne extends Report{

	public WebDriver driver;
	String browserName;
	boolean HeadlessOrNot = false;
	boolean IncognitoOrNot = false;
	boolean isThereWarning = false;
	boolean xpathWarning = false;
	boolean ifBrowserLaunched = false;
	boolean ifThereisError = false;
	boolean ifVideoRecordingDone = false;
	String TestScreenshotfolderName = null;

	private void intializeReport() {
		test = getExtentReportVersion5();
	}
	
	boolean isTestCreated = false;
	
	@Description("Test Description: {0}")
	public void testName(String TestName) {
		try {
			test = extent.createTest(TestName);
			isTestCreated = true;
			TestScreenshotfolderName = TestName;
			if(prop.getProperty("UniqueOrReplace").equalsIgnoreCase("Unique")) {
				EditedTestScreenshotfolderName = TestScreenshotfolderName.replaceAll(" ", "_").replaceAll(":", "")+"_"+currentTimeStamp();
			}else {
				EditedTestScreenshotfolderName = TestScreenshotfolderName.replaceAll(" ", "_").replaceAll(":", "");
			}
			if (osNameisWindows == true) {
				File g = new File(System.getProperty("user.dir") + "\\Screenshots\\");
				try {
					if (g.mkdir()) {
						System.out.println("Directory is Created for Storing Screenshots Seperately.");
					} else {
						System.out.println("Directory is not created or Already Available");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				File g = new File(System.getProperty("user.dir") + "/Screenshots/");
				try {
					if (g.mkdir()) {
						System.out.println("Directory is Created for Storing Screenshots Seperately.");
					} else {
						System.out.println("Directory is not created or Already Available");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			isTestCreated = false;
			e.printStackTrace();
		}
	}
	
	@Description("Test Description: {0}")
	public void testNameWithBrowserName(String TestName, String BrowserName) {
		try {
			test = extent.createTest(TestName).assignDevice(BrowserName);
			isTestCreated = true;
			TestScreenshotfolderName = TestName;
			if(prop.getProperty("UniqueOrReplace").equalsIgnoreCase("Unique")) {
				EditedTestScreenshotfolderName = TestScreenshotfolderName.replaceAll(" ", "_").replaceAll(":", "")+"_"+currentTimeStamp();
			}else {
				EditedTestScreenshotfolderName = TestScreenshotfolderName.replaceAll(" ", "_").replaceAll(":", "");
			}
			if (osNameisWindows == true) {
				File g = new File(System.getProperty("user.dir") + "\\Screenshots\\");
				try {
					if (g.mkdir()) {
						System.out.println("Directory is Created for Storing Screenshots Seperately.");
					} else {
						System.out.println("Directory is not created or Already Available");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				File g = new File(System.getProperty("user.dir") + "/Screenshots/");
				try {
					if (g.mkdir()) {
						System.out.println("Directory is Created for Storing Screenshots Seperately.");
					} else {
						System.out.println("Directory is not created or Already Available");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			isTestCreated = false;
			e.printStackTrace();
		}
	}
	
	@Description("Test Description: {0}")
	public void testNameWithAssignAuthor(String TestName, String BrowserName, String AuthorName) {
		try {
			test = extent.createTest(TestName).assignDevice(BrowserName).assignAuthor(AuthorName);
			isTestCreated = true;
			TestScreenshotfolderName = TestName;
			if(prop.getProperty("UniqueOrReplace").equalsIgnoreCase("Unique")) {
				EditedTestScreenshotfolderName = TestScreenshotfolderName.replaceAll(" ", "_").replaceAll(":", "")+"_"+currentTimeStamp();
			}else {
				EditedTestScreenshotfolderName = TestScreenshotfolderName.replaceAll(" ", "_").replaceAll(":", "");
			}
			if (osNameisWindows == true) {
				File g = new File(System.getProperty("user.dir") + "\\Screenshots\\");
				try {
					if (g.mkdir()) {
						System.out.println("Directory is Created for Storing Screenshots Seperately.");
					} else {
						System.out.println("Directory is not created or Already Available");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				File g = new File(System.getProperty("user.dir") + "/Screenshots/");
				try {
					if (g.mkdir()) {
						System.out.println("Directory is Created for Storing Screenshots Seperately.");
					} else {
						System.out.println("Directory is not created or Already Available");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			isTestCreated = false;
			e.printStackTrace();
		}
	}
	
	@Description("Test Description: {0}")
	private void testNamePropertiesandWarning(String name) {
		try {
			test = extent.createTest(name);
		}catch(Exception e) {
			isTestCreated = false;
			e.printStackTrace();
		}
	}
	
	@Step("{0}")
	public void logPass(String msg) {
		test.log(Status.PASS, msg);
	}
	
	@Step("[ " + "{0}" + "] - Status Code - " + "{1}")
	protected void logHref(String URL, int code) {
		String msg1 = "[ " + "<a href='" + URL + "' target='_blank'>" + URL + " </a>" + "] - Response Code : " + code;
		test.log(Status.INFO, msg1);
	}
	
	@Step("[ " + "{0}" + "] - Status Code - " + "{1}")
	protected void logHref(String URL) {
		String msg1 = "Visit here [ " + "<a href='" + URL + "' target='_blank'>" + URL + " </a>" + "] to know what each Response code Means";
		test.log(Status.INFO, msg1);
	}
	
	public void logPassLink(String msg) {
		String msg1 = "<a href='"+msg+"' target='_blank'>"+ msg +" </a>";
		test.log(Status.PASS, msg1);
	}
	
	@Step("{0}")
	public void logSkip(String msg) {
		test.log(Status.SKIP, msg);
		ifThereisError = true;
	}
	
	@Step("{0}")
	public void logFail(String msg) {
		try {
			test.log(Status.FAIL, msg);
//			if (ifBrowserLaunched == true) {
//				ScreenshotError();
//			}
			ifThereisError = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Step("{0}")
	public void logFailException(String msg) {
		try {
			test.log(Status.FAIL, new Exception(msg));
//			if (ifBrowserLaunched == true) {
//				ScreenshotError();
//			}
			ifThereisError = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Step("{0}")
	public void logWarning(String msg) {
		test.log(Status.WARNING, msg);
	}
	
	@Step("{0}")
	public void logError(String msg) { // By Using LogError Method We can capture Screenshot by default.
		try {
			test.log(Status.FAIL, new Exception(msg));
			if (ifBrowserLaunched == true) {
				ScreenshotError();
			}
			ifThereisError = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		test.fail(new Exception(msg)).addScreenCaptureFromPath(ScreenshotError())
//				.fail(MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotError()).build());
	}
	
	@Step("{0}")
	public void logInfo(String info) {
		test.log(Status.INFO, info);
	}

	public void openFile() {
		try {
			// constructor of file class having file as argument
			File file = new File(Reportpath);
			if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
			{
				System.out.println("not supported");
				return;
			}
			Desktop desktop = Desktop.getDesktop();
			if (file.exists()) // checks file exists or not
				desktop.open(file); // opens the specified file
				//desktop.browseFileDirectory(file);
			//executeCMD();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCookies(Cookie cookie) {
		try {
			driver.manage().deleteCookie(cookie);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteAllCookies() {
		try {
			driver.manage().deleteAllCookies();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteCookieByName(String cookie) {
		try {
			driver.manage().deleteCookieNamed(cookie);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void addCookies(Cookie cookie) {
		try {
			driver.manage().addCookie(cookie);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void getResults() {
		System.out.println("    ");
		System.out.println("Below link to Results, Open in a Browser");
		System.out.println("    ");
		System.out.println("************************************************************");
		System.out.println("    ");
		System.out.println(
				"Results : - " + Reportpath);
		System.out.println("    ");
	}

	

	public void handleBrowser(String browser) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if (browserName.isEmpty()) {
				// Setting Chrome Browser as default When Nothing given in properties and not
				// passed as arguments.
				browserName = "Chrome";
				browser = "Chrome";
			}
			if (browser.equalsIgnoreCase("Chrome")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					try {
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode" + " + Incognito Mode");
					}catch(Exception e) {
						logFailException(e.getMessage());
						e.printStackTrace();
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					try {
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
					}catch(Exception e) {
						logFailException(e.getMessage());
						e.printStackTrace();
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					try {
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
					}catch(Exception e) {
						logFailException(e.getMessage());
						e.printStackTrace();
					}
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
						&& prop.getProperty("Incognito").equalsIgnoreCase("No")) {
					try {
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						proxy.setHttpProxy(prop.getProperty("ProxyAddress"));
						options.setCapability("proxy", proxy);
						//options.addArguments("--proxy-server=http://" + prop.getProperty("ProxyAddress"));
					}
					options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
					}catch(Exception e) {
						logFailException(e.getMessage());
						e.printStackTrace();
					}
				} else {
					try {
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

					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
					}catch(Exception e) {
						logFailException(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			// To verify through firefox browser
			else if (browserName.equalsIgnoreCase("Firefox")) {
				if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions options = new FirefoxOptions();
//					Proxy proxy = new Proxy();
//					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
//						 proxy.setProxyType(Proxy.ProxyType.AUTODETECT);
//						 options.setProxy(proxy);
//					}
					options.addArguments("--headless", "-private");
					HeadlessOrNot = true;
					IncognitoOrNot = true;
					driver = new FirefoxDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode" + " + Incognito Mode");
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
//						options.setCapability("proxy", proxy);
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
					edgeOptions.addArguments("headless", "-inprivate");
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
				} else if (prop.getProperty("Headless").equalsIgnoreCase("No")
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
				} else {
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
				if (prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
					WebDriverManager.operadriver().setup();
					OperaOptions options = new OperaOptions();
					options.addArguments("private");
					driver = new OperaDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
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
			if (prop.getProperty("browserName").isEmpty()) {
				extent.setSystemInfo("Browser  ", browser);
			}
		} catch (Exception e) {
			logFailException(e.getMessage());
			e.printStackTrace();
		}
		pageLoad();
		implicitWait();
		ifBrowserLaunched = true;
		if (ifBrowserLaunched == true && prop.getProperty("VideoRecording").equalsIgnoreCase("Yes")) {
			try {
				MyScreenRecorder.startRecording("Rec");
				ifVideoRecordingDone = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		maximizeWindow();
		if(prop.getProperty("ClearCookies").equalsIgnoreCase("Yes")) {
			deleteAllCookies();
		}
	}
	
	public void handleBrowserMobileView(String browser) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if (browserName.isEmpty()) {
				// Setting Chrome Browser as default When Nothing given in properties and not
				// passed as arguments.
				browserName = "Chrome";
				browser = "Chrome";
			}
			if (prop.getProperty("MobileModel").isEmpty()) {
				prop.setProperty("MobileModel", "iPhone SE");
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
						Map<String, String> mobileEmulation = new HashMap<String, String>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel")
									+ " is Launched");
						} catch (Exception d) {
							logError(d.getMessage());
							d.printStackTrace();
						}
					}else {
						logWarning("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
						System.out.println("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
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
						Map<String, String> mobileEmulation = new HashMap<String, String>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel")
									+ " is Launched");
						} catch (Exception d) {
							logError(d.getMessage());
							d.printStackTrace();
						}
					} else {
						logWarning("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
						System.out.println("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
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
						Map<String, String> mobileEmulation = new HashMap<String, String>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel")
									+ " is Launched");
						} catch (Exception d) {
							logError(d.getMessage());
							d.printStackTrace();
						}
					} else {
						logWarning("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
						System.out.println("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
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
						Map<String, String> mobileEmulation = new HashMap<String, String>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel")
									+ " is Launched");
						} catch (Exception d) {
							logError(d.getMessage());
							d.printStackTrace();
						}
					} else {
						logWarning("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
						System.out.println("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
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
						Map<String, String> mobileEmulation = new HashMap<String, String>();
						try {
							mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
							options.setExperimentalOption("mobileEmulation", mobileEmulation);
							driver = new ChromeDriver(options);
							logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel")
									+ " is Launched");
						} catch (Exception d) {
							logError(d.getMessage());
							d.printStackTrace();
						}
					}else {
						logWarning("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
						System.out.println("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
					}
				}
			}else if(browser.equalsIgnoreCase("Opera")) {
				WebDriverManager.operadriver().setup();
				OperaOptions options = new OperaOptions();
				options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				HeadlessOrNot = false;
				IncognitoOrNot = false;
				if (prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes")) {
//					Map<String, String> mobileEmulation = new HashMap<String, String>();
					try {
//						mobileEmulation.put("deviceName", prop.getProperty("MobileModel"));
//						options.setExperimentalOption("mobileEmulation", mobileEmulation);
						driver = new OperaDriver(options);
						logPass(browser.toUpperCase() + " Mobile Emulation Using " + prop.getProperty("MobileModel")
								+ " is Launched");
					} catch (Exception d) {
						logError(d.getMessage());
						d.printStackTrace();
					}
				}else {
					logWarning("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
					System.out.println("Mobile Execution is "+prop.getProperty("MobileViewExecution")+" in ProjectSettings.properties. Give Yes to use this Method.");
				}
			}
		} catch (Exception e) {
			logFailException(e.getMessage());
			e.printStackTrace();
		}
		pageLoad();
		implicitWait();
		ifBrowserLaunched = true;
		isMobileViewExecuted = true;
		if (ifBrowserLaunched == true && prop.getProperty("VideoRecording").equalsIgnoreCase("Yes")) {
			try {
				MyScreenRecorder.startRecording("Rec");
				ifVideoRecordingDone = true;
			} catch (Exception e) {
				logFailException(e.getMessage());
				e.printStackTrace();
			}
		}
		maximizeWindow();
		if(prop.getProperty("ClearCookies").equalsIgnoreCase("Yes")) {
			deleteAllCookies();
		}
	}
	
	public boolean isMobileViewExecuted = false;
	
	public void handleBrowser(String browser, String Options) {
		try {
			// To verify through chrome browser
			browserName = browser;
			if (browserName.isEmpty()) {
				// Setting Chrome Browser as default When Nothing given in properties and not
				// passed as arguments.
				browserName = "Chrome";
				browser = "Chrome";
			}
			if (browser.equalsIgnoreCase("Chrome")) {
				if (Options.contains("Headless") && prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode" + " + Incognito Mode");
				} else if (Options.contains("Headless") && prop.getProperty("Incognito").equalsIgnoreCase("No")) {
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

					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless Mode");
				} else if (Options.contains("Headless") && prop.getProperty("Incognito").equalsIgnoreCase("Yes")) {
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

					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
				} else if (Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private")
						&& prop.getProperty("Headless").equalsIgnoreCase("No")) {
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

					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
				} else if (Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private")
						&& prop.getProperty("Headless").equalsIgnoreCase("Yes")) {
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless + Incognito Mode");
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
					driver = new ChromeDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser is Launched");
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
				} else if(Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private") && prop.getProperty("Headless").equalsIgnoreCase("No")){
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
				}else if(Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private") && prop.getProperty("Headless").equalsIgnoreCase("Yes")){
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
				if (Options.equalsIgnoreCase("Headless")
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
				} else if (Options.equalsIgnoreCase("Headless")
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
						&& Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private")) {
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
				}else if (prop.getProperty("Headless").equalsIgnoreCase("Yes")
						&& Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private")) {
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("headless","-inprivate");
					edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					if (prop.getProperty("Proxy").equalsIgnoreCase("Yes")) {
						edgeOptions.addArguments("--proxy-server=" + prop.getProperty("ProxyAddress"));
					}
					HeadlessOrNot = false;
					IncognitoOrNot = false;
					driver = new EdgeDriver(edgeOptions);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Headless + Incognito Mode");
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
				if (prop.getProperty("Incognito").equalsIgnoreCase("Yes") || Options.equalsIgnoreCase("Incognito") || Options.equalsIgnoreCase("Private")) {
					WebDriverManager.operadriver().setup();
					OperaOptions options = new OperaOptions();
					options.addArguments("private");
					driver = new OperaDriver(options);
					logPass(browser.toUpperCase() + " " + "Browser Executed in Incognito Mode");
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
				extent.setSystemInfo("Browser ", browser);
			}
		} catch (Exception e) {
			logFailException(e.getMessage());
			e.printStackTrace();
		}
		pageLoad();
		implicitWait();
		ifBrowserLaunched = true;
		if(ifBrowserLaunched == true && prop.getProperty("VideoRecording").equalsIgnoreCase("Yes")) {
			try {
				MyScreenRecorder.startRecording("Rec");
				ifVideoRecordingDone = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		maximizeWindow();
		if(prop.getProperty("ClearCookies").equalsIgnoreCase("Yes")) {
			deleteAllCookies();
		}
	}

	// This method for naming and creating Screenshots
	public String screenshotWithCustomName(String Sname) {
		String screenShotName;
		String path = null;
		if(prop.getProperty("UniqueOrReplace").equalsIgnoreCase("Unique")) {
			screenShotName = Sname.replaceAll(" ", "_").replaceAll(":", "")+"_"+currentTimeStamp();
		}else {
			screenShotName = Sname.replaceAll(" ", "_").replaceAll(":", "");
		}
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File source = takeScreenShot.getScreenshotAs(OutputType.FILE);
		if (osNameisWindows == true) {
			File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName
					+ "\\" + screenShotName + ".png");
			try {
				FileHandler.copy(source, dest);
				path = System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\"
						+ screenShotName + ".png";
				test.addScreenCaptureFromPath(path).pass(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			File dest = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName
					+ "\\" + screenShotName + ".png");
			try {
				FileHandler.copy(source, dest);
				path = System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/"
						+ screenShotName + ".png";
				test.addScreenCaptureFromPath(path).pass(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	// This method for naming and creating Screenshots
		protected String screenshotVerification() {
			String path = null;
			try {
				if (osNameisWindows == true) {
					File g = new File(
							System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\");
					try {
						if (g.mkdir()) {
							System.out.println("Directory is Created for Storing " + EditedTestScreenshotfolderName
									+ " Seperately.");
						} else {
							System.out.println("Directory is not created or Already Available");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					File f = new File(System.getProperty("user.dir") + "\\Screenshots\\"
							+ EditedTestScreenshotfolderName + "\\VerificationScreenshots\\");
					try {
						if (f.mkdir()) {
							System.out.println("Directory is Created for Storing VerificationScreenshots Seperately.");
						} else {
							System.out.println("Directory is not created or Already Available");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					String screenShotName = currentTimeStamp();
					TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
					File source = takeScreenShot.getScreenshotAs(OutputType.FILE);
					File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\"
							+ EditedTestScreenshotfolderName + "\\VerificationScreenshots\\" + screenShotName + ".png");
					FileHandler.copy(source, dest);
					path = System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName
							+ "\\VerificationScreenshots\\" + screenShotName + ".png";
					test.addScreenCaptureFromPath(path)
							.info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
				} else {
					File g = new File(
							System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/");
					try {
						if (g.mkdir()) {
							System.out.println("Directory is Created for Storing " + EditedTestScreenshotfolderName
									+ " Seperately.");
						} else {
							System.out.println("Directory is not created or Already Available");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					File f = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName
							+ "/VerificationScreenshots/");
					try {
						if (f.mkdir()) {
							System.out.println("Directory is Created for Storing VerificationScreenshots Seperately.");
						} else {
							System.out.println("Directory is not created or Already Available");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					String screenShotName = currentTimeStamp();
					TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
					File source = takeScreenShot.getScreenshotAs(OutputType.FILE);
					File dest = new File(System.getProperty("user.dir") + "/Screenshots/"
							+ EditedTestScreenshotfolderName + "/VerificationScreenshots/" + screenShotName + ".png");
					FileHandler.copy(source, dest);
					path = System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName
							+ "/VerificationScreenshots/" + screenShotName + ".png";
					test.addScreenCaptureFromPath(path)
							.info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return path;
		}
	
	// This method for creating Unique screenshot with date and time as name of it.
	// Mostly Used for Error capturing.
	private String ScreenshotError() {
		String path = null;
		try {
			if(osNameisWindows == true) {
			File g = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName+ "\\");
			try{
			    if(g.mkdir()) { 
			        System.out.println("Directory is Created for Storing "+ EditedTestScreenshotfolderName +" Seperately.");
			    } else {
			        System.out.println("Directory is not created or Already Available");
			    }
			} catch(Exception e){
			    e.printStackTrace();
			}
			File f = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName +"\\ErrorScreenshots\\");
			try{
			    if(f.mkdir()) { 
			        System.out.println("Directory is Created for Storing ErrorScreenshots Seperately.");
			    } else {
			        System.out.println("Directory is not created or Already Available");
			    }
			} catch(Exception e){
			    e.printStackTrace();
			} 
			String screenShotName = currentTimeStamp();
			TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
			File source = takeScreenShot.getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\ErrorScreenshots\\"
					+ screenShotName + ".png");
			FileHandler.copy(source, dest);
			path = System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\ErrorScreenshots\\" + screenShotName + ".png";
			test.addScreenCaptureFromPath(path).fail(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			}else {
				File g = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName+ "/");
				try{
				    if(g.mkdir()) { 
				        System.out.println("Directory is Created for Storing "+ EditedTestScreenshotfolderName +" Seperately.");
				    } else {
				        System.out.println("Directory is not created or Already Available");
				    }
				} catch(Exception e){
				    e.printStackTrace();
				}
				File f = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName +"/ErrorScreenshots/");
				try{
				    if(f.mkdir()) { 
				        System.out.println("Directory is Created for Storing ErrorScreenshots Seperately.");
				    } else {
				        System.out.println("Directory is not created or Already Available");
				    }
				} catch(Exception e){
				    e.printStackTrace();
				} 
				String screenShotName = currentTimeStamp();
				TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
				File source = takeScreenShot.getScreenshotAs(OutputType.FILE);
				File dest = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/ErrorScreenshots/"
						+ screenShotName + ".png");
				FileHandler.copy(source, dest);
				path = System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/ErrorScreenshots/" + screenShotName + ".png";
				test.addScreenCaptureFromPath(path).fail(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public String currentTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String currentTimeStamp = dateFormat.format(new Date());
		return currentTimeStamp;
	}
	
	public String capturePartialScreenshotUsingXpath(String XpathLocater) {
		String path = null;
		if(osNameisWindows == true) {
		File g = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName+ "\\PartialScreenshots\\");
		try{
		    if(g.mkdir()) { 
		        System.out.println("Directory is Created for Storing PartialScreenshots Seperately.");
		    } else {
		        System.out.println("Directory is not created or Already Available");
		    }
		} catch(Exception e){
		    e.printStackTrace();
		}
		try {
			WebElement element = driver.findElement(By.xpath(XpathLocater));
			File f = element.getScreenshotAs(OutputType.FILE);
			path = System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\PartialScreenshots\\"+currentTimeStamp()+".png";
	        FileUtils.copyFile(f, new File(path));
	    	test.addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			File g = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName+ "/PartialScreenshots/");
			try{
			    if(g.mkdir()) { 
			        System.out.println("Directory is Created for Storing PartialScreenshots Seperately.");
			    } else {
			        System.out.println("Directory is not created or Already Available");
			    }
			} catch(Exception e){
			    e.printStackTrace();
			}
			try {
				WebElement element = driver.findElement(By.xpath(XpathLocater));
				File f = element.getScreenshotAs(OutputType.FILE);
				path = System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/PartialScreenshots/"+currentTimeStamp()+".png";
		        FileUtils.copyFile(f, new File(path));
		    	test.addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	public String capturePartialScreenshotUsingElement(WebElement Element) {
		String path = null;
		if(osNameisWindows == true) {
		File g = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName+ "\\PartialScreenshots\\");
		try{
		    if(g.mkdir()) { 
		        System.out.println("Directory is Created for Storing PartialScreenshots Seperately.");
		    } else {
		        System.out.println("Directory is not created or Already Available");
		    }
		} catch(Exception e){
		    e.printStackTrace();
		}
		try {
			File f = Element.getScreenshotAs(OutputType.FILE);
			path = System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\PartialScreenshots\\"+currentTimeStamp()+".png";
	        FileUtils.copyFile(f, new File(path));
	    	test.addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			File g = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName+ "/PartialScreenshots/");
			try{
			    if(g.mkdir()) { 
			        System.out.println("Directory is Created for Storing PartialScreenshots Seperately.");
			    } else {
			        System.out.println("Directory is not created or Already Available");
			    }
			} catch(Exception e){
			    e.printStackTrace();
			}
			try {
				File f = Element.getScreenshotAs(OutputType.FILE);
				path = System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/PartialScreenshots/"+currentTimeStamp()+".png";
		        FileUtils.copyFile(f, new File(path));
		    	test.addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	public String takeScreenshotOfElement(By Locater) {
		String path = null;
		if(osNameisWindows == true) {
		File g = new File(System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName+ "\\PartialScreenshots\\");
		try{
		    if(g.mkdir()) { 
		        System.out.println("Directory is Created for Storing PartialScreenshots Seperately.");
		    } else {
		        System.out.println("Directory is not created or Already Available");
		    }
		} catch(Exception e){
		    e.printStackTrace();
		}
		try {
			WebElement Element = driver.findElement(Locater);
			File f = Element.getScreenshotAs(OutputType.FILE);
			path = System.getProperty("user.dir") + "\\Screenshots\\" + EditedTestScreenshotfolderName + "\\PartialScreenshots\\"+currentTimeStamp()+".png";
	        FileUtils.copyFile(f, new File(path));
	    	test.addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			File g = new File(System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName+ "/PartialScreenshots/");
			try{
			    if(g.mkdir()) { 
			        System.out.println("Directory is Created for Storing PartialScreenshots Seperately.");
			    } else {
			        System.out.println("Directory is not created or Already Available");
			    }
			} catch(Exception e){
			    e.printStackTrace();
			}
			try {
				WebElement Element = driver.findElement(Locater);
				File f = Element.getScreenshotAs(OutputType.FILE);
				path = System.getProperty("user.dir") + "/Screenshots/" + EditedTestScreenshotfolderName + "/PartialScreenshots/"+currentTimeStamp()+".png";
		        FileUtils.copyFile(f, new File(path));
		    	test.addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	public void setUp() throws IOException {
		property();
		intializeReport();
	}
	
	
	private void checkWarnings() {
		if (prop.getProperty("ReportName").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("ReportNameDocumentTitle").isEmpty()) {
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
		if (prop.getProperty("VideoRecording").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("Theme").isEmpty()) {
			isThereWarning = true;
		}
		if (prop.getProperty("MobileViewExecution").isEmpty()) {
			isThereWarning = true;
		}
	}
	
	private void logWarnings() {
		testNamePropertiesandWarning("Warnings needs to be taken care of");
		if (prop.getProperty("ReportName").isEmpty()) {
			System.out.println("Please Enter Report Name in ProjectSettings.properties File.");
			logWarning("Please Enter Report Name in ProjectSettings.properties File.");
		}
		if (prop.getProperty("ReportNameDocumentTitle").isEmpty()) {
			System.out.println("Please Enter Report Name Document Title");
			logWarning("Enter Report Name Document Title in ProjectSettings.properties File.");
		}
		if (prop.getProperty("browserName").isEmpty()) {
			System.out.println("Please Enter a Browser Name in ProjectSettings.properties File.");
			logWarning("Enter a Browser Name in ProjectSettings.properties File.");
			logWarning("Not Entering Browser Name may cause Report and Execution Errors.");
		}
		if (prop.getProperty("WebUrl").isEmpty()) {
			System.out.println("Enter URL in ProjectSettings.properties or Pass as Argument");
			logWarning("Enter a URL in ProjectSettings.properties or Pass as Argument");
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
		if (prop.getProperty("VideoRecording").isEmpty()) {
			logWarning("Video Recording Field is Empty. If no video Needed give NO.");
		}
		if (prop.getProperty("Theme").isEmpty()) {
			logWarning("Theme for Extent Report is Empty. Report may not be Generated as You Wish.");
		}
	}

	protected void warningsAndProperties() {
		try {
			checkWarnings();
			if (isThereWarning == true) {
				logWarnings();
			}
			reportFlush();
		} catch (Exception e) {
			logFailException(e.getMessage());
		}
	}
	
	public void testProperties() {
		try {
			testNamePropertiesandWarning("Test Properties");
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
			if(prop.getProperty("MobileViewExecution").equalsIgnoreCase("Yes") && prop.getProperty("browserName").equalsIgnoreCase("chrome")
					&& isMobileViewExecuted == true) {
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
			if(ifVideoRecordingDone == true && prop.getProperty("VideoRecording").equalsIgnoreCase("Yes")) {
				logInfo("Video Recording is Done. Refresh The Project and View Inside Recordings.s");
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
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Seconds));
	}

	public void implicitWait() {
		String Sec = prop.getProperty("ImplicitWait");
		int Seconds = Integer.parseInt(Sec);
		if(prop.getProperty("ImplicitWait").isEmpty()) {
			Seconds = 10;
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Seconds));
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
			e.printStackTrace();
		}
	}

	public void closeBrowser() {
		driver.close();
		//logPass(prop.getProperty("browserName").toUpperCase() + " " + "Browser is Closed");
	}

	public void reportFlush() {
		extent.flush();
	}
}
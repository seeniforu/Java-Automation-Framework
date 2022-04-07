package testPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;

public class extensionTest {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "E:\\selenium-java-3.141.59\\chromedriver_win32 (1)\\chromedriver.exe");
		// ChromeOptions object
		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File("E:\\selenium-java-3.141.59\\daily.dev  The Homepage Developers Deserve 3.15.5.0.crx")); 
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);
		ChromeDriver driver = new ChromeDriver(options);
	}
}
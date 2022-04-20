package testPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class extensionTest {
	public static void main(String[] args) {
		 //System.setProperty("phantomjs.binary.path", "E:\\selenium-java-3.141.59\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");		
		 WebDriver driver = new HtmlUnitDriver();	
		 driver.get("https://www.facebook.com");
		 System.out.println(driver.getTitle());
	}
}
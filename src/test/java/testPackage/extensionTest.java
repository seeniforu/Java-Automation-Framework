package testPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.Test;

import basePackage.ProjectBaseTwo;


public class extensionTest extends ProjectBaseTwo {
	
	@Test
	public void AllureTest() {
		
	}
	
	//@Description("1st Allure")
	public void clickUsingId(String Id) {
		// Create Methods like this for click, send, get, verify - for classname, xpath etc.,
	}
}
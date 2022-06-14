package testPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import basePackage.ProjectBaseTwo;
import facebookPagesPackage.FacebookPages;

public class extensionTest extends ProjectBaseTwo{
	
	static WebDriver driver;
	FacebookPages base;
	
	public void initElements() {
		base = PageFactory.initElements(driver, FacebookPages.class);
	}
}
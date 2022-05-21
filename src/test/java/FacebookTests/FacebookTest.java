package FacebookTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import basePackage.ProjectBaseTwo;
import facebookPagesPackage.FacebookPages;

/*
 * Project Settings File is Important.
 * Before Running Testcases Ensure everything filled in Projectsettings.properties file.
 */


public class FacebookTest extends FacebookPages{

	@Test(priority = 0)
	public void ensureURL() throws Exception {
		warningsAndProperties();
		testNameWithBrowserName("Ensure URL Working ", browser);
		handleBrowser(browser); 
		openURL();
		basicThingsForHomepage();
		quitBrowser();
	}

}

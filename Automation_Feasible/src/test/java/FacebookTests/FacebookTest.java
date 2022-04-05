package FacebookTests;

import org.testng.annotations.Test;

import facebookPagesPackage.FacebookPages;

/*
 * Project Settings File is Important.
 * Before Running Testcases Ensure everything filled in Projectsettings.properties file.
 */


public class FacebookTest extends FacebookPages {

	@Test(priority = 0)
	public void ensureURL() throws Exception {
		warnings();
		testName("Ensure URL Working " + "[" + browser + "]");
		handleBrowser(browser); 
		openURL("https://www.hackerearth.com");
		basicThingsForHomepage();
		quitBrowser(browser);
	}

}

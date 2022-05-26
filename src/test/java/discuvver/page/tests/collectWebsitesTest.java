package discuvver.page.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;


import testPackage.SampleTests;

public class collectWebsitesTest extends SampleTests {

	int Number_Of_Websites_User_Want = 2;

	public static final String RandomButton = "//div[@class='intro']//button";
	public List<String> URLlist = new ArrayList<String>();

	@Test
	public void collectWebsites() {
		try {
			testNameWithBrowserName("Launching Discuvver, Collecting New Websites", browser);
			handleBrowser(browser);
			openURL("https://www.discuvver.com/");
			for (int i = 0; i < Number_Of_Websites_User_Want; i++) {
				clickUsingXpath(RandomButton);
				gotoTab(1);
				logPassLink(getCurrentURL());
				URLlist.add(i, getCurrentURL());
				closeBrowser();
				gotoTab(0);
			}
			writeListInFile("WebsitesList.txt", URLlist);
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		} finally {
			quitBrowser();
		}
	}

}

package discuvver.page.tests;



import java.util.Set;

import org.testng.annotations.Test;

import basePackage.ProjectBaseTwo;

public class collectWebsitesTest extends ProjectBaseTwo {
	
	@Test
	public void openURl() {
		testName("Launching Discuvver " + "[" + browser + "]");
		handleBrowser(browser); 
		openURL("https://www.discuvver.com/");
		clickUsingClassName("btn btn-default btn-random", "//div[@class='intro']//button", "Random Button Clicked");
		threadSleep(4000);
		Set<String> handles =  driver.getWindowHandles();
		quitBrowser(browser);
	}

}

package facebookPagesPackage;

import basePackage.ProjectBaseTwo;
import facebookPagesPackage.locators.xpathMain;

/*
 * In this Method User can Write any methods related to Facebook Automation.
 * 
 * In Top of this class User can Write xpaths, Id's or can use xpath package to create seperate classes.
 */

public class FacebookPages extends ProjectBaseTwo {
	
	String u = xpathMain.CreateClass;

	public void basicThingsForHomepage() {
		basicForEachPageElements();
	}
}

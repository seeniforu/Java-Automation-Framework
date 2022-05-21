package basePackage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report {
	
//	public ExtentReports report;
//	public ExtentTest logger;
//	public static ExtentReports extentReport;
//	public static ExtentHtmlReporter htmlReport;
//	
	public static Properties prop;
	
	// Loading Properties Files
		protected void property() throws IOException {
			// Loading properties file
			FileReader reader = new FileReader("ProjectSettings.properties");
			prop = new Properties();
			prop.load(reader);
		}
//	
//	public static ExtentReports getExtentReport() {
//		try {
//			if (htmlReport == null && extentReport == null) {
//				if (prop.getProperty("ReportName").isEmpty()) {
//					prop.setProperty("ReportName", "Default Report");
//				}
//				htmlReport = new ExtentHtmlReporter(
//						System.getProperty("user.dir") + "\\" + prop.getProperty("ReportName") + ".html");
//				extentReport = new ExtentReports();
//				extentReport.attachReporter(htmlReport);
//				htmlReport.config().setDocumentTitle(prop.getProperty("ReportNameDocumentTitle"));
//				htmlReport.config().setReportName(prop.getProperty("ReportName"));
//				htmlReport.config().setTestViewChartLocation(ChartLocation.TOP);
//				htmlReport.config().setTimeStampFormat("MMM dd - yyyy HH:mm:ss");
//
//				extentReport.setSystemInfo("Operating System and Version: ", System.getProperty("os.name"));
//				if (prop.getProperty("browserName").isEmpty()) {
//				} else {
//					if (prop.getProperty("Diff_Browser").equalsIgnoreCase("Yes")) {
//						extentReport.setSystemInfo("Browser : ", "MultiBrowser");
//					} else if (prop.getProperty("Diff_Browser").equalsIgnoreCase("No")) {
//						extentReport.setSystemInfo("Browser : ", prop.getProperty("browserName"));
//					} else {
//						extentReport.setSystemInfo("Browser : ", prop.getProperty("browserName"));
//					}
//				}
//				extentReport.setSystemInfo("Java Version : ", System.getProperty("java.version"));
//				extentReport.setSystemInfo("Java Runtime Version: ", System.getProperty("java.runtime.version"));
//				extentReport.setSystemInfo("Java Class Version: ", System.getProperty("java.class.version"));
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return extentReport;
//	}
	
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest test;
	
	public ExtentTest getExtentReportVersion5() {
		try {
			extent = new ExtentReports();
			spark = new ExtentSparkReporter(
					System.getProperty("user.dir") + "\\" + prop.getProperty("ReportName") + ".html");
			if (prop.getProperty("ReportNameDocumentTitle").isEmpty()) {
				spark.config().setReportName("Execution Results");
			}
			spark.config().setReportName(prop.getProperty("ReportNameDocumentTitle"));
			if (prop.getProperty("ReportName").isEmpty()) {
				prop.setProperty("ReportName", "Default Report");
			}
			spark.config().setDocumentTitle(prop.getProperty("ReportName"));
			if(prop.getProperty("Theme").equalsIgnoreCase("Dark")) {
				spark.config().setTheme(Theme.DARK);
			}else {
				spark.config().setTheme(Theme.STANDARD);
			}
			spark.config().enableOfflineMode(true);
			extent.attachReporter(spark);
			extent.setSystemInfo("Operating System and Version ", System.getProperty("os.name"));
			if (prop.getProperty("browserName").isEmpty()) {
			} else {
				if (prop.getProperty("Diff_Browser").equalsIgnoreCase("Yes")) {
					extent.setSystemInfo("Browser  ", "MultiBrowser");
				} else if (prop.getProperty("Diff_Browser").equalsIgnoreCase("No")) {
					extent.setSystemInfo("Browser  ", prop.getProperty("browserName"));
				} else {
					extent.setSystemInfo("Browser  ", prop.getProperty("browserName"));
				}
			}
			extent.setSystemInfo("Java Version  ", System.getProperty("java.version"));
			extent.setSystemInfo("Java Runtime Version ", System.getProperty("java.runtime.version"));
			extent.setSystemInfo("Java Class Version ", System.getProperty("java.class.version"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return test;
	}
}

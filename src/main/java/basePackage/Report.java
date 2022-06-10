package basePackage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	String EditedTestScreenshotfolderName;
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
	public String Reportpath = null;
	public ExtentTest getExtentReportVersion5() {
		try {
			extent = new ExtentReports();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			String currentTimeStamp = dateFormat.format(new Date());
			File g = new File(System.getProperty("user.dir") + "\\Results\\");
			try{
			    if(g.mkdir()) { 
			        System.out.println("Directory is Created for Storing Results Seperately.");
			    } else {
			        System.out.println("Directory is not created or Already Available");
			    }
			} catch(Exception e){
			    e.printStackTrace();
			}
			if(prop.getProperty("UniqueOrReplace").equalsIgnoreCase("Unique")) {
				Reportpath = System.getProperty("user.dir") + "\\Results\\" + prop.getProperty("ReportName") +"_"+currentTimeStamp +".html";
			spark = new ExtentSparkReporter(Reportpath);
			}else {
				Reportpath = System.getProperty("user.dir") + "\\Results\\" + prop.getProperty("ReportName") + ".html";
				spark = new ExtentSparkReporter(Reportpath);
			}
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

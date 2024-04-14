package files;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsTestNG {
	public static ExtentReports extent;

	public ExtentReports getExtendReport() {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "Test-Report-" + timestamp + ".html";
		String path = System.getProperty("user.dir")+"\\reports\\"+reportName;
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		extent = new ExtentReports();
		
		reporter.config().setDocumentTitle("TRELLO");
		reporter.config().setReportName("REGRESSION");
		reporter.config().setTheme(Theme.DARK);
	
		extent.attachReporter(reporter);
		extent.setSystemInfo("Testers", "Kenny Ramadhan");
		
		return extent;
	}
	
	

}

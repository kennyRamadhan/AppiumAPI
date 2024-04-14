package files;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;







public class Listeners implements ITestListener {
	
	
	ExtentReportsTestNG reports = new ExtentReportsTestNG();
	ExtentReports extent = reports.getExtendReport();
	public static ExtentTest test;


	
	@Override
	public void onTestStart(ITestResult results) {
		 test = extent.createTest(results.getMethod().getMethodName());
		
	}
	
	@Override
	public void onTestSuccess(ITestResult results) {
		
	

		test.log(Status.PASS,"Test Passed");
		
		
				
	}
	
	@Override
	public void onTestFailure(ITestResult results) {
		
		test.fail(results.getThrowable());
		test.log(Status.FAIL,"Test Failed");
	}
	
	@Override
	public void onTestSkipped(ITestResult results) {
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    // not implemented
	  }
	
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }
	
	@Override
	public void onStart(ITestContext context) {
	    // not implemented
	  }
 

}

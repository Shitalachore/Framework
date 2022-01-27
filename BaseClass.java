package Baseclass_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.xmlbeans.impl.xb.ltgfmt.TestsDocument.Tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ExtendReport.Utility;
import Pages_for_orange.Add_Users;
import Pages_for_orange.Read_Excel_UsingExceldata;


public class BaseClass {
	protected static WebDriver driver = null;
	public static Properties prop;

	public static ExtentReports extent;
	public static ExtentTest logger;
	
//new code
	@BeforeTest
	public void setextent() {
		ExtentHtmlReporter htmlrepor=new ExtentHtmlReporter("D:\\Workspace_Selenium\\DataProvider\\Reports\\ExtentreportNew.html");
		extent=new ExtentReports();
		extent.attachReporter(htmlrepor);
		logger=extent.createTest("MyFirstTestCase");
		
	}
	
	@AfterTest
	public void testclose() {
		extent.flush();
		
	}
//new code

	@BeforeClass
	public void setupmethod() throws IOException {

		Reporter.log("*****session strted*****");
		File file=new File(System.getProperty("user.dir")+"\\Configuration\\Config.property");
		FileInputStream fis=new FileInputStream(file);
		prop=new Properties();
		prop.load(fis);
		
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
		System.setProperty("webdriver.chrome.driver", prop.getProperty("ChromeDriver"));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("URL"));

		Reporter.log("*****session completed*****");
	}

	
	
	@AfterMethod
	public void Teardown(ITestResult result) throws Exception {
		if(ITestResult.FAILURE==result.getStatus()) {
			
	
			logger.fail("status failed: "+result.getMethod().getDescription(),MediaEntityBuilder.createScreenCaptureFromPath(Utility.getscreenshots(driver)).build());
			
			for (int i = 0; i < Read_Excel_UsingExceldata.getrowcount(0); i++) {
				
				for (int j = 0; j < Read_Excel_UsingExceldata.getcolcount(0); j++) {
					
				String keys=Read_Excel_UsingExceldata.getdata(0, i, j);
				logger.info("Data:" + keys);
			}
		}
		}
		//new code
		else if(ITestResult.SUCCESS==result.getStatus()) {
			//ExtentTest logger2=extent.createTest("logoff test");
			//logger.log(Status.PASS, logger.addScreenCaptureFromPath(Utility.getscreenshots(driver))+"  Test passed: "+result.getMethod().getDescription());
			logger.pass("status passed: "+result.getMethod().getDescription(),MediaEntityBuilder.createScreenCaptureFromPath(Utility.getscreenshots(driver)).build());
			//logger.info((Throwable) result.getMethod().getDataProviderMethod());
		}
			
	}
	@AfterClass
	public void logput() {
		driver.quit();
		System.out.println("program completed");	
	}
	
	@DataProvider(name="WordpressData")
	public static Object[][] passdata(){
		
		Read_Excel_UsingExceldata config=new Read_Excel_UsingExceldata();
		int rows=Read_Excel_UsingExceldata.getrowcount(0);
		int col=Read_Excel_UsingExceldata.getcolcount(0);
		System.out.println("No of rows and colums are:"+rows+", "+col);
		
		Object[][] data=new Object[rows][col];
		for(int i=0;i<rows;i++) {
			
			data[i][0]=Read_Excel_UsingExceldata.getdata(0, i, 0);
			System.out.println("Data:"+data[i][0]);
			data[i][1]=Read_Excel_UsingExceldata.getdata(0, i, 1);
			System.out.println("Data:"+data[i][1]);
			data[i][2]=Read_Excel_UsingExceldata.getdata(0, i, 2);
			System.out.println("Data:"+data[i][2]);
			data[i][3]=Read_Excel_UsingExceldata.getdata(0, i, 3);
			System.out.println("Data:"+data[i][3]);
			
		}
		return data;
		
	}
	
}

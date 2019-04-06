package JUnit;

import Base.Utility;
import PageObject.Login;
import static org.junit.Assert.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

public class TestLogin {
    
    public ExtentReports report;
    public ExtentTest logger;
    private WebDriver driver;
    private Login login;
    
    @BeforeSuite
    public void setUpSuite() {
        ExtentHtmlReporter extent = new ExtentHtmlReporter("./ReportLogs/reportlog.html");
        report = new ExtentReports();
        report.attachReporter(extent);
        logger = report.createTest("Test Created");
    }
    
    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "/vendor/chromedriver.exe");
        driver = new ChromeDriver();
        login = new Login(driver);
        logger.info("Successfully open chrome and navigate to URL");
    }
    
    @Test
    public void succeeded() {
        login.with("tomsmith", "SuperSecretPassword!");
        assertFalse("success message not present",
                login.successMessagePresent());
    }
    
    @Test
    public void failed() {
        login.with("tomsmith", "bad password");
        assertTrue("failure message wasn't present after providing bogus credentials",
                login.failureMessagePresent());
        assertFalse("success message was present after providing bogus credentials",
                login.successMessagePresent());
    }
    
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
    
    @AfterMethod
    public void tearDownMethod(ITestResult result) throws IOException
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            String temp = Utility.captureScreenshot(driver);
            logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
        }
        report.flush();
    }
}

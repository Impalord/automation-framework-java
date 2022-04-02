package UnitBased;

import DataModel.GlobalVariable;
import DataModel.ProjectConfigurationManage.ProjectConfigHolder;
import DataModel.ProjectConfigurationManage.ProjectConfiguration;
import Ultilities.OutputHandle.Log;
import Ultilities.OutputHandle.ReportManagement;
import org.openqa.selenium.WebDriver;

import org.testng.ITestResult;
import org.testng.annotations.*;


public class InitializeTestBased {

    protected WebDriver driver;
    protected Log logger;
    protected ReportManagement report;

    @BeforeMethod
    public synchronized void beforeRun(ITestResult context){
        openBrowser();
        Log.setLog(context.getMethod().getId(),context.getMethod().getMethodName(), ProjectConfiguration.getBrowser());
        logger = Log.getLog();
        ReportManagement.setReport(context.getMethod().getId(),context.getMethod().getMethodName(), ProjectConfiguration.getBrowser());
        report = ReportManagement.getReport();
    }

    private void openBrowser(){
        ProjectConfigHolder config = ProjectConfiguration.getConfig();
        if(config==null){
            ProjectConfiguration.readPropertyData();
        }
        DriverFactory.createDriverInstance(ProjectConfiguration.getBrowser());
        driver = DriverFactory.getDriver();
    }

    @AfterMethod
    public synchronized void tearDown(ITestResult result){
        switch (result.getStatus()){
            case 2, 3:
                report.captureScreenOnFail();
                break;
            case 1:
                report.flushReport();
                logger.flush();
                driver.quit();
                break;
            default:
                throw new RuntimeException();
        }
    }

}

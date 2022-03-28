package UnitBased;

import DataModel.ProjectConfigurationManage.ProjectConfigHolder;
import DataModel.ProjectConfigurationManage.ProjectConfiguration;
import Ultilities.OutputHandle.Log;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;


public class InitializeTestBased {

    protected WebDriver driver;
    protected Log logger;

    @BeforeTest
    public synchronized void beforeRun(){
        openBrowser();
        Log.setLog(null,null, ProjectConfiguration.getBrowser());
        logger = Log.getLog();
    }

    private void openBrowser(){
        ProjectConfigHolder config = ProjectConfiguration.getConfig();
        if(config==null){
            ProjectConfiguration.readPropertyData();
        }
        DriverFactory.createDriverInstance(ProjectConfiguration.getBrowser());
        driver = DriverFactory.getDriver();
    }

    @AfterTest
    public synchronized void tearDown(){
        logger.flush();
        driver.quit();
    }

}

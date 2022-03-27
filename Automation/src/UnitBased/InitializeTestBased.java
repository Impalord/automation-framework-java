package UnitBased;

import DataModel.ProjectConfigurationManage.ProjectConfigHolder;
import DataModel.ProjectConfigurationManage.ProjectConfiguration;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;


public class InitializeTestBased {

    protected WebDriver driver;

    @BeforeTest
    public synchronized void beforeRun(){
        openBrowser();
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
        driver.quit();
    }

}

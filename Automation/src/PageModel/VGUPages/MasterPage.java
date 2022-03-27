package PageModel.VGUPages;

import DataModel.ProjectConfigurationManage.ProjectConfiguration;
import Ultilities.WebActions;
import UnitBased.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Master of Page
 * @Technique: Page Object Model framework
* */

public class MasterPage extends WebActions {
    public MasterPage(){
        super();
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    @FindBy(xpath = "//img[@alt ='VGU LOGO']")
    WebElement VGULogo;


    public void verifyPageTitle(){
        waitPageLoaded();
        verifyElementIsVisible(VGULogo, "Vgu Logo");
    }

    public void accessToVGUPage(){
        navigateToUrl(ProjectConfiguration.getConfig().getUrl());
    }
}

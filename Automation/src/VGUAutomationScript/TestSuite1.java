package VGUAutomationScript;

import PageModel.PageObjectManager;
import Ultilities.OutputHandle.Reporter;
import org.testng.annotations.Test;

public class TestSuite1 extends PageObjectManager{

    @Test(description = "Access Page and Verify")
    public void testAccessPage(){
        Reporter.startStep("Go to VGU");
        masterPage = getMasterPage();
        masterPage.accessToVGUPage();
        masterPage.verifyPageTitle();
        Reporter.endStep();

        Reporter.startStep("Click close");
        masterPage.clickCloseBanner();
        masterPage.verifyPageTitle();
        Reporter.endStep();
    }
}

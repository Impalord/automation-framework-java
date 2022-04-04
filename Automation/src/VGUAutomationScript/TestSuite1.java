package VGUAutomationScript;

import PageModel.PageObjectManager;
import Ultilities.OutputHandle.ReportManagement;
import org.testng.annotations.Test;

public class TestSuite1 extends PageObjectManager{

    @Test(description = "Access Page and Verify")
    public void testAccessPage(){
        ReportManagement.startStep("Go to VGU");
        masterPage = getMasterPage();
        masterPage.accessToVGUPage();
        masterPage.verifyPageTitle();
        ReportManagement.endStep();

        ReportManagement.startStep("Click close");
        masterPage.clickCloseBanner();
        masterPage.verifyPageTitle();
        ReportManagement.endStep();
    }
}

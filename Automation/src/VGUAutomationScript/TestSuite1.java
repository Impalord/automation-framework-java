package VGUAutomationScript;

import PageModel.PageObjectManager;
import org.testng.annotations.Test;

public class TestSuite1 extends PageObjectManager{

    @Test(description = "Access Page and Verify")
    public void testAccessPage(){
        masterPage = getMasterPage();
        masterPage.accessToVGUPage();
        masterPage.verifyPageTitle();
    }
}

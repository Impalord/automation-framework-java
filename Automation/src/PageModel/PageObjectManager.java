package PageModel;

import PageModel.VGUPages.MasterPage;
import UnitBased.InitializeTestBased;

public class PageObjectManager extends InitializeTestBased {
    protected MasterPage masterPage;



    protected MasterPage getMasterPage(){
        masterPage = new MasterPage();
        return masterPage;
    }
}

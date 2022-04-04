package Ultilities.OutputHandle;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Then;
import org.testng.Assert;

/**
 * Behave as the log and final call for a report
* */

public class TestResult extends ReportManagement{
    public TestResult(){
    }

    public void setPassed(String info, String stepDescription){
        Log.getLog().info(info);
        scenarioStep.createNode(Then.class , info).pass(stepDescription);
        Assert.assertTrue(true, info);
    }

    public void assertStep(Boolean condition, String stepDescription){
        if(condition){
            Log.getLog().info(stepDescription);
        }
        else {
            Log.getLog().error(stepDescription, "");
        }
        Assert.assertTrue(condition, stepDescription);
    }

    public void setFailed(String info, Throwable e){
        Log.getLog().error(info, e.getMessage());
        scenarioStep.createNode(Then.class, info).fail(info + " " + e);
        Assert.fail(info + System.getProperty("line.seperator")+ e.getMessage());
    }
}

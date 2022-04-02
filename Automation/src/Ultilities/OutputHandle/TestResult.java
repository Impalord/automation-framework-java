package Ultilities.OutputHandle;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Then;
import org.testng.Assert;

public class TestResult extends Reporter{
    public TestResult(){
    }

    public void setPassed(String info, String stepDescription){
        Log.getLog().info(info);
        Assert.assertTrue(true, info);
        scenarioStep.createNode(Then.class , info).pass(stepDescription);
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
        Assert.fail(info + System.getProperty("line.seperator")+ e.getMessage());
        scenarioStep.createNode(Then.class, info).fail(info + " " + e);
    }
}

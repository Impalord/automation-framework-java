package Ultilities.OutputHandle;

import DataModel.GlobalVariable;
import Ultilities.JavaUtils.JavaUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.gherkin.model.*;

public class Reporter extends ReportManagement{
    private static int steps = 1;
    protected static ExtentTest scenarioStep;
    public Reporter(){}

    public Reporter(String id, String testName, String browser) {
        super(id, testName, browser);
    }

    public static void startStep(String info){
        scenarioStep = featureTest.createNode(Scenario.class, "Step "+steps+":" +info);
    }

    public static void endStep(){
        scenarioStep.createNode(Then.class, "End Step");
        steps++;
    }

    public static void reportEvent(String info){
        scenarioStep.createNode(Then.class, info).pass(info);
    }

    public static void captureScreen(){
        String path = GlobalVariable.SCREEN_PATH +"\\loggingPic" + JavaUtils.getCurrentDateTime() +".png";
        scenarioStep.createNode(When.class, JavaUtils.getCurrentDateTime()+".png").addScreenCaptureFromPath(path)
                .info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }
}

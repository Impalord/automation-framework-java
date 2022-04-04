package Ultilities.OutputHandle;

import DataModel.GlobalVariable;
import Ultilities.JavaUtils.JavaUtils;
import UnitBased.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description: Implement Extend Report with inheritance using Ghrekin-Style Report (BDD)
 *
 * */

public class ReportManagement {

    private static final ThreadLocal<ReportManagement> reportThread = new ThreadLocal<>();
    protected static ExtentReports reporter;
    private static int steps = 1;
    protected static ExtentTest featureTest;
    protected static ExtentTest scenarioStep;
    protected ReportManagement(String id, String testName, String browser){
        configReport(id +"_"+testName+"_"+browser);
    }

    protected ReportManagement(){};

    private static void configReport(String title){
        Path path = Paths.get(GlobalVariable.REPORT_PATH);
        reporter = new ExtentReports();
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            ExtentSparkReporter spark = new ExtentSparkReporter(GlobalVariable.REPORT_PATH + "\\Report.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle(title);
            spark.config().setReportName(title);
            spark.config().enableOfflineMode(true);

            reporter.attachReporter(spark);
        }
        catch (IOException error){
            error.printStackTrace();
        }finally {
            featureTest = reporter.createTest(Feature.class, title);
        }
    }


    public static void setReport(String id, String testName, String browser)
    {
        reportThread.set(new ReportManagement(id, testName, browser));
    }

    public static ReportManagement getReport(){
        return reportThread.get();
    }



    public static void startStep(String info){
        scenarioStep = featureTest.createNode(Scenario.class, "Step "+steps+":" +info);
    }

    public static void endStep(){
        steps++;
    }

    public void reportEvent(String info){
        scenarioStep.createNode(And.class, info);
    }

    public static void captureScreen() {
        try{
            if (!Files.exists(Paths.get(GlobalVariable.SCREEN_PATH))) {
                Files.createDirectories(Paths.get(GlobalVariable.SCREEN_PATH));
            }
        }catch (IOException error){
            error.printStackTrace();
        }finally {
            String screenshotPath = JavaUtils.captureScreenShot(GlobalVariable.SCREEN_PATH, DriverFactory.getDriver());
            scenarioStep.createNode(And.class, "Screenshot.png").addScreenCaptureFromPath(screenshotPath)
                    .info(MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
        }
    }


    public void flushReport() {
        reporter.flush();
    }

}

package Ultilities.OutputHandle;

import DataModel.GlobalVariable;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ReportManagement {

    private static final ThreadLocal<ReportManagement> reportThread = new ThreadLocal<>();
    protected static ExtentTest featureTest;
    protected static ExtentReports reporter;
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
        reportThread.set(new Reporter(id, testName, browser));
    }

    public static ReportManagement getReport(){
        return reportThread.get();
    }

    public void flushReport() {
        reporter.flush();
    }

    public void captureScreenOnFail(){
        String path = GlobalVariable.SCREEN_PATH +"\\failed.png";
        featureTest.addScreenCaptureFromPath(path)
                .info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }
}

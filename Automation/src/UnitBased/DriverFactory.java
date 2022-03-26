package UnitBased;

import DataModel.GlobalVariable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    //Using thread local to handle multiple conflict threading
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    /** @Author: Nguyen Toan Thang
    * @Description: Factory Design Pattern to initialize desired browser driver from local file and set it into var: driverThread
    * */


    public static void createDriverInstance(String browserName){
        switch (browserName.toUpperCase()) {
            case GlobalVariable.CHROME -> {
                System.setProperty("webdriver.chrome.driver", GlobalVariable.DRIVER_FOLDER + "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("disable-infobars");
                driverThread.set(new ChromeDriver(chromeOptions));
            }
            case GlobalVariable.FF -> {
                System.setProperty("webdriver.gecko.driver", GlobalVariable.DRIVER_FOLDER + "geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                driverThread.set(new FirefoxDriver(firefoxOptions));
            }
            default -> throw new IllegalStateException("Not configuration" + browserName);
        }
        //From here trying to sleep 5 second after open up and end thread if any errors. Then finally make it fullscreen
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
           Thread.currentThread().interrupt();
        }
        finally {
            driverThread.get().manage().window().fullscreen();
            driverThread.get().manage().window().maximize();
            ((JavascriptExecutor) driverThread.get()).executeScript("window.focus();");
        }
    }



    public static WebDriver getDriver(){
        return driverThread.get();
    }

}

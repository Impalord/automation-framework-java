package Ultilities;

import UnitBased.DriverFactory;
import UnitBased.InitializeTestBased;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v97.runtime.model.ExceptionDetails;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

/**
 * @Description: WebActions Class will contain actions which perform on a page, this will use generated driver
 from previous InitializeTestBased.
 *
 * Four Section will be created: Navigation, Actions, Verification, Wait
* */

public class WebActions extends InitializeTestBased {
    protected WebDriverWait defaultWait;
    protected WebDriver driver;

    public WebActions(){
        super();
        driver = DriverFactory.getDriver();
        defaultWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }


    /**
     * @Description: Find Element method and return WebElement
    * */
    protected  WebElement findElement(By selector){
        WebElement element = null;
        try{
           for(int i = 0; i <= 30; i++){
               element = driver.findElement(selector);
           }
        }
        catch (Exception e){
            Assert.fail(e.getMessage());
            System.out.println("Could not find such element");
        }
        return element;
    }


    /**@Purpose: To Navigation to another fixed link or Managing number of windows appears
     * Navigation Section
     *
    * */

    protected void navigateToUrl(String url){
        try{
            driver.navigate().to(url);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }


    /**@Purpose: To verify that an element/text/button is/isNot visible
     *
     * Verification
     */

    protected <T> void verifyElementIsVisible(T elementAttribute, String name){
        WebElement element = null;
        try{
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
            }else {
                 element = (WebElement) elementAttribute;
            }
            Assert.assertTrue(element.isDisplayed(), "Element"+ name +" was visible");
        }catch(Exception e){
            Assert.fail(e.getMessage());
            System.out.println("Element was not displayed" + name);
        }
    }

    /**
    *   Wait Methods Section
     * @Author: Nguyen Toan Thang
     * @Description: Methods to wait for a specific page/element to be displayed
    * */

    protected boolean waitPageLoaded(){
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    return true;
                }
            }
        };
        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
        return defaultWait.until(jQueryLoad) && defaultWait.until(jsLoad);
    }


}

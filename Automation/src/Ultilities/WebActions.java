package Ultilities;

import Ultilities.OutputHandle.Log;
import UnitBased.DriverFactory;
import UnitBased.InitializeTestBased;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
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
    protected Log logger;

    public WebActions(){
        super();
        driver = DriverFactory.getDriver();
        defaultWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        logger = Log.getLog();
    }


    /**
     * @Description: Find Element method and return WebElement
     *  Find up to 30 times until no element appear
    * */
    protected  WebElement findElement(By selector){
        WebElement element = null;
        for(int i = 0; i <= 30; i++) {
            try {
                element = driver.findElement(selector);
            } catch (Exception e) {
                Assert.fail(e.getMessage());
                logger.fatal("Could not find such element");
            }
        }
        return element;
    }

    /**@Purpose: To perform certain actions like: Click, Input, MultipleClick
     * @Author: Nguyen Toan Thang
     * Actions Sections
    * */

    protected <T> void click(T elementAttribute, String name){
        WebElement element;
        try{
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
            }else {
                element = (WebElement) elementAttribute;
            }
            waitUntilElementClickable(elementAttribute, name);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            logger.info("Clicked: " + name);
            Thread.sleep(3000);
        }
        catch(ElementNotInteractableException |TimeoutException | InterruptedException  error){
            Assert.fail(error.getMessage());
            logger.fatal("Element "+name+" is not interactable");
        }
    }


    protected <T> void hoverIntoElement(T elementAttribute, String name){
        WebElement element;
        try {
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
            }else {
                element = (WebElement) elementAttribute;
            }
            waitUntilElementDisplay(elementAttribute, name);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).build().perform();
            logger.info("Move and hover to: " + name);
            Thread.sleep(3000);
        }catch (TimeoutException | InterruptedException  error){
            Assert.fail(error.getMessage());
            logger.fatal("Element "+name+" is not appear on "+driver.getTitle()+" page");
        }
    }

    protected <T> void copyToClipBoard(T elementAttribute){
        WebElement element;
        try {
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
            }else {
                element = (WebElement) elementAttribute;
            }
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click();
            Thread.sleep(2000);
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
            logger.info("Pressed: Ctrl + A");
            Thread.sleep(2000);
            actions.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).build().perform();
            logger.info("Copied text: " + getClipBoard());
        }catch (TimeoutException | InterruptedException  error){
            Assert.fail(error.getMessage());
            logger.fatal("Element is not interactable on "+driver.getTitle()+" page");
        }
    }

    protected <T> void paste(T elementAttribute) {
        WebElement element;
        try {
            if (elementAttribute.getClass().getName().contains("By")) {
                element = driver.findElement((By) elementAttribute);
            } else {
                element = (WebElement) elementAttribute;
            }
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click();
            Thread.sleep(2000);
            actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();
            logger.info("Pasted text: " + getClipBoard());
        } catch (TimeoutException | InterruptedException error) {
            Assert.fail(error.getMessage());
            logger.fatal("Element is not interactable on " + driver.getTitle() + " page");
        }
    }

    protected <T> void rightClick(T elementAttribute, String name){
        WebElement element;
        try{
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
            }else {
                element = (WebElement) elementAttribute;
            }
            waitUntilElementClickable(elementAttribute, name);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).contextClick().perform();
            System.out.println("Clicked: " + name);
            Thread.sleep(3000);
        }
        catch(ElementNotInteractableException |TimeoutException | InterruptedException  error){
            Assert.fail(error.getMessage());
            logger.fatal("Element "+name+" is not interactable");
        }
    }





    /**@Purpose: To Navigation to another fixed link or Managing number of windows appears
     * Navigation Section
    * */

    protected void navigateToUrl(String url){
        try{
            driver.navigate().to(url);
            logger.info("Navigated to: " +url );
        }catch (Exception e){
            Assert.fail(e.getMessage());
            logger.fatal("Can not navigate to: "+ url);
        }
    }

    protected <T> void switchToFrame(T frameAttribute){
        WebElement iFrame;
        try{
            if(frameAttribute.getClass().getName().contains("By")){
                iFrame  = driver.findElement((By)frameAttribute);
            }else{
                iFrame = (WebElement)frameAttribute;
            }
            Thread.sleep(2000);
            driver.switchTo().frame(iFrame);
            System.out.println("Switched to frame");
        }catch (InterruptedException | TimeoutException | ElementNotInteractableException error){
            Assert.fail(error.getMessage());
            System.out.println("Could not switch to frame");
        }
    }


    /**@Purpose: To verify that an element/text/button is/isNot visible
     *
     * Verification
     */

    protected <T> void verifyElementIsVisible(T elementAttribute, String name){
        WebElement element;
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
        ExpectedCondition<Boolean> jQueryLoad = driver ->{
            try {
                return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                return true;
            }
        };
        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .toString().equals("complete");
        return defaultWait.until(jQueryLoad) && defaultWait.until(jsLoad);
    }

    protected <T> boolean waitUntilElementDisplay(T elementAttribute, String name){
        WebElement element;
        try{
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
                defaultWait.until(ExpectedConditions.visibilityOf(element));
            } else{
              element = (WebElement) elementAttribute;
              defaultWait.until(ExpectedConditions.visibilityOf(element));
            }
            logger.info("Element "+name+" is display on "+driver.getTitle()+" page");
            return true;
        }catch (TimeoutException | ElementNotVisibleException e){
            Assert.fail(e.getMessage());
            logger.fatal("No such element present");
        }
        return false;
    }

    protected <T> boolean waitUntilElementClickable(T elementAttribute, String name){
        WebElement element;
        try{
            if(elementAttribute.getClass().getName().contains("By")){
                element = driver.findElement((By) elementAttribute);
                defaultWait.until(ExpectedConditions.elementToBeClickable(element));
            } else{
                element = (WebElement) elementAttribute;
                defaultWait.until(ExpectedConditions.elementToBeClickable(element));
            }
            logger.info("Element "+name+" is Clickable");
            return true;
        }catch (ElementNotInteractableException | TimeoutException e){
            Assert.fail(e.getMessage());
            logger.fatal("No such element clickable");
        }
        return false;
    }

    /**
     * Windows Utilities
    * */
    public String getClipBoard(){
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (HeadlessException | IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }
        finally {
            logger.error("No such text in clipboard ");
        }
        return "";
    }
}

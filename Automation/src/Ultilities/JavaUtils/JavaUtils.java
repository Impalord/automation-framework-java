package Ultilities.JavaUtils;

import DataModel.GlobalVariable;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class JavaUtils {
    public static String getCurrentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("M-d_HHmmss");
        Date date = new Date();
        return formatter.format(date);
    }



    /**
     * @Description: Capture Screen then encoded to based64 due to normal screenshot can not at to report
    * */
    public static String captureScreenShot(String path, WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String encodedBase64 = null;
        String destination = GlobalVariable.SCREEN_PATH +JavaUtils.getCurrentDateTime()+".png";
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException error){
            error.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(destination);
            byte[] bytes =new byte[(int)finalDestination.length()];
            fileInputStream.read(bytes);
            encodedBase64 = Base64.getEncoder().encodeToString(bytes);

        }catch (IOException e){
            e.printStackTrace();
        }
        return encodedBase64;
    }
}

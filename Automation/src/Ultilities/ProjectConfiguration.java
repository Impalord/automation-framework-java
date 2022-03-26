package Ultilities;

import DataModel.GlobalVariable;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @Author: Nguyen Toan Thang
 * @Description: This following technique call Keyword-Driven Framework.
 * @Purpose: To read property file and get keyword to get data from external property file
 */

public class ProjectConfiguration {

    private static final ThreadLocal<ProjectConfigHolder> threadConfig = new ThreadLocal<>();

    private static final ProjectConfigHolder config = new ProjectConfigHolder();


    public static void readPropertyData(){
        Properties props = new Properties();
        try (InputStream inputPropsFile = new FileInputStream(GlobalVariable.DATA_FOLDER+"data.property")
        ){
            props.load(inputPropsFile);
            config.setBrowser(props.getProperty("browser"));
            config.setUrl(props.getProperty("url"));
            threadConfig.set(config);
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
            System.out.println("Could not read file");
        }
        finally {
            //Can not detect the system environment we make it default
            config.setBrowser("CHROME");
            config.setUrl("https://vgu.edu.vn/");
        }
    }

    public static ProjectConfigHolder getConfig(){
        return threadConfig.get();
    }

    public static String getBrowser(){
        return config.getBrowser();
    }
}

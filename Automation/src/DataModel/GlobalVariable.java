package DataModel;

public class GlobalVariable {
    /**@Author: Nguyen Toan Thang
     * @Description: This is the part of Data-Driven Framework, Global variable will get the system
     * path file, thus, for the sustainable, reusable, lowest maintainable cost for CI/CD implementation.
     *
     * @Important: All the paths, system variables, filename, foldername should be changed in THIS CLASS
     *
     * for example: I want to use the same setup for remote machine and the code would not change any after
     * got pull and build for the new system.
    * */


    /*Browser Variable*/
    public static final String CHROME = "CHROME";
    public static final String FF = "FIREFOX";
    public static final String EDGE = "MSEDGE";


    /*System variables*/
    public static final String ENV_VAR = "VGU_AUTOMATION";
    public static final String PROJECT_PATH = System.getenv(ENV_VAR);

    /*Project Folder Path*/
    public static final String DRIVER_FOLDER = PROJECT_PATH + "\\driver\\";
    public static final String DATA_FOLDER = PROJECT_PATH + "\\data\\";

    /*Credential Data Path*/
    public static final String CREDENTIAL_DATA = PROJECT_PATH + "\\data\\"+"userdata.property";


    /*Output File Path*/
    public static final String LOGGER_PATH = PROJECT_PATH + "\\Output\\Log\\";
    public static final String REPORT_PATH = PROJECT_PATH + "\\Output\\Report\\";


}


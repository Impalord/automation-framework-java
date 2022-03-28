package Ultilities.OutputHandle;

import DataModel.GlobalVariable;
import org.apache.log4j.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Log {
    private static final ThreadLocal<Log> logThread = new ThreadLocal<>();
    private Logger log4J;
    private PrintWriter logWriter;


    public Log(String ID, String methodName, String browserName){
        //Create Log4j File for Console Logging
        configLog4JProperty();
        log4J.info("!!![Start the execution]!!! " + System.getProperty("line.seperator") + methodName + " " +browserName);
        createLogFile(ID, methodName, browserName);
        logWriter.write("!!![Start the execution]!!!" + System.getProperty("line.seperator") + methodName +" on "+browserName + System.getProperty("line.separator"));

    };

    /**
     * Logging Methods
    * */

    public void info(String message){
        logWriter.write("Info: " + message + System.getProperty("line.separator"));
        log4J.info(message);
    }

    public void error(String message){
        logWriter.write("Error: "+ message +System.getProperty("line.separator"));
        log4J.error(message);
    }

    public void fatal(String message){
        logWriter.write("Fatal: " + message + System.getProperty("line.separator"));
        log4J.fatal(message);
    }

    public void warning(String message){
        logWriter.write("Warning: " + message + System.getProperty("line.separator"));
        log4J.warn(message);
    }


    /**
     * Create an external .txt file then use PrintWriter behavior as logger for outputFile
     */
    private void createLogFile(String ID, String methodName, String currentBrowser) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M-d_HHmmss");
        Path path = Paths.get(GlobalVariable.LOGGER_PATH);
        try{
            logWriter = new PrintWriter(path +"\\Run_"+ ID +"_"+methodName+"_"+currentBrowser+"_"+dateFormat.format(Calendar.getInstance().getTime())+".txt");
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    /**
     * Flush the log framework after ending test case
    * */
    public void flush(){
        logWriter.write("!!![Ending the execution]!!!");
        log4J.info("!!![Ending the execution]!!!");
        logWriter.flush();
    }


    /**
     * Config Log4j Property without external property file
     * @Source: Literally on log4j document how to do this
    * */
    private void configLog4JProperty(){
        //Set Pattern Through Builder
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-7p %d [%t] %x - %m%n";
        layout.setConversionPattern(conversionPattern);

        /*
         * Logging in console
         * Level: All
         * Flush after run: True
         * Behavior: System.out
        */
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setThreshold(Level.ALL);
        consoleAppender.setImmediateFlush(true);
        consoleAppender.setTarget("System.out");
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();

        /*
         * Logging into file to folder Output
         * Level: All
         * Flush after run: True
         * Behavior: Writer
         */
        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setFile("Logger.txt");
        fileAppender.setLayout(layout);
        fileAppender.setThreshold(Level.ALL);
        fileAppender.setMaxFileSize("5MB");
        fileAppender.setMaxBackupIndex(5);
        fileAppender.activateOptions();

        // configures the root logger
        log4J = Logger.getLogger(Log.class);
        log4J.setLevel(Level.ALL);
        log4J.addAppender(consoleAppender);
        log4J.addAppender(fileAppender);
    }


    public  static void setLog(String ID, String methodName, String browserName){
        logThread.set(new Log(ID, methodName,browserName));
    }

    public static Log getLog(){
        return logThread.get();
    }
}

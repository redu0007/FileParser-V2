package com.waynaut.log;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Rahman Md Redwanur
 */

public class Log4J {

    private final Logger logger;
    
    /**
     * Initialization of Log4J
     * @param sClassName main class name
     * @param sPath path to save log of this application
     * @param sLevel ALL, DEBUG, ERROR, FATAL, INFO,OFF, TRACE, TRACE_INT, WARN
     * ALL <p> The ALL has the lowest possible rank and is intended to turn on all logging.
     * DEBUG <p> The DEBUG Level designates fine-grained informational events that are most useful to debug an application.
     * ERROR <p> The ERROR level designates error events that might still allow the application to continue running.
     * FATAL <p> The FATAL level designates very severe error events that will presumably lead the application to abort.
     * INFO  <p> The INFO level designates informational messages that highlight the progress of the application at coarse-grained level.
     * OFF  <p> The OFF has the highest possible rank and is intended to turn off logging.
     * TRACE  <p> The TRACE Level designates finer-grained informational events than the DEBUG
     * TRACE_INT  <p> TRACE level integer value.
     * WARN  <p> The WARN level designates potentially harmful situations.
     */
    public Log4J(String sClassName,String sPath,String sLevel) {
        
        logger = Logger.getLogger(sClassName);
        
        Properties log4jProperties = new Properties();
        
        log4jProperties.setProperty("log4j.rootLogger", sLevel+", FILE");
        log4jProperties.setProperty("log4j.appender.FILE","org.apache.log4j.DailyRollingFileAppender");
        log4jProperties.setProperty("log4j.appender.FILE.DatePattern","'.'yyyy-MM-dd");
        log4jProperties.setProperty("log4j.appender.FILE.File",sPath);
        log4jProperties.setProperty("log4j.appender.FILE.ImmediateFlush","true");
        //log4jProperties.setProperty("log4j.appender.FILE.Threshold","debug");
        log4jProperties.setProperty("log4j.appender.FILE.Append","true");
        log4jProperties.setProperty("log4j.appender.FILE.layout","org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.FILE.layout.conversionPattern","%d{yyyy-MM-dd@HH:mm:ss} %-5p (%13F:%L) %3x - %m%n");
        
        PropertyConfigurator.configure(log4jProperties);
    }
    
    public Logger getLogger(){
        return logger;   
    }
}


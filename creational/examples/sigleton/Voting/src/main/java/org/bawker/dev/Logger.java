package org.bawker.dev;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.TimeZone;

public class Logger {

    private static Logger instance;
    private String logFileName;
    private Level logLevel;

    public enum Level {
        INFO, WARNING, ERROR
    }

    private Logger() {
        // private constructor to prevent direct instantiation
        logFileName = "application.log";
        logLevel = Level.INFO;
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void setLogFileName(String fileName) {
        logFileName = fileName;
    }

    public void setLogLevel(Level level) {
        logLevel = level;
    }

    public void log(Level level, String message) {
        if (level.ordinal() >= logLevel.ordinal()) {
            String logMessage = String.format("[%s] %s: %s\n", getCurrentTimeStamp(), level.name(), message);
            try {
                FileWriter fileWriter = new FileWriter(logFileName, true);
                fileWriter.write(logMessage);
                fileWriter.close();

            } catch (IOException e) {
                Logger.getInstance().logError(e.getMessage());
            }
        }
    }

    public void logInfo(String message) {
        log(Level.INFO, message);
    }

    public void logWarning(String message) {
        log(Level.WARNING, message);
    }

    public void logError(String message) {
        log(Level.ERROR, message);
    }

    private String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date now = new Date();
        return sdfDate.format(now);
    }

}
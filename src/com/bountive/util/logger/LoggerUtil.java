package com.bountive.util.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.bountive.start.Info;
import com.bountive.util.resource.ResourceLocation;

public class LoggerUtil implements Thread.UncaughtExceptionHandler {

	private static final Logger consoleLogger = Logger.getLogger(LoggerUtil.class);
	private static final ResourceLocation LOG_DIR = new ResourceLocation(ResourceLocation.APPDATA_DIRECTORY, "/logs");
	private static final String s = System.lineSeparator();
	
	private static final String DATE_FORMAT = "dd_MM_yyyy HH_mm_ss";
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logError(t, e);
	}
	
	/**
	 * Should be called when an uncaught exception is thrown or a caught exception is thrown.
	 * @param c: The class where the error is coming from.
	 * @param t: The thread the error occurred on.
	 * @param e: The throwable, or actual error itself.
	 */
	public static void logError(Thread t, Throwable e) {
		String date = getDate(DATE_FORMAT);
		String message = s + "---------- " + Info.NAME + " Error Report ----------" + s + s + date
				+ s + s + "Uh oh, I'm going down. Save yourself!" + s + s
				+ "Details about the crash are listed below" + s
				+ "---------------------------------------" + s + s +
				"Crash occured in class: " + t.getName();
		
		consoleLogger.error(message, e);
		logToErrorFile(date, message, e);
	}
	
	public static void logWarn(Throwable e, String warnMessage, boolean logToFile) {
		logWarn(Thread.currentThread(), e, warnMessage, logToFile);
	}
	
	public static void logWarn(Thread t, Throwable e, String warnMessage, boolean logToFile) {
		String date = getDate(DATE_FORMAT);
		String message = s + "---------- " + Info.NAME + " Warning Report ----------" + s + s + date
				+ s + s + "Keep calm! I can get back up from this!" + s + s
				+ "Details about the crash are listed below" + s
				+ "---------------------------------------" + s + s +
				"Crash occured in class: " + t.getName() + " | " + warnMessage;
		
		consoleLogger.warn(message, e);
		
		if (logToFile)
			logToErrorFile(date, message, e);
	}
	
	public static void logWarn(String warnMessage) {
		logWarn(Thread.currentThread(), warnMessage);
	}
	
	public static void logWarn(Thread t, String warnMessage) {
		consoleLogger.warn("Warning coming from class: " + t.getName() + " | " + warnMessage);
	}
	
	private static void logToErrorFile(String date, String message, Throwable e) {
		File f = new File(LOG_DIR.getFullPath());
		boolean append = false;
		if (f.exists()) {
			append = true;
		}
		else {
			f.mkdirs();
		}
		
		ResourceLocation fileName = new ResourceLocation(LOG_DIR.getFullPath(), "/log_report_" + date + ".txt");
		
		try (PrintStream writer = new PrintStream(new FileOutputStream(fileName.getFullPath(), append))) {
			writer.println(message + s);
			writer.println(e.getClass() + ": " + e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
                writer.println("\t" + e.getStackTrace()[i].toString());
            }
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private static String getDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
}

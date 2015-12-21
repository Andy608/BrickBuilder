package com.bountive.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.bountive.resource.ResourceLocation;
import com.bountive.start.Info;

public class ErrorFileLogger implements Thread.UncaughtExceptionHandler {

	private static final Logger consoleLogger = Logger.getLogger(ErrorFileLogger.class);
	private static final ResourceLocation ERROR_LOG_DIR = new ResourceLocation("/log");
	private static final String s = System.lineSeparator();
	
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
//	public static void logError(Thread t, Throwable e) {
//		String date = Util.getDate("dd MM yyyy HH mm ss");
//		String message = s + "---------- Sandbox Error Report ----------" + s + s + date
//				+ s + s +"Uh oh, I'm going down. Save yourself!" + s + s
//				+ "Details about the crash is listed below" + s
//				+ "---------------------------------------" + s + s +
//				"Crash occured in class: " + t.getName();
//		
//		consoleLogger.error(message, e);
//		new File(ERROR_LOG_DIR.getFullPath()).mkdirs();
//		
//		ResourceLocation fileName = new ResourceLocation(ERROR_LOG_DIR.getFullPath(), "/error_report_" + date + ".txt");
//		
//		try (PrintStream writer = new PrintStream(fileName.getFullPath(), "UTF-8")) {
//			writer.println(message + s);
//			writer.println(e.getClass() + ": " + e.getMessage());
//			for (int i = 0; i < e.getStackTrace().length; i++) {
//                writer.println("\t" + e.getStackTrace()[i].toString());
//            }
//		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
//			ex.printStackTrace();
//		}
//	}
	public static void logError(Thread t, Throwable e) {
		String date = Util.getDate("dd MM yyyy HH mm ss");
		String message = s + "---------- " + Info.NAME + " Error Report ----------" + s + s + date
				+ s + s +"Uh oh, I'm going down. Save yourself!" + s + s
				+ "Details about the crash is listed below" + s
				+ "---------------------------------------" + s + s +
				"Crash occured in class: " + t.getName();
		
		consoleLogger.error(message, e);
		File f = new File(ERROR_LOG_DIR.getFullPath());
		boolean append = false;
		if (f.exists()) {
			append = true;
		}
		else {
			f.mkdirs();
		}
		
		ResourceLocation fileName = new ResourceLocation(ERROR_LOG_DIR.getFullPath(), "/error_report_" + date + ".txt");
		
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
	
	public static void logWarn(Class<?> c, String warningMessage) {
		String date = Util.getDate("dd MM yyyy HH mm ss");
		consoleLogger.warn(warningMessage);
		
		new File(ERROR_LOG_DIR.getFullPath()).mkdirs();
		ResourceLocation fileName = new ResourceLocation(ERROR_LOG_DIR.getFullPath(), "/error_report_" + date + ".txt");
		
		try (PrintStream writer = new PrintStream(fileName.getFullPath(), "UTF-8")) {
			writer.println(c.getSimpleName() + ": " + warningMessage);
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}
}

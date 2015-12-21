package com.bountive.start;

import org.apache.log4j.PropertyConfigurator;

import com.bountive.util.ErrorFileLogger;

public class Main {

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		Thread.setDefaultUncaughtExceptionHandler(new ErrorFileLogger());
		new BrickBuilder().run();
	}
}

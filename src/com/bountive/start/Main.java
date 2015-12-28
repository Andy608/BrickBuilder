package com.bountive.start;

import org.apache.log4j.PropertyConfigurator;

import com.bountive.util.logger.LoggerUtils;

public class Main {

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		Thread.setDefaultUncaughtExceptionHandler(new LoggerUtils());
		new BrickBuilder().run();
	}
}

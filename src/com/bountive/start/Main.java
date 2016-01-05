package com.bountive.start;

import java.io.File;

import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.ResourceLocation;

public class Main {

	public static void main(String[] args) {
		ResourceLocation nativesLocation = new ResourceLocation("C:/Program Files (x86)/" + Info.NAME + "/natives", "", false);
		System.setProperty("org.lwjgl.librarypath", new File(nativesLocation.getFullPath()).getAbsolutePath());
		System.out.println(System.getProperty("org.lwjgl.librarypath"));
		LoggerUtil.init();
		Thread.setDefaultUncaughtExceptionHandler(LoggerUtil.getInstance());
		new BrickBuilder().run();
	}
}

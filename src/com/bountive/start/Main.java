package com.bountive.start;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.bountive.util.FileUtil;
import com.bountive.util.logger.LoggerUtil;

public class Main {

	public static void main(String[] args) {
		String path = (new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParentFile().getPath();
		String decodedPath;
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			System.setProperty("org.lwjgl.librarypath", decodedPath + FileUtil.getFileSeparator(false));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println(System.getProperty("org.lwjgl.librarypath"));
		LoggerUtil.init();
		Thread.setDefaultUncaughtExceptionHandler(LoggerUtil.getInstance());
		new BrickBuilder().run();
	}
}

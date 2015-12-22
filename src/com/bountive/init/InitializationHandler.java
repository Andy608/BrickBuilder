package com.bountive.init;

import com.bountive.display.Window;
import com.bountive.model.util.ModelBuilder;
import com.bountive.model.util.ModelManager;

public class InitializationHandler {

	public static void init() {
		Window.init();
		ModelManager.init();
		ModelBuilder.init();
	}
	
	public static void release() {
		Window.release();
		ModelManager.manager.release();
	}
}

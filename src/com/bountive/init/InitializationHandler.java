package com.bountive.init;

import com.bountive.display.Window;
import com.bountive.graphics.model.util.ModelBuilder;
import com.bountive.graphics.model.util.ModelManager;

public class InitializationHandler {

	public static void init() {
		ModelManager.init();
		ModelBuilder.init();
		Window.init();
	}
	
	public static void release() {
		Window.release();
		ModelManager.getManager().release();
	}
}

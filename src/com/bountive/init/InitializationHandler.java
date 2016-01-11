package com.bountive.init;

import com.bountive.display.Window;
import com.bountive.graphics.model.util.ModelBuilder;
import com.bountive.graphics.model.util.ModelManager;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.setting.ControlOptions;
import com.bountive.world.brick.Bricks;

public class InitializationHandler {

	public static void init() {
		Window.init();
		ModelBuilder.init();
		ModelManager.init();
		Window.createWindow();
		
		//TODO:Initialize this when loading up the world.
		Bricks.registerBricks();
		CameraMatrixManager.init();
		ControlOptions.setPaused(false);
		///////////////////////////////////////
	}
	
	public static void release() {
		Window.release();
		ModelManager.getManager().release();
	}
}

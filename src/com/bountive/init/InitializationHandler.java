package com.bountive.init;

import com.bountive.display.Window;
import com.bountive.graphics.model.util.ModelBrickList;
import com.bountive.graphics.model.util.ModelBuilder;
import com.bountive.graphics.model.util.ModelResourceManager;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.setting.ControlOptions;
import com.bountive.world.brick.material.BrickMaterials;
import com.bountive.world.brick.model.BrickModels;

public class InitializationHandler {

	public static void init() {
		Window.init();
		ModelBuilder.init();
		ModelResourceManager.init();
		ModelBrickList.loadModels();
		
		////TODO: MOVE SOMEWHERE ELSE
		BrickModels.registerBrickModels();
		BrickMaterials.registerBrickMaterials();
		////////////////////////////
		
		Window.initWindowHints();
		Window.buildScreen();
		
		//TODO:Initialize this when loading up the world.
		CameraMatrixManager.init();
		ControlOptions.setPaused(false);
		///////////////////////////////////////
		
	}
	
	public static void release() {
		Window.release();
		ModelResourceManager.getManager().release();
	}
}

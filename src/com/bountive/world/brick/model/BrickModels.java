package com.bountive.world.brick.model;

import com.bountive.util.logger.LoggerUtil;
import com.bountive.world.brick.material.BrickMaterials;

public class BrickModels {

	public static BrickStandardModel FLAT_1x1;
	public static BrickStandardModel FULL_1x1;
	
	public static void registerBrickModels() {
		LoggerUtil.logInfo(BrickMaterials.class, "Registering Brick Models...");
//		BrickMaterialRegistry.registerMaterial(stone);
		FLAT_1x1 = new BrickFlat1x1Model(0);
		FULL_1x1 = new BrickFull1x1Model(1);
	}
}

package com.bountive.world.brick.material;

import com.bountive.util.logger.LoggerUtil;

public class BrickMaterials {

	public static final BrickMaterial rock = new BrickRockMaterial(0);
	
	public static void registerBrickMaterials() {
		LoggerUtil.logInfo(BrickMaterials.class, "Registering Brick Materials...");
//		BrickMaterialRegistry.registerMaterial(stone);
	}
}

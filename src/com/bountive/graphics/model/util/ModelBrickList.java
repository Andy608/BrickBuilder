package com.bountive.graphics.model.util;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.util.resource.ResourceLocation;

public class ModelBrickList {

	public static ModelMesh FULL_1X1_BRICK;
	public static ModelMesh FLAT_1X1_BRICK;
	
	public static void createModels() {
		//TEMPORARY 
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().build2DColorRecModel(new float[] {-1, 1, -1, -1, 1, -1, 1, 1}, new float[] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
//		FLAT_1X1_BRICK = ModelBuilder.getBuilder().build3DColorModel(positions, indices, colors);
		
//		FLAT_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "test.obj"));
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "full_1x1_brick.obj"));
		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "1x1_brick_ultra_setting.obj", true));
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "texture_test.obj"));
		FLAT_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "dragon.obj", true));
	}
}

package com.bountive.graphics.model.util;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.util.resource.FileResourceLocation;
import com.bountive.util.resource.FileResourceLocation.EnumFileExtension;
import com.bountive.util.resource.ResourceDirectory;

public class ModelBrickList {

	private static final ResourceDirectory brickDirectory = new ResourceDirectory("res/models", "bricks", true);
	
	
	public static ModelMesh REGULAR_1X1_BRICK;
	public static ModelMesh FLAT_1X1_BRICK;
	
	public static void loadModels() {
		//TEMPORARY 
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().build2DColorRecModel(new float[] {-1, 1, -1, -1, 1, -1, 1, 1}, new float[] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
//		FLAT_1X1_BRICK = ModelBuilder.getBuilder().build3DColorModel(positions, indices, colors);
		
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "test", EnumFileExtension.OBJ));
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "unit_cube", EnumFileExtension.OBJ));
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "full_1x1_brick.obj"));
		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "1x1_brick_ultra_setting", EnumFileExtension.OBJ));
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "texture_test.obj"));
		FLAT_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "dragon", EnumFileExtension.OBJ));
	}
}

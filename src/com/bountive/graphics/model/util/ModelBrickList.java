package com.bountive.graphics.model.util;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.world.brick.component.BrickComponents;

public class ModelBrickList {

//	private static final ResourceDirectory brickDirectory = new ResourceDirectory("res/models", "bricks", true);
	
	public static ModelMesh REGULAR_1X1_BRICK;
	public static ModelMesh FLAT_1X1_BRICK;
	
	public static void loadModels() {
		BrickComponents.loadBrickComponents();
		//TEMPORARY 
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().build2DColorRecModel(new float[] {-1, 1, -1, -1, 1, -1, 1, 1}, new float[] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
//		FLAT_1X1_BRICK = ModelBuilder.getBuilder().build3DColorModel(positions, indices, colors);
		
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "test", EnumFileExtension.OBJ));
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "unit_cube", EnumFileExtension.OBJ));
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "full_1x1_brick.obj"));
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "1x1_brick_ultra_setting", EnumFileExtension.OBJ));
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "cube_test", EnumFileExtension.OBJ));
//		FULL_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new ResourceLocation("res/models/bricks", "texture_test.obj"));
//		REGULAR_1X1_BRICK = ModelBuilder.getBuilder().buildModelFromFile(new FileResourceLocation(brickDirectory, "dragon", EnumFileExtension.OBJ));
	}
}

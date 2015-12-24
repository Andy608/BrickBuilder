package com.bountive.graphics.model;

import com.bountive.graphics.model.util.ModelBuilder;

public class ModelBrickList {

	public static ModelMesh FULL_1X1_BRICK;
	
	public static void createModels() {
		FULL_1X1_BRICK = ModelBuilder.getBuilder().build2DColorRecModel(new float[] {-1, 1, -1, -1, 1, -1, 1, 1}, new float[] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
	}
}

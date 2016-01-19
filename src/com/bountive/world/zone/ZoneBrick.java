package com.bountive.world.zone;

import com.bountive.world.brick.material.BrickMaterial;
import com.bountive.world.brick.material.BrickMaterials;
import com.bountive.world.brick.material.EnumBrickColor;
import com.bountive.world.brick.model.BrickModels;
import com.bountive.world.brick.model.BrickStandardModel;

public class ZoneBrick {

	private BrickMaterial brickMaterial;
	private BrickStandardModel brickModel;
	private EnumBrickColor color;
	
	public static final ZoneBrick stoneBrick = new ZoneBrick(BrickMaterials.rock, BrickModels.FLAT_1x1, 0);
	
	private ZoneBrick(BrickMaterial material, BrickStandardModel model, int colorIndex) {
		brickMaterial = material;
		brickModel = model;
		color = material.getValidColors()[colorIndex];
	}
	
	public BrickMaterial getMaterial() {
		return brickMaterial;
	}
	
	public BrickStandardModel getModel() {
		return brickModel;
	}
	
	public EnumBrickColor getColor() {
		return color;
	}
}

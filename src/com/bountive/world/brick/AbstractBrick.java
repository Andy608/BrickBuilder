package com.bountive.world.brick;

import com.bountive.world.brick.helper.EnumBrickColor;
import com.bountive.world.brick.helper.EnumBrickModel;

public abstract class AbstractBrick {

	private int materialID;
	private EnumBrickColor colorID;
	private EnumBrickModel modelID;
	
	public AbstractBrick(int materialID, EnumBrickColor colorID, EnumBrickModel modelID) {
		this.materialID = materialID;
		this.colorID = colorID;
		this.modelID = modelID;
	}
	
	public final int getMaterialID() {
		return materialID;
	}
	
	public final EnumBrickColor getColorID() {
		return colorID;
	}
	
	public final EnumBrickModel getModelID() {
		return modelID;
	}
	
	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof AbstractBrick)) return false;
		AbstractBrick b = (AbstractBrick) another;
		if (materialID != b.materialID) return false;
		if (colorID != b.colorID) return false;
		if (modelID != b.modelID) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Brick: " + materialID + " | " + colorID + " | " + modelID;
	}
}

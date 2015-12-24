package com.bountive.brick;

public class Brick {

	private EnumBrickModel currentType;
	
	public Brick(EnumBrickModel type) {
		currentType = type;
	}
	
	public enum EnumBrickModel {
		FLAT_1x1,
		FULL_1x1;
	}
	
	public EnumBrickModel getModelType() {
		return currentType;
	}
}

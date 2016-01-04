package com.bountive.world.object.brick;

public abstract class AbstractBrick {

	private EnumBrickModel type;
	
	public AbstractBrick(EnumBrickModel t) {
		type = t;
	}
	
	public enum EnumBrickModel {
		FLAT_1x1,
		FULL_1x1;
	}
	
	public EnumBrickModel getModelType() {
		return type;
	}
}

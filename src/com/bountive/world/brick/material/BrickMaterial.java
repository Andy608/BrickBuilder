package com.bountive.world.brick.material;

public abstract class BrickMaterial {

	private final int MATERIAL_ID;
	
	protected EnumBrickColor[] validColors;
	
	public BrickMaterial(int id) {
		this(id, new EnumBrickColor[] {EnumBrickColor.WHITE});
	}
	
	public BrickMaterial(int id, EnumBrickColor[] colors) {
		MATERIAL_ID = id;
		validColors = colors;
	}
	
	public final int getID() {
		return MATERIAL_ID;
	}
	
	public final EnumBrickColor[] getValidColors() {
		return validColors;
	}
}

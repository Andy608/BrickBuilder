package com.bountive.world.brick.model;


public abstract class BrickPlaceableModel extends BrickModel {

	public BrickPlaceableModel(int id, int length, int width, int height) {
		super(id, length, width, height);
	}
	
	public enum EnumComponentType {
		LEFT,
		LEFT_FRONT_SIDE_BEVEL,
		FRONT,
		RIGHT_FRONT_SIDE_BEVEL,
		RIGHT,
		RIGHT_BACK_SIDE_BEVEL,
		BACK,
		LEFT_BACK_SIDE_BEVEL,
		
		LEFT_TOP_BEVEL,
		FRONT_TOP_BEVEL,
		RIGHT_TOP_BEVEL,
		BACK_TOP_BEVEL,
		
		BOTTOM,
		TOP,
		
		////////////////////
		
		SIDE,		//Used for objects like round objects/if side needs to be rendered when blocks are next to it.
		ALL;		//Used for weirdly shaped objects that need all sides to be rendered even when blocks are next to it.
	}
}

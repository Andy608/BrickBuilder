package com.bountive.world.brick.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BrickModel {

	public final int MODEL_ID;
	
	public final int BRICK_LENGTH;
	public final int BRICK_WIDTH;
	public final float BRICK_HEIGHT;
	
	protected final List<BrickModelComponent> components;
	
	public BrickModel(int id, int length, int width, int height) {
		MODEL_ID = id;
		BRICK_LENGTH = length;
		BRICK_WIDTH = width;
		BRICK_HEIGHT = height;
		components = new ArrayList<>();
	}
	
	protected abstract void assembleModel();
	
	public List<BrickModelComponent> getComponents() {
		return components;
	}
}

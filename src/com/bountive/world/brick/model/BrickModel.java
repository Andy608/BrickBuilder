package com.bountive.world.brick.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BrickModel {

	public final int MODEL_ID;
	
	//Length = x direction.
	public final int BRICK_LENGTH;
	
	//Width = z direction.
	public final int BRICK_WIDTH;
	
	//Height = y direction.
	public final int BRICK_HEIGHT;
	
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
	
	@Override
	public String toString() {
		return "Brick model: " + MODEL_ID + " Model Height: " + BRICK_HEIGHT;
	}
}

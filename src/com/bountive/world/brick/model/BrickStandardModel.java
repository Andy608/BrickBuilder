package com.bountive.world.brick.model;

import math.Vector3f;

import com.bountive.graphics.model.util.ModelComponents;

/**
 * Standard means the normal brick shape. Not special pieces.
 */
public abstract class BrickStandardModel extends BrickPlaceableModel {
	
	public BrickModelComponent left;
	public BrickModelComponent leftFrontSideBevel;
	public BrickModelComponent front;
	public BrickModelComponent rightFrontSideBevel;
	public BrickModelComponent right;
	public BrickModelComponent rightBackSideBevel;
	public BrickModelComponent back;
	public BrickModelComponent leftBackSideBevel;
	public BrickModelComponent leftTopBevel;
	public BrickModelComponent frontTopBevel;
	public BrickModelComponent rightTopBevel;
	public BrickModelComponent backTopBevel;
	public BrickModelComponent bottom;
	public BrickModelComponent top;
	
	public BrickStandardModel(int id, int length, int width, int height) {
		super(id, length, width, height);
		
//		left 					= new BrickModelComponent[BRICK_WIDTH][BRICK_HEIGHT];
		left 					= new BrickModelComponent(EnumComponentType.LEFT);
		leftFrontSideBevel 		= new BrickModelComponent(EnumComponentType.LEFT_FRONT_SIDE_BEVEL);
//		front					= new BrickModelComponent[BRICK_LENGTH][BRICK_HEIGHT];
		front					= new BrickModelComponent(EnumComponentType.FRONT);
		rightFrontSideBevel		= new BrickModelComponent(EnumComponentType.RIGHT_FRONT_SIDE_BEVEL);
//		right					= new BrickModelComponent[BRICK_WIDTH][BRICK_HEIGHT];
		right					= new BrickModelComponent(EnumComponentType.RIGHT);
		rightBackSideBevel		= new BrickModelComponent(EnumComponentType.RIGHT_BACK_SIDE_BEVEL);
//		back					= new BrickModelComponent[BRICK_LENGTH][BRICK_HEIGHT];
		back					= new BrickModelComponent(EnumComponentType.BACK);
		leftBackSideBevel		= new BrickModelComponent(EnumComponentType.LEFT_BACK_SIDE_BEVEL);
		leftTopBevel			= new BrickModelComponent(EnumComponentType.LEFT_TOP_BEVEL);
		frontTopBevel			= new BrickModelComponent(EnumComponentType.FRONT_TOP_BEVEL);
		rightTopBevel			= new BrickModelComponent(EnumComponentType.RIGHT_TOP_BEVEL);
		backTopBevel			= new BrickModelComponent(EnumComponentType.BACK_TOP_BEVEL);
//		bottom					= new BrickModelComponent[BRICK_LENGTH][BRICK_WIDTH];
//		top						= new BrickModelComponent[BRICK_LENGTH][BRICK_WIDTH];
		bottom					= new BrickModelComponent(EnumComponentType.BOTTOM);
		top					= new BrickModelComponent(EnumComponentType.TOP);
		
//		for (int h = 0; h < BRICK_HEIGHT; h++) {
//			for (int w = 0; w < BRICK_WIDTH; w++) {
//				left[w][h] = new BrickModelComponent(EnumComponentType.LEFT);
//				right[w][h] = new BrickModelComponent(EnumComponentType.RIGHT);
//				components.add(left[w][h]);
//				components.add(right[w][h]);
//			}
//			
//			for (int l = 0; l < BRICK_LENGTH; l++) {
//				front[l][h] = new BrickModelComponent(EnumComponentType.FRONT);
//				back[l][h] = new BrickModelComponent(EnumComponentType.BACK);
//				components.add(front[l][h]);
//				components.add(back[l][h]);
//			}
//		}
//		
//		for (int w = 0; w < BRICK_WIDTH; w++) {
//			for (int l = 0; l < BRICK_LENGTH; l++) {
//				bottom[l][w] = new BrickModelComponent(EnumComponentType.BOTTOM);
//				top[l][w] = new BrickModelComponent(EnumComponentType.TOP);
//				components.add(bottom[l][w]);
//				components.add(top[l][w]);
//			}
//		}
		
		components.add(left);
		components.add(leftFrontSideBevel);
		components.add(front);
		components.add(rightFrontSideBevel);
		components.add(right);
		components.add(rightBackSideBevel);
		components.add(back);
		components.add(leftBackSideBevel);
		components.add(leftTopBevel);
		components.add(frontTopBevel);
		components.add(rightTopBevel);
		components.add(backTopBevel);
		components.add(bottom);
		components.add(top);
	}
	
	protected final void addComponent(BrickModelComponent comp, ModelComponents model, Vector3f translation, Vector3f rotation) {
		comp.addModel(model, translation, rotation);
		translation.set(0, 0, 0);
		rotation.set(0, 0, 0);
	}
}

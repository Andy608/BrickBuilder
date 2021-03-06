package com.bountive.world.brick.model;

import math.Vector3f;

import com.bountive.setting.GameOptions;
import com.bountive.setting.util.EnumGraphicsLevel;
import com.bountive.world.brick.component.BrickComponents;

public class BrickFlat1x1Model extends BrickStandardModel {

	public BrickFlat1x1Model(int id) {
		super(id, 1, 1, 1);
		assembleModel();
	}

	@Override
	protected void assembleModel() {
		Vector3f translationHelper3f = new Vector3f();
		Vector3f rotationHelper3f = new Vector3f();
		
		if (GameOptions.gameOptions.getGraphicsLevel() == EnumGraphicsLevel.LOW) {
			
			addComponent(leftBackSideBevel, BrickComponents.LOW_POLY_FLAT_SIDE_BEVEL, translationHelper3f, rotationHelper3f);
			addComponent(left, BrickComponents.LOW_POLY_FLAT_SIDE_TYPE_2, translationHelper3f, rotationHelper3f);
			addComponent(leftTopBevel, BrickComponents.LOW_POLY_FLAT_TOP_BEVEL, translationHelper3f, rotationHelper3f);
			
			rotationHelper3f.y = 90f;
			translationHelper3f.z = 1f;
			addComponent(leftFrontSideBevel, BrickComponents.LOW_POLY_FLAT_SIDE_BEVEL, translationHelper3f, rotationHelper3f);
			rotationHelper3f.y = 90f;
			translationHelper3f.z = 1f;
			addComponent(front, BrickComponents.LOW_POLY_FLAT_SIDE_TYPE_2, translationHelper3f, rotationHelper3f);
			rotationHelper3f.y = 90f;
			translationHelper3f.z = 1f;
			addComponent(frontTopBevel, BrickComponents.LOW_POLY_FLAT_TOP_BEVEL, translationHelper3f, rotationHelper3f);
			
			rotationHelper3f.y = 180f;
			translationHelper3f.x = 1f;
			translationHelper3f.z = 1f;
			addComponent(rightFrontSideBevel, BrickComponents.LOW_POLY_FLAT_SIDE_BEVEL, translationHelper3f, rotationHelper3f);
			rotationHelper3f.y = 180f;
			translationHelper3f.x = 1f;
			translationHelper3f.z = 1f;
			addComponent(right, BrickComponents.LOW_POLY_FLAT_SIDE_TYPE_2, translationHelper3f, rotationHelper3f);
			rotationHelper3f.y = 180f;
			translationHelper3f.x = 1f;
			translationHelper3f.z = 1f;
			addComponent(rightTopBevel, BrickComponents.LOW_POLY_FLAT_TOP_BEVEL, translationHelper3f, rotationHelper3f);
			
			rotationHelper3f.y = -90f;
			translationHelper3f.x = 1f;
			addComponent(rightBackSideBevel, BrickComponents.LOW_POLY_FLAT_SIDE_BEVEL, translationHelper3f, rotationHelper3f);
			rotationHelper3f.y = -90f;
			translationHelper3f.x = 1f;
			addComponent(back, BrickComponents.LOW_POLY_FLAT_SIDE_TYPE_2, translationHelper3f, rotationHelper3f);
			rotationHelper3f.y = -90f;
			translationHelper3f.x = 1f;
			addComponent(backTopBevel, BrickComponents.LOW_POLY_FLAT_TOP_BEVEL, translationHelper3f, rotationHelper3f);
			
			addComponent(bottom, BrickComponents.LOW_POLY_FLAT_BOTTOM, translationHelper3f, rotationHelper3f);
			addComponent(top, BrickComponents.LOW_POLY_FLAT_TOP, translationHelper3f, rotationHelper3f);
		}
		else if (GameOptions.gameOptions.getGraphicsLevel() == EnumGraphicsLevel.MEDIUM) {
			
		}
		else if (GameOptions.gameOptions.getGraphicsLevel() == EnumGraphicsLevel.HIGH) {
			
		}
		else if (GameOptions.gameOptions.getGraphicsLevel() == EnumGraphicsLevel.ULTRA) {
			
		}
		else {
			
		}
	}
}

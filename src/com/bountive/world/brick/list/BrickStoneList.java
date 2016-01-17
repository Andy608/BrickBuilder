package com.bountive.world.brick.list;

import com.bountive.world.brick.AbstractBrick;
import com.bountive.world.brick.BrickStone;
import com.bountive.world.brick.helper.EnumBrickColor;
import com.bountive.world.brick.helper.EnumBrickModel;

public class BrickStoneList extends AbstractBrickList {

	private static final EnumBrickColor[] COLOR_TYPES = new EnumBrickColor[]{EnumBrickColor.WHITE, EnumBrickColor.BRIGHT_RED, EnumBrickColor.BRIGHT_YELLOW};
	private static final EnumBrickModel[] MODEL_TYPES = new EnumBrickModel[]{EnumBrickModel.REGULAR_1X1, EnumBrickModel.FLAT_1X1};
	
	public BrickStoneList(int materialID) {
		super(materialID, COLOR_TYPES, MODEL_TYPES);
	}
	
	@Override
	protected AbstractBrick createBrick(int materialID, EnumBrickColor colorID, EnumBrickModel modelID) {
		return new BrickStone(materialID, colorID, modelID);
	}
}

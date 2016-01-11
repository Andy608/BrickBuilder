package com.bountive.world.brick.list;

import java.util.List;

import com.bountive.world.brick.AbstractBrick;
import com.bountive.world.brick.BrickStone;
import com.bountive.world.brick.helper.EnumBrickColor;
import com.bountive.world.brick.helper.EnumBrickModel;

public class BrickStoneList extends AbstractBrickList {

	public BrickStoneList(int materialID, EnumBrickColor[] colorTypes, EnumBrickModel[] modelTypes) {
		super(materialID, colorTypes, modelTypes);
	}
	
	public BrickStoneList(int materialID, List<EnumBrickColor> colorTypes, List<EnumBrickModel> modelTypes) {
		super(materialID, colorTypes, modelTypes);
	}

	@Override
	protected AbstractBrick createBrick(int materialID, EnumBrickColor colorID, EnumBrickModel modelID) {
		return new BrickStone(materialID, colorID, modelID);
	}
}

package com.bountive.world.brick;

import com.bountive.world.brick.helper.BrickIDManager;
import com.bountive.world.brick.helper.EnumBrickColor;
import com.bountive.world.brick.helper.EnumBrickModel;
import com.bountive.world.brick.list.BrickStoneList;

public class Bricks {

	public static BrickStoneList stoneList = new BrickStoneList(0, new EnumBrickColor[]{EnumBrickColor.WHITE, EnumBrickColor.BRIGHT_RED, EnumBrickColor.BRIGHT_YELLOW}, new EnumBrickModel[]{EnumBrickModel.REGULAR_1X1, EnumBrickModel.FLAT_1X1});
	
	
	//In future use the list in IDManager to render using IDs of brick.
	public static void registerBricks() {
		BrickIDManager.init();
		BrickIDManager.getManager().registerBrickType(stoneList);
		
		System.out.println(stoneList);
	}
}

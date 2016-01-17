package com.bountive.world.brick;

import com.bountive.world.brick.helper.BrickIDManager;
import com.bountive.world.brick.list.BrickStoneList;

public class Bricks {

	public static BrickStoneList stoneList = new BrickStoneList(0);
	
	
	//In future use the list in IDManager to render using IDs of brick.
	public static void registerBricks() {
		BrickIDManager.init();
		BrickIDManager.getManager().registerBrickType(stoneList);
		System.out.println(stoneList);
	}
}

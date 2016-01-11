package com.bountive.world.brick.helper;

import com.bountive.util.logger.LoggerUtil;
import com.bountive.world.brick.list.AbstractBrickList;

public class BrickIDManager {

	private static final int UNSIGNED_BYTE_MAX = 255;
	
	private static BrickIDManager instance;
	private AbstractBrickList[] brickList;
	
	private BrickIDManager() {
		instance = this;
		brickList = new AbstractBrickList[UNSIGNED_BYTE_MAX + 1];
	}
	
	public static void init() {
		if (instance == null) {
			new BrickIDManager();
		}
		else {
			LoggerUtil.logWarn(BrickIDManager.class, Thread.currentThread(), BrickIDManager.class.getSimpleName() + " is already initialized.");
		}
	}
	
	public void registerBrickType(AbstractBrickList brickType) {
		try {
			validateID(brickType.getMaterialID());
		} catch (IllegalArgumentException e) {
			LoggerUtil.logError(getClass(), e);
		}
		brickList[brickType.getMaterialID()] = brickType;
	}
	
	private void validateID(int id) throws IllegalArgumentException {
		if (id > UNSIGNED_BYTE_MAX || id < 0) {
			throw new IllegalArgumentException("Brick id: " + id + " is out of bounds.");
		}
		
		for (AbstractBrickList b : brickList) {
			if (b != null && b.getMaterialID() == id) {
				throw new IllegalArgumentException("Brick id: " + id + " is already in use.");
			}
		}
	}
	
	public static BrickIDManager getManager() {
		return instance;
	}
}

package com.bountive.world.zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import math.Vector3f;

import com.bountive.util.math.MathHelper;
import com.bountive.world.brick.AbstractBrick;
import com.bountive.world.brick.Bricks;
import com.bountive.world.brick.helper.EnumBrickModel;

public class Zone {

	public static final float BRICK_CONTAINER_HEIGHT = 0.4f;
//	public static final int ZONE_WIDTH = 18;
//	public static final int ZONE_HEIGHT = 45;
	public static final int ZONE_WIDTH = 16;
	public static final int ZONE_HEIGHT = 16;
	
	private AbstractBrick[] zoneArray;
	private List<Integer> knownIndexes;
	
	//Holds a list of index spots per brick type.
	private HashMap<AbstractBrick, List<Integer>> brickTypeIndexes;
	//Holds a list of index spots per model
	private HashMap<EnumBrickModel, List<AbstractBrick>> modelBatch;
	
	private Vector3f position;
	private Vector3f rotation;
	
	public Zone(Vector3f startingPoint) {
		this(startingPoint, new Vector3f());
	}
	
	public Zone(Vector3f startingPoint, Vector3f zoneRotation) {
		zoneArray = new AbstractBrick[ZONE_HEIGHT * ZONE_WIDTH * ZONE_WIDTH];
		knownIndexes = new ArrayList<>(zoneArray.length);
		
		brickTypeIndexes = new HashMap<>();
		modelBatch = new HashMap<>();
		
		position = new Vector3f(startingPoint);
		rotation = new Vector3f(zoneRotation);
		//get bricks from file or generate if new... --> WorldZoneHandler will handle that ;D
		populateZone();
	}
	
	private void populateZone() {
		Random rand = MathHelper.RAND;
		for (int i = 0; i < 200; i++) {
			onBrickPlaced(Bricks.stoneList.getBrick(rand.nextInt(Bricks.stoneList.getColorAmount()), 0), i);
//			zoneArray[i] = Bricks.stoneList.getBrick(rand.nextInt(Bricks.stoneList.getColorAmount()), 0);
		}
	}
	
	public void onBrickPlaced(AbstractBrick brick, int zoneX, int zoneY, int zoneZ) {
		onBrickPlaced(brick, zoneCoordToIndex(zoneX, zoneY, zoneZ));
	}
	
	/**
	 * This method updates the bricks in the Maps whenever a brick is placed.
	 */
	private void onBrickPlaced(AbstractBrick brick, int index) {
		knownIndexes.add(index);
		System.out.println("ADDING" + index);
		
//		for (int i = 0; i < knownIndexes.size(); i++) {
		
		//Check if brick type has a list.
		List<Integer> brickIndexes = brickTypeIndexes.get(brick);
		
		//If not, create a new list and add the index to it.
		if (brickIndexes == null) {
			List<Integer> newIndexList = new ArrayList<>();
			newIndexList.add(index);
			brickTypeIndexes.put(brick, newIndexList);
		}
		//If so, add the index to the brick list.
		else {
			brickIndexes.add(index);
		}
		
		//////////////////////////////////////////////////
		
		//Check if model type has a list.
		List<AbstractBrick> bricksForModel = modelBatch.get(brick.getModelID());
		
		//If not, create a new list and add the model to it.
		if (bricksForModel == null) {
			List<AbstractBrick> newBrickList = new ArrayList<>();
			newBrickList.add(brick);
			modelBatch.put(brick.getModelID(), newBrickList);
		}
		//If so, add the brick to the model list.
		else {
			bricksForModel.add(brick);
		}
//		}
		
		//Add the brick to the zone array.
		zoneArray[index] = brick;
	}
	
	private int zoneCoordToIndex(int x, int y, int z) {
		return x + (z * ZONE_WIDTH) + (y * ZONE_WIDTH * ZONE_WIDTH);
	}
	
	public AbstractBrick getBrick(int x, int y, int z) {
		return zoneArray[zoneCoordToIndex(x, y, z)];
	}
	
	public AbstractBrick getBrick(int index) {
		return zoneArray[index];
	}
	
	public Vector3f zoneIndexToZoneCoord(Vector3f source, int index) {
//		source.x = index % ZONE_WIDTH;
//		index /= ZONE_WIDTH;
//		source.z = index % ZONE_WIDTH;
//		index /= ZONE_WIDTH;
//		source.y = index;
//		return source;
		
		source.x = (index % ZONE_WIDTH);
		index /= ZONE_WIDTH;
		source.z = (index % ZONE_WIDTH);
		index /= ZONE_WIDTH;
		source.y = (index) * 1.2f;
		return source;
	}
	
	public HashMap<AbstractBrick, List<Integer>> getBrickIndexes() {
		return new HashMap<AbstractBrick, List<Integer>>(brickTypeIndexes);
	}
	
	public HashMap<EnumBrickModel, List<AbstractBrick>> getModelBatch() {
		return new HashMap<EnumBrickModel, List<AbstractBrick>>(modelBatch);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
}

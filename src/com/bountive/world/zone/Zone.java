package com.bountive.world.zone;

import java.util.Random;

import math.Vector3f;

import com.bountive.world.brick.material.BrickMaterial;
import com.bountive.world.brick.model.BrickModel;

public class Zone {

	public static final int ZONE_WIDTH = 18;
	public static final int ZONE_HEIGHT = 45;
	public static final int ZONE_ARRAY_LENGTH = ZONE_WIDTH * ZONE_WIDTH * ZONE_HEIGHT;
	public static final float ZONEBRICK_HEIGHT = 0.4f;
	
	private ZoneBrick[] zoneBricks;
	
	private Vector3f position;
	private final Vector3f ROTATION;
	
	public Zone(Vector3f pos) {
		this(pos, new Vector3f());
	}
	
	public Zone(Vector3f pos, Vector3f rot) {
		position = pos;
		ROTATION = rot;
		zoneBricks = new ZoneBrick[ZONE_ARRAY_LENGTH];
		
		for (int i = 0; i < Zone.ZONE_ARRAY_LENGTH; i++) {
			zoneBricks[i] = ZoneBrick.stoneBrick;
		}
	}
	
	//JUST FOR FUN
	public Zone(Vector3f pos, Vector3f rot, boolean randomize) {
		position = pos;
		ROTATION = rot;
		zoneBricks = new ZoneBrick[ZONE_ARRAY_LENGTH];
		
		Random rand = new Random();
		for (int i = 0; i < Zone.ZONE_ARRAY_LENGTH; i++) {
			if (rand.nextInt(4) == 0)
				zoneBricks[i] = ZoneBrick.stoneBrick;
		}
	}
	
	public int getIndexFromZoneCoordinate(int x, int y, int z) {
		return x + (z * ZONE_WIDTH) + (y * ZONE_WIDTH * ZONE_WIDTH);
	}
	
	public Vector3f getZoneCoordinateFromZoneIndex(Vector3f source, int index) {
		source.x = index % ZONE_WIDTH;
		index /= ZONE_WIDTH;
		source.z = index % ZONE_WIDTH;
		index /= ZONE_WIDTH;
		source.y = index * ZONEBRICK_HEIGHT;
		return source;
	}
	
	public ZoneBrick getZoneBrick(int x, int y, int z) {
		return zoneBricks[getIndexFromZoneCoordinate(x, y, z)];
	}
	
	public ZoneBrick getZoneBrick(int i) {
		return zoneBricks[i];
	}
	
	public BrickMaterial getBrickMaterial(int x, int y, int z) {
		return getZoneBrick(x, y, z).getMaterial();
	}
	
	public BrickMaterial getBrickMaterial(int i) {
		return getZoneBrick(i).getMaterial();
	}
	
	public BrickModel getBrickModel(int x, int y, int z) {
		return getZoneBrick(x, y, z).getModel();
	}
	
	public BrickModel getBrickModel(int i) {
		return getZoneBrick(i).getModel();
	}
	
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	public Vector3f getRotation() {
		return new Vector3f(ROTATION);
	}
}

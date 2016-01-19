package com.bountive.world;

import math.Vector3f;

import com.bountive.graphics.view.FlyingCamera;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.world.render.WorldZoneRenderer;
import com.bountive.world.zone.WorldZoneManager;
import com.bountive.world.zone.Zone;

public class World {
	
	private FlyingCamera camera; //Eventually the player.

	private WorldZoneRenderer zoneRenderer;
	private WorldZoneManager zoneManager;
	
	public World() {
		LoggerUtil.logInfo(this.getClass(), "Loading World...");
		camera = new FlyingCamera();
		zoneRenderer = new WorldZoneRenderer();
		zoneManager = new WorldZoneManager();
		
		testZone = new Zone(new Vector3f());
		testZone1 = new Zone(new Vector3f(Zone.ZONE_WIDTH + 1, 0, 0), new Vector3f(0, 50, 0), false);
		addZonesToWorld();
	}
	
	Zone testZone, testZone1;
	//TEMP
	public void addZonesToWorld() {
		LoggerUtil.logInfo(this.getClass(), "Attempting to create zone...");
		WorldZoneManager.counter = 0;	//TODO:TEMP
		zoneManager.addZone(testZone);	//
		zoneManager.addZone(testZone1);	//
	}
	
	public void update(double deltaTime) {
		//TODO: UPDATE ZONE ON BLOCK PLACED.
	}
	
	public void render(double lerp) {
		zoneRenderer.render(camera, zoneManager);
	}
	
	public FlyingCamera getCamera() {
		return camera;
	}
	
	public void release() {
		zoneRenderer.release();
	}
}

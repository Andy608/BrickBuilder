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
		addZonesToWorld();
	}
	
//	Zone z1 = new Zone(new Vector3f(Zone.ZONE_WIDTH, 0, 0));
	Zone z2 = new Zone(new Vector3f());
//	Zone z3 = new Zone(new Vector3f(Zone.ZONE_WIDTH * 2, 0, 0), new Vector3f(0, 70, 0));
	
	//TEMP
	public void addZonesToWorld() {
		LoggerUtil.logInfo(this.getClass(), "Attempting to create zone...");
		WorldZoneManager.counter = 0;	//TODO:TEMP
		System.out.println("ADDING ZONES");
//		zoneManager.addZone(z1);
		zoneManager.addZone(z2);
//		zoneManager.addZone(z3);
	}
	
	public void update(double deltaTime) {
		//TODO: UPDATE ZONE ON BLOCK PLACED.
//		z3.rotation.y += (float)(deltaTime * 20f);
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

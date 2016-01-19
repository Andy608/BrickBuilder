package com.bountive.world.zone;

import com.bountive.setting.GameOptions;
import com.bountive.util.logger.LoggerUtil;

public class WorldZoneManager {
	
	//TODO: THIS WHOLE CLASS. THIS NEEDS TO BE ALL THOUGHT OUT.

	private ZoneModelFactory zoneBuilder;
	private Zone[][] activeZones;
	
	private ModelZone[][] zoneModels;
	
	public WorldZoneManager() {
		zoneBuilder = new ZoneModelFactory();
		
		int size = GameOptions.gameOptions.getZoneRenderDistance();
		activeZones = new Zone[size][size];
		zoneModels = new ModelZone[size][size];
	}
	
	public static int counter;
	public void addZone(Zone z) {
		//THIS IS ALL TEMP
		activeZones[counter][0] = z;
		
		LoggerUtil.logInfo(getClass(), "Attempting to create zone model...");
		zoneModels[counter][0] = zoneBuilder.buildNewZoneModel(z, null, null, null, null, null, null);
		
		counter++;
	}
	
	public void updateRenderDistance() {
		int newSize = GameOptions.gameOptions.getZoneRenderDistance();
		Zone[][] temp = new Zone[newSize][newSize];
		ModelZone[][] temp2 = new ModelZone[newSize][newSize];
		
		for (int i = 0; i < activeZones.length; i++) {
			for (int j = 0; j < activeZones[0].length; j++) {
				temp[i][j] = activeZones[i][j];
				temp2[i][j] = zoneModels[i][j];
			}
		}
		activeZones = temp;
		zoneModels = temp2;
	}
	
	public int getSize() {
		return activeZones.length;
	}
	
	public Zone getZone(int i, int j) {
		return activeZones[i][j];
	}
	
	public ModelZone getModel(int i, int j) {
		return zoneModels[i][j];
	}
}

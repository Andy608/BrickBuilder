package com.bountive.world.zone;

import com.bountive.setting.GameOptions;
import com.bountive.util.logger.LoggerUtil;

public class WorldZoneManager {
	
	//TODO: THIS WHOLE CLASS. THIS NEEDS TO BE ALL THOUGHT OUT.

	private Zone[][] activeZones;
	
	public WorldZoneManager() {
		int size = GameOptions.gameOptions.getZoneRenderDistance();
		activeZones = new Zone[size][size];
	}
	
	//THIS IS ALL TEMP
	public static int counter;
	public void addZone(Zone z) {
		activeZones[counter][0] = z;
		LoggerUtil.logInfo(getClass(), "Attempting to create zone model...");
		counter++;
	}
	
	public void updateRenderDistance() {
		int newSize = GameOptions.gameOptions.getZoneRenderDistance();
		Zone[][] temp = new Zone[newSize][newSize];
		
		for (int i = 0; i < activeZones.length; i++) {
			for (int j = 0; j < activeZones[0].length; j++) {
				temp[i][j] = activeZones[i][j];
			}
		}
		activeZones = temp;
	}
	
	public int getSize() {
		return activeZones.length;
	}
	
	public Zone getZone(int i, int j) {
		return activeZones[i][j];
	}
}

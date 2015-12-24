package com.bountive.setting;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import math.Vector2f;

import com.bountive.util.SystemUtil;
import com.bountive.util.SystemUtil.EnumOS;
import com.bountive.util.resource.ResourceLocation;

public abstract class SettingsBase {

	protected static final String EXTENSION = ".bbs";
	protected static final EnumOS os = SystemUtil.getOSType();
	protected static final ResourceLocation SETTINGS_DIR = new ResourceLocation(ResourceLocation.APPDATA_DIRECTORY, "settings");
	
	public abstract void loadSettingsFromFile();
	protected abstract void setDefaultSettings();
	public abstract void storeSettingsInFile();
	
	/**
	 * Reads all information in from a settings file and breaks up each line into a string and puts it in an array of Strings.
	 * Then trims the array to take out any lines with blank space in them.
	 * @param settingsFile
	 * @return
	 */
	protected String[] readSettingsFile(File settingsFile) throws IOException {
		List<String> lineValues = null;
		lineValues = Files.readAllLines(Paths.get(settingsFile.getPath()), StandardCharsets.UTF_8);
		return trimExtraSpace(lineValues);
	}
	
	protected String[] trimExtraSpace(List<String> list) {
		
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).trim());
			if (list.get(i).isEmpty()) {
				list.remove(i);
				i--;
			}
			else {
				list.set(i, list.get(i).replaceAll("\\s+",""));
			}
		}
		return list.toArray(new String[list.size()]);
	}
	
	protected Boolean parseBoolean(String s) {
		if (s != null) {
			if (s.equalsIgnoreCase("true")) return true;
			else if (s.equalsIgnoreCase("false")) return false;
		}
		return null;
	}
	
	protected boolean getBooleanValue(Boolean savedValue, boolean defaultValue) {
		if (savedValue == null) return defaultValue;
		else return savedValue.booleanValue();
	}
	
	protected int getIntValue(Integer savedValue, int defaultValue) {
		if (savedValue == null) return defaultValue;
		else return savedValue.intValue();
	}
	
	protected Vector2f getVector2fValue(Vector2f savedValue, Vector2f defaultValue) throws IllegalStateException {
		if (defaultValue == null && savedValue == null) {
			throw new IllegalStateException("Both savedValue and defaultValue cannot be null! Cannot set value to a null reference.");
		}
		
		if (savedValue == null) return defaultValue;
		else return savedValue;
	}
}

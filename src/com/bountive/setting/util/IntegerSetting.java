package com.bountive.setting.util;

public class IntegerSetting extends AbstractFileSetting {

	private int defaultSetting;
	private int customSetting;
	
	public IntegerSetting(String fileName, int defaultint) {
		super(fileName);
		defaultSetting = defaultint;
		customSetting = defaultint;
	}
	
	public void setCustomInteger(int customInteger) {
		customSetting = customInteger;
	}
	
	public void resetInteger() {
		customSetting = defaultSetting;
	}
	
	public int getDefaultInteger() {
		return defaultSetting;
	}
	
	public int getCustomInteger() {
		return customSetting;
	}
}

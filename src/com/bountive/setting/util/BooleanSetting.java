package com.bountive.setting.util;

public class BooleanSetting extends AbstractFileSetting{

	private boolean defaultSetting;
	private boolean customSetting;
	
	public BooleanSetting(String fileName, boolean defaultValue) {
		super(fileName);
		defaultSetting = defaultValue;
		customSetting = defaultValue;
	}
	
	public void setCustomBoolean(boolean customBoolean) {
		customSetting = customBoolean;
	}
	
	public void resetBoolean() {
		customSetting = defaultSetting;
	}
	
	public boolean getDefaultBoolean() {
		return defaultSetting;
	}
	
	public boolean getCustomBoolean() {
		return customSetting;
	}
}

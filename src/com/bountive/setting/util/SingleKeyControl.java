package com.bountive.setting.util;


public class SingleKeyControl extends AbstractKey {

	private int defaultKey;
	private int customKey;
	
	public SingleKeyControl(String fileName, int defaultKeyOption) {
		super(fileName);
		defaultKey = defaultKeyOption;
		customKey = defaultKeyOption;
	}
	
	public void setCustomKey(int customKeyOption) {
		customKey = customKeyOption;
	}
	
	public boolean equalsControl(int key) {
		return customKey == key;
	}
	
	public void resetKey() {
		customKey = defaultKey;
	}
	
	public int getDefaultKey() {
		return defaultKey;
	}
	
	public int getCustomKey() {
		return customKey;
	}
}

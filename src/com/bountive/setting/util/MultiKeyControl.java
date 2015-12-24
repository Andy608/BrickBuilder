package com.bountive.setting.util;


public class MultiKeyControl extends AbstractKey {

	private int[] defaultKey;
	private int[] customKey;
	
	public MultiKeyControl(String fileName, int[] defaultBinding) {
		super(fileName);
		defaultKey = defaultBinding;
		customKey = defaultBinding.clone();
	}
	
	public void setCustomKeyBinding(int[] customKeyBinding) {
		customKey = customKeyBinding;
	}
	
	public void resetKeyBinding() {
		for (int i = 0; i < defaultKey.length; i++) {
			customKey[i] = defaultKey[i];
		}
	}
	
	public int[] getDefaultKeyBinding() {
		return defaultKey;
	}
	
	public int[] getCustomKeyBinding() {
		return customKey;
	}
}

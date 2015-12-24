package com.bountive.setting.util;

public abstract class AbstractFileSetting {

	private String fileName;
	
	public AbstractFileSetting(String name) {
		fileName = name;
	}
	
	public String getFileName() {
		return fileName;
	}
}

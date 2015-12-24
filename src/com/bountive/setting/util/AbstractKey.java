package com.bountive.setting.util;

public abstract class AbstractKey extends AbstractFileSetting{

	private boolean isPressed;
	
	public AbstractKey(String fileName) {
		super(fileName);
		isPressed = false;
	}
	
	public boolean isPressed() {
		return isPressed;
	}
	
	public void setPressed(boolean b) {
		isPressed = b;
	}
}

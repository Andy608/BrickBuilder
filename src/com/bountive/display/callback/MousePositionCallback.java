package com.bountive.display.callback;

import math.Vector2f;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.bountive.display.Window;

public class MousePositionCallback extends GLFWCursorPosCallback {

	private static Vector2f mouseOffset = new Vector2f();
	private static Vector2f mousePos = new Vector2f();
	
	@Override
	public void invoke(long windowID, double xpos, double ypos) {
		Vector2f windowSize = Window.getWindowSize();
		
		mousePos.set((float)xpos, (float)ypos);
		
		int width = (((int)windowSize.x & 1) == 0) ? (int)windowSize.x : (int)windowSize.x - 1;
		int height = (((int)windowSize.y & 1) == 0) ? (int)windowSize.y : (int)windowSize.y - 1;
		
		mouseOffset.set((float)(xpos - (width / 2f)), (float)(ypos - (height / 2f)));
	}
	
	public static Vector2f getMouseOffset() {
		return mouseOffset;
	}
	
	public static Vector2f getMousePos() {
		return mousePos;
	}
}

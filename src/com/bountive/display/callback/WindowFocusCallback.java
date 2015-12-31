package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowFocusCallback;

public class WindowFocusCallback extends GLFWWindowFocusCallback {

	public static boolean isWindowFocused;
	
	@Override
	public void invoke(long window, int focused) {
		isWindowFocused = focused == 1 ? true : false;
		System.out.println("Focused: " + isWindowFocused);
		//Focused!
		/*
		 * if (focused = GL11.GL_FALSE) {
		 * 		stop playing music!		
		 * 		bring up the pause menu! --> Save things
		 * }
		 */
	}
}

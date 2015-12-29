package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowFocusCallback;

public class WindowFocusCallback extends GLFWWindowFocusCallback {

	@Override
	public void invoke(long window, int focused) {
		System.out.println("Focused: " + (focused == 1 ? "true" : "false"));
		//Focused!
		/*
		 * if (focused = GL11.GL_FALSE) {
		 * 		stop playing music!		
		 * 		bring up the pause menu! --> Save things
		 * }
		 */
	}
}

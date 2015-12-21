package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

public class FramebufferSizeCallback extends GLFWFramebufferSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
//		GL11.glViewport(0, 0, width, height);
	}
}

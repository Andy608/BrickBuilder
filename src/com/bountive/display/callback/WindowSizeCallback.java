package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

import com.bountive.setting.WindowSettings;

public class WindowSizeCallback extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		
		if (!WindowSettings.windowSettings.isFullscreenEnabled()) {
			WindowSettings.windowSettings.updateWindowSize(width, height);
		}
	}
}

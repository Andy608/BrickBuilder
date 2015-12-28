package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

import com.bountive.setting.GameOptions;

public class WindowSizeCallback extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		if (!GameOptions.gameOptions.isFullscreenEnabled()) {
			GameOptions.gameOptions.updateWindowSize(width, height);
		}
	}
}

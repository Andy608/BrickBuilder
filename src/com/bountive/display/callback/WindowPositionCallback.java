package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowPosCallback;

import com.bountive.setting.WindowSettings;

public class WindowPositionCallback extends GLFWWindowPosCallback {

	@Override
	public void invoke(long window, int xpos, int ypos) {
		if (!WindowSettings.windowSettings.isFullscreenEnabled()) {
			WindowSettings.windowSettings.updateWindowPosition(xpos, ypos);
		}
	}
}

package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowPosCallback;

import com.bountive.setting.GameOptions;

public class WindowPositionCallback extends GLFWWindowPosCallback {

	@Override
	public void invoke(long window, int xpos, int ypos) {
		if (!GameOptions.gameOptions.isFullscreenEnabled()) {
			GameOptions.gameOptions.updateWindowPosition(xpos, ypos);
		}
	}
}

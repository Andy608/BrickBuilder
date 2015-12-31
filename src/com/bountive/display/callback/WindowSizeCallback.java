package com.bountive.display.callback;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.setting.GameOptions;

public class WindowSizeCallback extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		
		if (!GameOptions.gameOptions.isFullscreenEnabled()) {
			GameOptions.gameOptions.updateSavedWindowSize(width, height);
		}
		
		if (CameraMatrixManager.manager != null) {//TODO: if on game screen...
			CameraMatrixManager.manager.buildProjectionMatrix();
		}
	}
}

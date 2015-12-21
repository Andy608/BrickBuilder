package com.bountive.display.callback;

import java.util.LinkedHashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import com.bountive.display.Window;
import com.bountive.setting.ControlSettings;
import com.bountive.setting.WindowSettings;

public class KeyboardCallback extends GLFWKeyCallback {

	private static LinkedHashSet<Integer> currentKeys = new LinkedHashSet<>();
	
	@Override
	public void invoke(long windowID, int key, int scancode, int action, int mods) {
		
		if (action == GLFW.GLFW_RELEASE) {
			currentKeys.remove(key);
			
			if (ControlSettings.shutdownKey == key) {
				GLFW.glfwSetWindowShouldClose(windowID, GL11.GL_TRUE);
			}
			else if (ControlSettings.fullscreenKey == key) {
				WindowSettings.windowSettings.setFullscreen(!WindowSettings.windowSettings.isFullscreenEnabled());
				Window.buildScreen();
			}
		}
		else if (action == GLFW.GLFW_PRESS) {
			currentKeys.add(key);
		}
	}
}

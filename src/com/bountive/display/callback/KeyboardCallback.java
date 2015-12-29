package com.bountive.display.callback;

import java.util.LinkedHashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import com.bountive.display.Window;
import com.bountive.setting.ControlOptions;
import com.bountive.setting.GameOptions;

public class KeyboardCallback extends GLFWKeyCallback {

	private static LinkedHashSet<Integer> currentKeys = new LinkedHashSet<>();
	
	@Override
	public void invoke(long windowID, int key, int scancode, int action, int mods) {
		
		if (action == GLFW.GLFW_RELEASE) {
			currentKeys.remove(key);
			
			if (ControlOptions.shutdownKey.equals(key)) {
				GLFW.glfwSetWindowShouldClose(windowID, GL11.GL_TRUE);
			}
			else if (ControlOptions.fullscreenKey.equals(key)) {
				GameOptions.gameOptions.setFullscreen(!GameOptions.gameOptions.isFullscreenEnabled());
				Window.buildScreen();
//				GameOptions.gameOptions.setPerspective(!GameOptions.gameOptions.isPerspective());
//				CameraMatrixManager.manager.buildProjectionMatrix();
			}
			else if (ControlOptions.moveForwardKey.equals(key)) {
				ControlOptions.moveForwardKey.setPressed(false);
			}
			else if (ControlOptions.moveBackwardKey.equals(key)) {
				ControlOptions.moveBackwardKey.setPressed(false);
			}
			else if (ControlOptions.moveLeftKey.equals(key)) {
				ControlOptions.moveLeftKey.setPressed(false);
			}
			else if (ControlOptions.moveRightKey.equals(key)) {
				ControlOptions.moveRightKey.setPressed(false);
			}
			else if (ControlOptions.jumpKey.equals(key)) {
				ControlOptions.jumpKey.setPressed(false);
			}
			else if (ControlOptions.duckKey.equals(key)) {
				ControlOptions.duckKey.setPressed(false);
			}
			else if (ControlOptions.pauseKey.equals(key)) {
				ControlOptions.pauseKey.setPressed(!ControlOptions.pauseKey.isPressed());
			}
		}
		else if (action == GLFW.GLFW_PRESS) {
			currentKeys.add(key);
			
			//if (in the game and...)
			if (ControlOptions.moveForwardKey.equals(key)) {
				ControlOptions.moveForwardKey.setPressed(true);
			}
			else if (ControlOptions.moveBackwardKey.equals(key)) {
				ControlOptions.moveBackwardKey.setPressed(true);
			}
			else if (ControlOptions.moveLeftKey.equals(key)) {
				ControlOptions.moveLeftKey.setPressed(true);
			}
			else if (ControlOptions.moveRightKey.equals(key)) {
				ControlOptions.moveRightKey.setPressed(true);
			}
			else if (ControlOptions.jumpKey.equals(key)) {
				ControlOptions.jumpKey.setPressed(true);
			}
			else if (ControlOptions.duckKey.equals(key)) {
				ControlOptions.duckKey.setPressed(true);
			}
		}
	}
}

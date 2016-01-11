package com.bountive.display.callback;

import java.util.LinkedHashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import com.bountive.setting.ControlOptions;
import com.bountive.setting.GameOptions;

public class KeyboardCallback extends GLFWKeyCallback {

	private static LinkedHashSet<Integer> currentKeys = new LinkedHashSet<>();
	public static boolean toggle;
	
	@Override
	public void invoke(long windowID, int key, int scancode, int action, int mods) {
		
		if (action == GLFW.GLFW_RELEASE) {
			currentKeys.remove(key);
			
			if (ControlOptions.moveForwardKey.equalsControl(key)) {
				ControlOptions.moveForwardKey.setPressed(false);
			}
			else if (ControlOptions.moveBackwardKey.equalsControl(key)) {
				ControlOptions.moveBackwardKey.setPressed(false);
			}
			else if (ControlOptions.moveLeftKey.equalsControl(key)) {
				ControlOptions.moveLeftKey.setPressed(false);
			}
			else if (ControlOptions.moveRightKey.equalsControl(key)) {
				ControlOptions.moveRightKey.setPressed(false);
			}
			else if (ControlOptions.jumpKey.equalsControl(key)) {
				ControlOptions.jumpKey.setPressed(false);
			}
			else if (ControlOptions.duckKey.equalsControl(key)) {
				ControlOptions.duckKey.setPressed(false);
			}
			else if (ControlOptions.getPausedKey() == key) {
				ControlOptions.setPaused(!ControlOptions.isPaused());
			}
			else if (GLFW.GLFW_KEY_R == key) {
				toggle = false;
			}
		}
		else if (action == GLFW.GLFW_PRESS) {
			currentKeys.add(key);
			
			//if (in the game and...)/////////
			if (ControlOptions.moveForwardKey.equalsControl(key)) {
				ControlOptions.moveForwardKey.setPressed(true);
			}
			else if (ControlOptions.moveBackwardKey.equalsControl(key)) {
				ControlOptions.moveBackwardKey.setPressed(true);
			}
			else if (ControlOptions.moveLeftKey.equalsControl(key)) {
				ControlOptions.moveLeftKey.setPressed(true);
			}
			else if (ControlOptions.moveRightKey.equalsControl(key)) {
				ControlOptions.moveRightKey.setPressed(true);
			}
			else if (ControlOptions.jumpKey.equalsControl(key)) {
				ControlOptions.jumpKey.setPressed(true);
			}
			else if (ControlOptions.duckKey.equalsControl(key)) {
				ControlOptions.duckKey.setPressed(true);
			}
			///////////////////////////////
			else if (ControlOptions.shutdownKey.equalsControl(currentKeys)) {
				GLFW.glfwSetWindowShouldClose(windowID, GL11.GL_TRUE);
			}
			else if (ControlOptions.fullscreenKey.equalsControl(currentKeys)) {
				currentKeys.clear();
				GameOptions.gameOptions.setFullscreen(!GameOptions.gameOptions.isFullscreenEnabled());
				
//				GameOptions.gameOptions.setPerspective(!GameOptions.gameOptions.isPerspective());
//				CameraMatrixManager.manager.buildProjectionMatrix();
			}
			else if (ControlOptions.debugMode.equalsControl(currentKeys)) {
				ControlOptions.debugMode.setPressed(!ControlOptions.debugMode.isPressed());
				GameOptions.gameOptions.enableDebugMode(ControlOptions.debugMode.isPressed());
			}
			else if (GLFW.GLFW_KEY_R == key) {
				toggle = true;
			}
		}
	}
}

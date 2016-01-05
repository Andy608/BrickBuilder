package com.bountive.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.lwjgl.glfw.GLFW;

import com.bountive.display.Window;
import com.bountive.display.callback.MousePositionCallback;
import com.bountive.setting.util.MultiKeyControl;
import com.bountive.setting.util.PercentageSetting;
import com.bountive.setting.util.SingleKeyControl;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.ResourceLocation;

public final class ControlOptions extends AbstractBaseOptions {

	private static final ResourceLocation CONTROL_OPTIONS = new ResourceLocation(OPTIONS_DIR.getFullPath(), "control_options" + EXTENSION, false);
	
	public static ControlOptions controlOptions;

	private static SingleKeyControl pauseKey;
	
	public static MultiKeyControl shutdownKey;
	public static MultiKeyControl fullscreenKey;
	public static MultiKeyControl debugMode;
	
	public static PercentageSetting mouseSensitivity;
	
	public static SingleKeyControl moveForwardKey;
	public static SingleKeyControl moveBackwardKey;
	public static SingleKeyControl moveLeftKey;
	public static SingleKeyControl moveRightKey;
	
	public static SingleKeyControl jumpKey;
	public static SingleKeyControl duckKey;
	
	private ControlOptions() {
		controlOptions = this;
	}
	
	public static void init() throws IllegalStateException {
		if (controlOptions == null) {
			new ControlOptions();
			controlOptions.initDefaultOptions();
		}
		else {
			LoggerUtil.logWarn(ControlOptions.class, Thread.currentThread(), CONTROL_OPTIONS.getResourceName() + " is already initialized.");
		}
	}
	
	@Override
	protected void initDefaultOptions() {
		//Utility keys
		shutdownKey = new MultiKeyControl("shutdown_key", GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_ESCAPE);
		pauseKey = new SingleKeyControl("pause_key", GLFW.GLFW_KEY_ESCAPE);
		fullscreenKey = new MultiKeyControl("fullscreen_key", GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_TAB);
		debugMode = new MultiKeyControl("debug_mode", GLFW.GLFW_KEY_F1, GLFW.GLFW_KEY_Q);
		
		//Utility sliders
		mouseSensitivity = new PercentageSetting("mouse_sensitivity", 10, 1f, 0.05f, 3f);
		
		//In-game controls
		moveForwardKey = new SingleKeyControl("move_forward_key", GLFW.GLFW_KEY_W);
		moveBackwardKey = new SingleKeyControl("move_backward_key", GLFW.GLFW_KEY_S);
		moveLeftKey = new SingleKeyControl("move_left_key", GLFW.GLFW_KEY_A);
		moveRightKey = new SingleKeyControl("move_right_key", GLFW.GLFW_KEY_D);
		jumpKey = new SingleKeyControl("jump_key", GLFW.GLFW_KEY_SPACE);
		duckKey = new SingleKeyControl("duck_key", GLFW.GLFW_KEY_LEFT_SHIFT);
	}
	
	@Override
	public void loadOptionsFromFile() {
		File location = new File(CONTROL_OPTIONS.getFullPath());
		setDefaultOptions();
		
		if (location.exists()) {
			try {
				String[] options = readOptionsFile(location);
				
				for (String s : options) {
					String controlAttrib = s.substring(0, s.indexOf(DEFAULT_DELIMITER));
					
					if (controlAttrib.equals(shutdownKey.getFileName())) {
						shutdownKey.setCustomKeyBinding(getMultipleIntegersFromOption(s, shutdownKey.getDefaultKeyBinding(), DEFAULT_DELIMITER, SEPARATOR));
					}
					else if (controlAttrib.equals(pauseKey.getFileName())) {
						pauseKey.setCustomKey(getSingleIntegerFromOption(s, pauseKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(fullscreenKey.getFileName())) {
						fullscreenKey.setCustomKeyBinding(getMultipleIntegersFromOption(s, fullscreenKey.getDefaultKeyBinding(), DEFAULT_DELIMITER, SEPARATOR));
					}
					else if (controlAttrib.equals(debugMode.getFileName())) {
						debugMode.setCustomKeyBinding(getMultipleIntegersFromOption(s, debugMode.getDefaultKeyBinding(), DEFAULT_DELIMITER, SEPARATOR));
					}
					else if (controlAttrib.equals(mouseSensitivity.getFileName())) {
						mouseSensitivity.setCustomPercentage(getSingleFloatFromOption(s, mouseSensitivity.getDefaultPercentage(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(moveForwardKey.getFileName())) {
						moveForwardKey.setCustomKey(getSingleIntegerFromOption(s, moveForwardKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(moveBackwardKey.getFileName())) {
						moveBackwardKey.setCustomKey(getSingleIntegerFromOption(s, moveBackwardKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(moveLeftKey.getFileName())) {
						moveLeftKey.setCustomKey(getSingleIntegerFromOption(s, moveLeftKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(moveRightKey.getFileName())) {
						moveRightKey.setCustomKey(getSingleIntegerFromOption(s, moveRightKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(jumpKey.getFileName())) {
						jumpKey.setCustomKey(getSingleIntegerFromOption(s, jumpKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(duckKey.getFileName())) {
						duckKey.setCustomKey(getSingleIntegerFromOption(s, duckKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else {
						throw new IllegalStateException(s + " is not an expected option.");
					}
				}
			} catch (Exception e) {
				LoggerUtil.logWarn(getClass(), Thread.currentThread(), e, CONTROL_OPTIONS.getResourceName() + " is corrupt! Using default values.", true);
			}
		}
		else {
			new File(CONTROL_OPTIONS.getParentDir()).mkdirs();
		}
	}

	@Override
	public void setDefaultOptions() {
		shutdownKey.resetKeyBinding();
		pauseKey.resetKey();
		fullscreenKey.resetKeyBinding();
		debugMode.resetKeyBinding();
		mouseSensitivity.resetPercentage();
		moveForwardKey.resetKey();
		moveBackwardKey.resetKey();
		moveLeftKey.resetKey();
		moveRightKey.resetKey();
		jumpKey.resetKey();
		duckKey.resetKey();
	}

	@Override
	public void storeOptionsInFile() {
		try (PrintStream writer = new PrintStream(CONTROL_OPTIONS.getFullPath(), "UTF-8")) {
			writer.println(shutdownKey.getFileName() + DEFAULT_DELIMITER + shutdownKey.getReadableCustomKeyBinding());
			writer.println(pauseKey.getFileName() + DEFAULT_DELIMITER + pauseKey.getCustomKey());
			writer.println(fullscreenKey.getFileName() + DEFAULT_DELIMITER + fullscreenKey.getReadableCustomKeyBinding());
			writer.println(debugMode.getFileName() + DEFAULT_DELIMITER + debugMode.getReadableCustomKeyBinding());
			writer.println(mouseSensitivity.getFileName() + DEFAULT_DELIMITER + mouseSensitivity.getCustomPercentage());
			writer.println(moveForwardKey.getFileName() + DEFAULT_DELIMITER + moveForwardKey.getCustomKey());
			writer.println(moveBackwardKey.getFileName() + DEFAULT_DELIMITER + moveBackwardKey.getCustomKey());
			writer.println(moveLeftKey.getFileName() + DEFAULT_DELIMITER + moveLeftKey.getCustomKey());
			writer.println(moveRightKey.getFileName() + DEFAULT_DELIMITER + moveRightKey.getCustomKey());
			writer.println(jumpKey.getFileName() + DEFAULT_DELIMITER + jumpKey.getCustomKey());
			writer.print(duckKey.getFileName() + DEFAULT_DELIMITER + duckKey.getCustomKey());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			LoggerUtil.logError(getClass(), Thread.currentThread(), e);
		}
	}
	
	//TODO: Use when window is unfocused unexpectedly.
	public static void unPressAllKeys() {
		shutdownKey.setPressed(false);
		pauseKey.setPressed(false);
		fullscreenKey.setPressed(false);
		debugMode.setPressed(false);
		moveForwardKey.setPressed(false);
		moveBackwardKey.setPressed(false);
		moveLeftKey.setPressed(false);
		moveRightKey.setPressed(false);
		jumpKey.setPressed(false);
		duckKey.setPressed(false);
	}
	
	public static void setPaused(boolean b) {
		MousePositionCallback.centerMouse();
		
		if (b) GLFW.glfwSetInputMode(Window.getID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		else GLFW.glfwSetInputMode(Window.getID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
		pauseKey.setPressed(b);
	}
	
	public static void setPausedKey(int keyID) {
		pauseKey.setCustomKey(keyID);
	}
	
	public static boolean isPaused() {
		return pauseKey.isPressed();
	}
	
	public static int getPausedKey() {
		return pauseKey.getCustomKey();
	}
}
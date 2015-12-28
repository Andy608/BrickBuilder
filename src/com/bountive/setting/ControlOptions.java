package com.bountive.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.lwjgl.glfw.GLFW;

import com.bountive.setting.util.SingleKeyControl;
import com.bountive.util.logger.LoggerUtils;
import com.bountive.util.resource.ResourceLocation;

public final class ControlOptions extends AbstractBaseOptions {

	private static final ResourceLocation CONTROL_OPTIONS = new ResourceLocation(OPTIONS_DIR.getFullPath(), "control_options" + EXTENSION);
	
	public static ControlOptions controlOptions;

	public static SingleKeyControl shutdownKey;
	public static SingleKeyControl fullscreenKey;
	
	public static SingleKeyControl moveForwardKey;
	public static SingleKeyControl moveBackwardKey;
	public static SingleKeyControl moveLeftKey;
	public static SingleKeyControl moveRightKey;
	
	private ControlOptions() {
		controlOptions = this;
	}
	
	//TODO: Maybe change this to just print a warning message than crash the game.
	public static void init() throws IllegalStateException {
		if (controlOptions == null) {
			new ControlOptions();
			controlOptions.initDefaultOptions();
		}
		else {
			LoggerUtils.logWarn(Thread.currentThread(), CONTROL_OPTIONS.getResourceName() + " is already initialized.");
		}
	}
	
	@Override
	protected void initDefaultOptions() {
		shutdownKey = new SingleKeyControl("shutdown_key", GLFW.GLFW_KEY_ESCAPE);
		fullscreenKey = new SingleKeyControl("fullscreen_key", GLFW.GLFW_KEY_F1);
		
		moveForwardKey = new SingleKeyControl("move_forward_key", GLFW.GLFW_KEY_W);
		moveBackwardKey = new SingleKeyControl("move_backward_key", GLFW.GLFW_KEY_S);
		moveLeftKey = new SingleKeyControl("move_left_key", GLFW.GLFW_KEY_A);
		moveRightKey = new SingleKeyControl("move_right_key", GLFW.GLFW_KEY_D);
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
						shutdownKey.setCustomKey(getSingleIntegerFromOption(s, shutdownKey.getDefaultKey(), DEFAULT_DELIMITER));
					}
					else if (controlAttrib.equals(fullscreenKey.getFileName())) {
						fullscreenKey.setCustomKey(getSingleIntegerFromOption(s, fullscreenKey.getDefaultKey(), DEFAULT_DELIMITER));
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
					else {
						throw new IllegalStateException(s + " is not an expected option.");
					}
				}
			} catch (Exception e) {
				LoggerUtils.logWarn(Thread.currentThread(), e, CONTROL_OPTIONS.getResourceName() + " is corrupt! Using default values.", false);
			}
		}
		else {
			new File(CONTROL_OPTIONS.getParentDir()).mkdirs();
		}
	}

	@Override
	public void setDefaultOptions() {
		shutdownKey.resetKey();
		fullscreenKey.resetKey();
		moveForwardKey.resetKey();
		moveBackwardKey.resetKey();
		moveLeftKey.resetKey();
		moveRightKey.resetKey();
	}

	@Override
	public void storeOptionsInFile() {
		try (PrintStream writer = new PrintStream(CONTROL_OPTIONS.getFullPath(), "UTF-8")) {
			writer.println(shutdownKey.getFileName() + DEFAULT_DELIMITER + (int)shutdownKey.getCustomKey());
			writer.println(fullscreenKey.getFileName() + DEFAULT_DELIMITER + (int)fullscreenKey.getCustomKey());
			writer.println(moveForwardKey.getFileName() + DEFAULT_DELIMITER + (int)moveForwardKey.getCustomKey());
			writer.println(moveBackwardKey.getFileName() + DEFAULT_DELIMITER + (int)moveBackwardKey.getCustomKey());
			writer.println(moveLeftKey.getFileName() + DEFAULT_DELIMITER + (int)moveLeftKey.getCustomKey());
			writer.print(moveRightKey.getFileName() + DEFAULT_DELIMITER + (int)moveRightKey.getCustomKey());
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			LoggerUtils.logError(Thread.currentThread(), ex);
		}
	}
}

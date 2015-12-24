package com.bountive.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.lwjgl.glfw.GLFW;

import com.bountive.setting.util.SingleKeyControl;
import com.bountive.util.logger.ErrorFileLogger;
import com.bountive.util.resource.ResourceLocation;

public class ControlSettings extends SettingsBase {

	private static final ResourceLocation CONTROL_SETTINGS = new ResourceLocation(SETTINGS_DIR.getFullPath(), "control_settings" + EXTENSION);
	
	public static ControlSettings controlSettings;

	public static SingleKeyControl shutdownKey;
	public static SingleKeyControl fullscreenKey;
	
	public static SingleKeyControl moveForwardKey;
	public static SingleKeyControl moveBackwardKey;
	public static SingleKeyControl moveLeftKey;
	public static SingleKeyControl moveRightKey;
	
	private ControlSettings() {
		controlSettings = this;
	}
	
	public static void init() throws IllegalStateException {
		if (controlSettings == null) {
			controlSettings = new ControlSettings();
			controlSettings.initDefaultSettings();
		}
		else {
			throw new IllegalStateException("ControlSettings is already initialized.");
		}
	}
	
	private void initDefaultSettings() {
		shutdownKey = new SingleKeyControl("shutdown_key", GLFW.GLFW_KEY_ESCAPE);
		fullscreenKey = new SingleKeyControl("fullscreen_key", GLFW.GLFW_KEY_F1);
		
		moveForwardKey = new SingleKeyControl("move_forward_key", GLFW.GLFW_KEY_W);
		moveBackwardKey = new SingleKeyControl("move_backward_key", GLFW.GLFW_KEY_S);
		moveLeftKey = new SingleKeyControl("move_left_key", GLFW.GLFW_KEY_A);
		moveRightKey = new SingleKeyControl("move_right_key", GLFW.GLFW_KEY_D);
	}
	
	@Override
	public void loadSettingsFromFile() {
		File location = new File(CONTROL_SETTINGS.getFullPath());
		setDefaultSettings();
		
		if (location.exists()) {
			
			try {
				String[] settings = readSettingsFile(location);
				
				for (String s : settings) {
					try {
						String controlAttrib = s.substring(0, s.indexOf('='));
						
						if (controlAttrib.equals(shutdownKey.getFileName())) {
							shutdownKey.setCustomKey(getSingleValueFromOption(s));
						}
						else if (controlAttrib.equals(fullscreenKey.getFileName())) {
							fullscreenKey.setCustomKey(getSingleValueFromOption(s));
						}
						else if (controlAttrib.equals(moveForwardKey.getFileName())) {
							moveForwardKey.setCustomKey(getSingleValueFromOption(s));
						}
						else if (controlAttrib.equals(moveBackwardKey.getFileName())) {
							moveBackwardKey.setCustomKey(getSingleValueFromOption(s));
						}
						else if (controlAttrib.equals(moveLeftKey.getFileName())) {
							moveLeftKey.setCustomKey(getSingleValueFromOption(s));
						}
						else if (controlAttrib.equals(moveRightKey.getFileName())) {
							moveRightKey.setCustomKey(getSingleValueFromOption(s));
						}
					} catch (Exception e) {
						ErrorFileLogger.logWarn(WindowSettings.class, "control_settings.sbs is corrupt! Did you edit this file? Unable to get correct control values. Using default values instead.");
						setDefaultSettings();
						break;
					}
				}
			} catch (IOException e) {
				ErrorFileLogger.logError(Thread.currentThread(), e);
			}
		}
		else {
			new File(SETTINGS_DIR.getFullPath()).mkdirs();
		}
	}

	private int getSingleValueFromOption(String fileOption) {
		return Integer.parseInt(fileOption.substring(fileOption.indexOf('=') + 1));
	}
	
	@Override
	public void setDefaultSettings() {
		shutdownKey.resetKey();
		fullscreenKey.resetKey();
		moveForwardKey.resetKey();
		moveBackwardKey.resetKey();
		moveLeftKey.resetKey();
		moveRightKey.resetKey();
	}

	@Override
	public void storeSettingsInFile() {
		try (PrintStream writer = new PrintStream(CONTROL_SETTINGS.getFullPath(), "UTF-8")) {
			writer.println(shutdownKey.getFileName() + "=" + (int)shutdownKey.getCustomKey());
			writer.println(fullscreenKey.getFileName() + "=" + (int)fullscreenKey.getCustomKey());
			writer.println(moveForwardKey.getFileName() + "=" + (int)moveForwardKey.getCustomKey());
			writer.println(moveBackwardKey.getFileName() + "=" + (int)moveBackwardKey.getCustomKey());
			writer.println(moveLeftKey.getFileName() + "=" + (int)moveLeftKey.getCustomKey());
			writer.print(moveRightKey.getFileName() + "=" + (int)moveRightKey.getCustomKey());
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			ErrorFileLogger.logError(Thread.currentThread(), ex);
		}
	}
}

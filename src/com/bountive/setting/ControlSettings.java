package com.bountive.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.lwjgl.glfw.GLFW;

import com.bountive.resource.ResourceLocation;
import com.bountive.util.ErrorFileLogger;

public class ControlSettings extends SettingsBase {

	private static final ResourceLocation CONTROL_SETTINGS = new ResourceLocation(SETTINGS_DIR.getFullPath(), "/control_settings" + EXTENSION);
	
	public static ControlSettings controlSettings;

	private int defaultShutdownKey;
	private int defaultFullscreenKey;
	
	public static int shutdownKey;
	public static int fullscreenKey;
	
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
		defaultShutdownKey = GLFW.GLFW_KEY_ESCAPE;
		defaultFullscreenKey = GLFW.GLFW_KEY_F1;
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
						
						if (controlAttrib.equals("shutdownKey")) {
							int key = Integer.parseInt(s.substring(s.indexOf('=') + 1));
							shutdownKey = key;
						}
						else if (controlAttrib.equals("fullscreenKey")) {
							int key = Integer.parseInt(s.substring(s.indexOf('=') + 1));
							fullscreenKey = key;
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

	@Override
	public void setDefaultSettings() {
		shutdownKey = defaultShutdownKey;
		fullscreenKey = defaultFullscreenKey;
	}

	@Override
	public void storeSettingsInFile() {
		try (PrintStream writer = new PrintStream(CONTROL_SETTINGS.getFullPath(), "UTF-8")) {
			writer.println("shutdownKey=" + (int)shutdownKey);
			writer.println("fullscreenKey=" + (int)fullscreenKey);
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			ErrorFileLogger.logError(Thread.currentThread(), ex);
		}
	}
}

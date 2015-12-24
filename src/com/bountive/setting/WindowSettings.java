package com.bountive.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import math.Vector2f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWvidmode;

import com.bountive.setting.util.BooleanSetting;
import com.bountive.setting.util.Vector2fSetting;
import com.bountive.util.logger.ErrorFileLogger;
import com.bountive.util.resource.ResourceLocation;

public class WindowSettings extends SettingsBase {

	private static final ResourceLocation WINDOW_SETTINGS = new ResourceLocation(SETTINGS_DIR.getFullPath(), "window_settings" + EXTENSION);
	
	public static WindowSettings windowSettings;
	
	private Vector2fSetting windowSize;
	private Vector2fSetting windowPosition;
	
	private BooleanSetting saveWindowState;
	private BooleanSetting vSync;
	private BooleanSetting fullscreen;
	
	private WindowSettings() {
		windowSettings = this;
	}
	
	public static void init() throws IllegalStateException {
		if (windowSettings == null) {
			windowSettings = new WindowSettings();
			windowSettings.initDefaultSettings();
		}
		else {
			throw new IllegalStateException("WindowSettings is already initialized.");
		}
	}
	
	private void initDefaultSettings() {
		ByteBuffer display = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		windowSize = new Vector2fSetting("window_size", new Vector2f(GLFWvidmode.width(display) / 2, GLFWvidmode.height(display) / 2));
		windowPosition = new Vector2fSetting("window_position", new Vector2f((GLFWvidmode.width(display) - windowSize.getDefaultVector2f().x) / 2, (GLFWvidmode.height(display) - windowSize.getDefaultVector2f().y) / 2));
		saveWindowState = new BooleanSetting("save_window_state", true);
		vSync = new BooleanSetting("vsync", true);
		fullscreen = new BooleanSetting("fullscreen", false);
	}
	
	@Override
	public void loadSettingsFromFile() {
		File location = new File(WINDOW_SETTINGS.getFullPath());
		setDefaultSettings();
		
		if (location.exists()) {
			try {
				String[] settings = readSettingsFile(location);
				
				for (String s : settings) {
					try {
						String settingAttrib = s.substring(0, s.indexOf('='));
					
						if (settingAttrib.equals(windowSize.getFileName())) {
							
							try {
								int width = Integer.parseInt(s.substring(s.indexOf('=') + 1, s.indexOf(',')));
								int height = Integer.parseInt(s.substring(s.indexOf(',') + 1));
								
								ByteBuffer primaryMonitor = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
								if (width == GLFWvidmode.width(primaryMonitor)) {
									windowSize.resetVector2f();
								}
								else {
									windowSize.setCustomVector2f(getVector2fValue(new Vector2f(width, height), windowSize.getDefaultVector2f()));
								}
							} catch (Exception e) {
								ErrorFileLogger.logWarn(WindowSettings.class, "window_settings.sbs is corrupt! Did you edit this file? Unable to get correct windowSize. Using default value instead.");
								windowSize.resetVector2f();
							}
							continue;
						}
						else if (settingAttrib.equals(windowPosition.getFileName())) {
							
							try {
								int xPos = Integer.parseInt(s.substring(s.indexOf('=') + 1, s.indexOf(',')));
								int yPos = Integer.parseInt(s.substring(s.indexOf(',') + 1));
								
								if (xPos == 0) {
									windowPosition.resetVector2f();
								}
								else {
									windowPosition.setCustomVector2f(getVector2fValue(new Vector2f(xPos, yPos), windowPosition.getDefaultVector2f()));
								}
							} catch (Exception e) {
								ErrorFileLogger.logWarn(WindowSettings.class, "window_settings.sbs is corrupt! Did you edit this file? Unable to get correct windowPosition. Using default value instead.");
								windowPosition.resetVector2f();
							}
							continue;
						}
						else if (settingAttrib.equals(saveWindowState.getFileName())) {
							saveWindowState.setCustomBoolean(getSingleValueFromOption(s, saveWindowState.getDefaultBoolean()));
						}
						else if (settingAttrib.equals(vSync.getFileName())) {
							vSync.setCustomBoolean(getSingleValueFromOption(s, vSync.getDefaultBoolean()));
							continue;
						}
						else if (settingAttrib.equals(fullscreen.getFileName())) {
							fullscreen.setCustomBoolean(getSingleValueFromOption(s, vSync.getDefaultBoolean()));
							continue;
						}
					} catch (Exception e) {
						ErrorFileLogger.logWarn(WindowSettings.class, "window_settings.sbs is corrupt! Did you edit this file?");
						ErrorFileLogger.logError(Thread.currentThread(), e);
					}
				}
			}
			catch (IOException e) {
				ErrorFileLogger.logError(Thread.currentThread(), e);
			}
		}
		else {
			new File(SETTINGS_DIR.getFullPath()).mkdirs();
		}
	}
	
	private boolean getSingleValueFromOption(String fileOption, boolean defaultValue) {
		return getBooleanValue(parseBoolean(fileOption.substring(fileOption.indexOf('=') + 1)), defaultValue);
	}
	
	@Override
	protected void setDefaultSettings() {
		resetWindow(true);
	}
	
	@Override
	public void storeSettingsInFile() {
		
//		System.out.println(!saveWindowState.getCustomBoolean());
//		System.out.println(!isWindowStateValid());
		
		if (!saveWindowState.getCustomBoolean() || !isWindowStateValid()) {
			resetWindow(false);
		}
		
		try (PrintStream writer = new PrintStream(WINDOW_SETTINGS.getFullPath(), "UTF-8")) {
			writer.println(windowSize.getFileName() + "=" + (int)windowSize.getCustomVector2f().x + "," + (int)windowSize.getCustomVector2f().y);
			writer.println(windowPosition.getFileName() + "=" + (int)windowPosition.getCustomVector2f().x + "," + (int)windowPosition.getCustomVector2f().y);
			writer.println(saveWindowState.getFileName() + "=" + saveWindowState.getCustomBoolean());
			writer.println(vSync.getFileName() + "=" + vSync.getCustomBoolean());
			writer.print(fullscreen.getFileName() + "=" + fullscreen.getCustomBoolean());
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			ErrorFileLogger.logError(Thread.currentThread(), ex);
		}
	}
	
	public static void resetWindow(boolean saveState) {
		if (saveState) windowSettings.saveWindowState.resetBoolean();
		windowSettings.windowSize.resetVector2f();
		windowSettings.windowPosition.resetVector2f();
		windowSettings.vSync.resetBoolean();
		windowSettings.fullscreen.resetBoolean();
	}
	
	public static boolean isWindowStateValid() {
		if (windowSettings.windowPosition.getCustomVector2f().x < 0 || windowSettings.windowPosition.getCustomVector2f().y < 0) return false;
		else if (windowSettings.windowSize.getCustomVector2f().x < 0 || windowSettings.windowSize.getCustomVector2f().y < 0) return false;
		return true;
	}
	
	public Vector2f getWindowSize() {
		return windowSize.getCustomVector2f();
	}
	
	public Vector2f getWindowPosition() {
		return windowPosition.getCustomVector2f();
	}
	
	public boolean isSaveWindowState() {
		return saveWindowState.getCustomBoolean();
	}
	
	public boolean isVSyncEnabled() {
		return vSync.getCustomBoolean();
	}
	
	public void setVSync(boolean b) {
		vSync.setCustomBoolean(b);
	}
	
	public boolean isFullscreenEnabled() {
		return fullscreen.getCustomBoolean();
	}
	
	public void setFullscreen(boolean b) {
		fullscreen.setCustomBoolean(b);
	}
	
	public void updateWindowSize(int x, int y) {
		windowSize.setCustomVector2f(x, y);
	}
	
	public void updateWindowPosition(int x, int y) {
		windowPosition.setCustomVector2f(x, y);
	}
}

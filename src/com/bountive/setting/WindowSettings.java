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

import com.bountive.resource.ResourceLocation;
import com.bountive.util.ErrorFileLogger;
import com.bountive.util.Util;

public class WindowSettings extends SettingsBase {

	private static final ResourceLocation WINDOW_SETTINGS = new ResourceLocation(SETTINGS_DIR.getFullPath(), "/window_settings" + EXTENSION);
	
	public static WindowSettings windowSettings;
	
	private Vector2f defaultWindowSize;
	private Vector2f defaultWindowPosition;
	private boolean defaultSaveWindowState;
	private boolean defaultVSync;
	private boolean defaultFullscreen;
	
	private Vector2f windowSize;
	private Vector2f windowPosition;
	private boolean saveWindowState;
	private boolean vSync;
	private boolean fullscreen;
	
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
		defaultWindowSize = new Vector2f(GLFWvidmode.width(display) / 2, GLFWvidmode.height(display) / 2);
		defaultWindowPosition = new Vector2f((GLFWvidmode.width(display) - defaultWindowSize.x) / 2, (GLFWvidmode.height(display) - defaultWindowSize.y) / 2);
		defaultSaveWindowState = true;
		defaultVSync = true;
		defaultFullscreen = false;
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
					
						if (settingAttrib.equals("windowSize")) {
							
							try {
								int width = Integer.parseInt(s.substring(s.indexOf('=') + 1, s.indexOf(',')));
								int height = Integer.parseInt(s.substring(s.indexOf(',') + 1));
								
								ByteBuffer primaryMonitor = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
								if (width == GLFWvidmode.width(primaryMonitor)) {
									windowSize = new Vector2f(defaultWindowSize);
								}
								else {
									windowSize = getVector2fValue(new Vector2f(width, height), defaultWindowSize);
								}
							} catch (Exception e) {
								ErrorFileLogger.logWarn(WindowSettings.class, "window_settings.sbs is corrupt! Did you edit this file? Unable to get correct windowSize. Using default value instead.");
								windowSize = new Vector2f(defaultWindowSize);
							}
							continue;
						}
						else if (settingAttrib.equals("windowPosition")) {
							
							try {
								int xPos = Integer.parseInt(s.substring(s.indexOf('=') + 1, s.indexOf(',')));
								int yPos = Integer.parseInt(s.substring(s.indexOf(',') + 1));
								
								if (xPos == 0) {
									windowPosition = new Vector2f(defaultWindowPosition);
								}
								else {
									windowPosition = getVector2fValue(new Vector2f(xPos, yPos), defaultWindowPosition);
								}
							} catch (Exception e) {
								ErrorFileLogger.logWarn(WindowSettings.class, "window_settings.sbs is corrupt! Did you edit this file? Unable to get correct windowPosition. Using default value instead.");
								windowPosition = new Vector2f(defaultWindowPosition);
							}
							continue;
						}
						else if (settingAttrib.equals("saveWindowState")) {
							saveWindowState = getBooleanValue(Util.parseBoolean(s.substring(s.indexOf('=') + 1)), defaultSaveWindowState);
						}
						else if (settingAttrib.equals("vSync")) {
							vSync = getBooleanValue(Util.parseBoolean(s.substring(s.indexOf('=') + 1)), defaultVSync);
							continue;
						}
						else if (settingAttrib.equals("fullscreen")) {
							fullscreen = getBooleanValue(Util.parseBoolean(s.substring(s.indexOf('=') + 1)), defaultFullscreen);
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
	
	@Override
	protected void setDefaultSettings() {
		resetWindow();
		saveWindowState = defaultSaveWindowState;
		vSync = defaultVSync;
		fullscreen = defaultFullscreen;
	}
	
	@Override
	public void storeSettingsInFile() {
		if (!saveWindowState || !isWindowStateValid()) {
			resetWindow();
		}
		
		try (PrintStream writer = new PrintStream(WINDOW_SETTINGS.getFullPath(), "UTF-8")) {
			writer.println("windowSize=" + (int)windowSize.x + "," + (int)windowSize.y);
			writer.println("windowPosition=" + (int)windowPosition.x + "," + (int)windowPosition.y);
			writer.println("saveWindowState=" + saveWindowState);
			writer.println("vSync=" + vSync);
			writer.println("fullscreen=" + fullscreen);
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			ErrorFileLogger.logError(Thread.currentThread(), ex);
		}
	}
	
	private void resetWindow() {
		windowSize = new Vector2f(defaultWindowSize);
		windowPosition = new Vector2f(defaultWindowPosition);
	}
	
	private boolean isWindowStateValid() {
		if (windowPosition.x < 0 || windowPosition.y < 0) return false;
		else if (windowSize.x < 0 || windowSize.y < 0) return false;
		return true;
	}
	
	public Vector2f getWindowSize() {
		return windowSize;
	}
	
	public Vector2f getWindowPosition() {
		return windowPosition;
	}
	
	public boolean isVSyncEnabled() {
		return vSync;
	}
	
	public boolean isFullscreenEnabled() {
		return fullscreen;
	}
	
	public void setFullscreen(boolean b) {
		fullscreen = b;
	}
	
	public void updateWindowSize(int x, int y) {
		windowSize.set(x, y);
	}
	
	public void updateWindowPosition(int x, int y) {
		windowPosition.set(x, y);
	}
}

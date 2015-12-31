package com.bountive.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import math.Vector2f;

import com.bountive.display.Window;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.setting.util.BooleanSetting;
import com.bountive.setting.util.ClampedIntegerSetting;
import com.bountive.setting.util.Vector2fSetting;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.ResourceLocation;

public final class GameOptions extends AbstractBaseOptions {

	private static final ResourceLocation GAME_OPTIONS = new ResourceLocation(OPTIONS_DIR.getFullPath(), "game_settings" + EXTENSION);
	
	public static GameOptions gameOptions;
	
	private Vector2fSetting windowSize;
	private Vector2fSetting windowPosition;
	private BooleanSetting saveWindowState;
	
	private BooleanSetting vSync;
	private BooleanSetting fullscreen;
	
	private BooleanSetting isPerspective;
	private ClampedIntegerSetting fieldOfView;
	
	//private IntegerSetting maxFrameRate;
	
	private GameOptions() {
		gameOptions = this;
	}
	
	public static void init() throws IllegalStateException {
		if (gameOptions == null) {
			new GameOptions();
			gameOptions.initDefaultOptions();
		}
		else {
			LoggerUtil.logWarn(Thread.currentThread(), GAME_OPTIONS.getResourceName() + " is already initialized.");
		}
	}
	
	@Override
	protected void initDefaultOptions() {
		//Window state options
		windowSize = new Vector2fSetting("window_size", new Vector2f(Window.getPrimaryMonitorWidth() / 2, Window.getPrimaryMonitorHeight() / 2));
		windowPosition = new Vector2fSetting("window_position", new Vector2f((Window.getPrimaryMonitorWidth() - windowSize.getDefaultVector2f().x) / 2, (Window.getPrimaryMonitorHeight() - windowSize.getDefaultVector2f().y) / 2));
		saveWindowState = new BooleanSetting("save_window_state", true);
		
		//Graphics options
		vSync = new BooleanSetting("vsync", true);
		fullscreen = new BooleanSetting("fullscreen", false);
		
		isPerspective = new BooleanSetting("perspective", true);
		fieldOfView = new ClampedIntegerSetting("FOV", 69, 30, 110);
	}
	
	@Override
	public void loadOptionsFromFile() {
		File location = new File(GAME_OPTIONS.getFullPath());
		setDefaultOptions();
		
		if (location.exists()) {
			try {
				String[] settings = readOptionsFile(location);
				
				for (String s : settings) {
					String settingAttrib = s.substring(0, s.indexOf(DEFAULT_DELIMITER));
				
					if (settingAttrib.equals(windowSize.getFileName())) {
						
						try {
							int width = getSingleIntegerFromOption(s, (int)windowSize.getDefaultVector2f().x, DEFAULT_DELIMITER, SEPARATOR);
							int height = getSingleIntegerFromOption(s, (int)windowSize.getDefaultVector2f().y, SEPARATOR);
							
							if (width == Window.getPrimaryMonitorWidth()) {
								windowSize.resetVector2f();
							}
							else {
								windowSize.setCustomVector2f(getVector2fValue(new Vector2f(width, height), windowSize.getDefaultVector2f()));
							}
						} catch (NumberFormatException e) {
							LoggerUtil.logWarn(Thread.currentThread(), e, GAME_OPTIONS.getResourceName() + " is corrupt! Did you edit this file? Unable to get correct windowSize. Using default value instead.", true);
							windowSize.resetVector2f();
						}
					}
					else if (settingAttrib.equals(windowPosition.getFileName())) {
						
						try {
							int xPos = getSingleIntegerFromOption(s, (int)windowPosition.getDefaultVector2f().x, DEFAULT_DELIMITER, SEPARATOR);
							int yPos = getSingleIntegerFromOption(s, (int)windowPosition.getDefaultVector2f().y, SEPARATOR);
							
							if (xPos == 0) {
								windowPosition.resetVector2f();
							}
							else {
								windowPosition.setCustomVector2f(getVector2fValue(new Vector2f(xPos, yPos), windowPosition.getDefaultVector2f()));
							}
						} catch (Exception e) {
							LoggerUtil.logWarn(Thread.currentThread(), e, GAME_OPTIONS.getResourceName() + " is corrupt! Did you edit this file? Unable to get correct windowPosition. Using default value instead.", true);
							windowPosition.resetVector2f();
						}
					}
					else if (settingAttrib.equals(saveWindowState.getFileName())) {
						saveWindowState.setCustomBoolean(getSingleBooleanFromOption(s, saveWindowState.getDefaultBoolean(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(vSync.getFileName())) {
						vSync.setCustomBoolean(getSingleBooleanFromOption(s, vSync.getDefaultBoolean(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(fullscreen.getFileName())) {
						fullscreen.setCustomBoolean(getSingleBooleanFromOption(s, vSync.getDefaultBoolean(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(isPerspective.getFileName())) {
						isPerspective.setCustomBoolean(getSingleBooleanFromOption(s, isPerspective.getDefaultBoolean(), DEFAULT_DELIMITER));
					}
					else if (settingAttrib.equals(fieldOfView.getFileName())) {
						fieldOfView.setCustomInteger(getSingleIntegerFromOption(s, fieldOfView.getDefaultInteger(), DEFAULT_DELIMITER));
					}
					else {
						throw new IllegalStateException(s + " is not an expected option.");
					}
				}
			}
			catch (Exception e) {
				LoggerUtil.logWarn(Thread.currentThread(), e, GAME_OPTIONS.getResourceName() + " is corrupt! Using default values.", true);
			}
		}
		else {
			new File(GAME_OPTIONS.getParentDir()).mkdirs();
		}
	}
	
	private static void resetWindowState() {
		gameOptions.windowSize.resetVector2f();
		gameOptions.windowPosition.resetVector2f();
		gameOptions.fullscreen.resetBoolean();
	}
	
	@Override
	protected void setDefaultOptions() {
		windowSize.resetVector2f();
		windowPosition.resetVector2f();
		saveWindowState.resetBoolean();
		vSync.resetBoolean();
		fullscreen.resetBoolean();
		isPerspective.resetBoolean();
		fieldOfView.resetInteger();
	}
	
	@Override
	public void storeOptionsInFile() {
		if (!saveWindowState.getCustomBoolean() || !isWindowStateValid()) {
			resetWindowState();
		}
		
		try (PrintStream writer = new PrintStream(GAME_OPTIONS.getFullPath(), "UTF-8")) {
			writer.println(windowSize.getFileName() + DEFAULT_DELIMITER + (int)windowSize.getCustomVector2f().x + "," + (int)windowSize.getCustomVector2f().y);
			writer.println(windowPosition.getFileName() + DEFAULT_DELIMITER + (int)windowPosition.getCustomVector2f().x + "," + (int)windowPosition.getCustomVector2f().y);
			writer.println(saveWindowState.getFileName() + DEFAULT_DELIMITER + saveWindowState.getCustomBoolean());
			writer.println(vSync.getFileName() + DEFAULT_DELIMITER + vSync.getCustomBoolean());
			writer.println(fullscreen.getFileName() + DEFAULT_DELIMITER + fullscreen.getCustomBoolean());
			writer.println(isPerspective.getFileName() + DEFAULT_DELIMITER + isPerspective.getCustomBoolean());
			writer.print(fieldOfView.getFileName() + DEFAULT_DELIMITER + fieldOfView.getCustomInteger());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			LoggerUtil.logError(Thread.currentThread(), e);
		}
	}
	
	public static boolean isWindowStateValid() {
		if (gameOptions.windowPosition.getCustomVector2f().x < 0 || gameOptions.windowPosition.getCustomVector2f().y < 0) return false;
		else if (gameOptions.windowSize.getCustomVector2f().x < 0 || gameOptions.windowSize.getCustomVector2f().y < 0) return false;
		else if (gameOptions.windowSize.getCustomVector2f().x == Window.getPrimaryMonitorWidth() && gameOptions.windowPosition.getCustomVector2f().x == 0) return false;
		return true;
	}
	
	public Vector2f getSavedWindowSize() {
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
		GameOptions.gameOptions.storeOptionsInFile();
		fullscreen.setCustomBoolean(b);
		Window.buildScreen();
		ControlOptions.setPaused(ControlOptions.isPaused());
		CameraMatrixManager.manager.buildProjectionMatrix();
	}
	
	public boolean isPerspective() {
		return isPerspective.getCustomBoolean();
	}
	
	public void setPerspective(boolean b) {
		isPerspective.setCustomBoolean(b);
	}
	
	public void setFOV(int fov) {
		fieldOfView.setCustomInteger(fov);
		CameraMatrixManager.manager.buildProjectionMatrix();
	}
	
	public int getFOV() {
		return fieldOfView.getCustomInteger();
	}
	
	public void updateSavedWindowSize(int x, int y) {
		windowSize.setCustomVector2f(x, y);
	}
	
	public void updateWindowPosition(int x, int y) {
		windowPosition.setCustomVector2f(x, y);
	}
}

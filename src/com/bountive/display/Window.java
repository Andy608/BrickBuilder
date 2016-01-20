package com.bountive.display;

import java.nio.ByteBuffer;

import math.Vector2f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.bountive.display.callback.CallbackManager;
import com.bountive.setting.ControlOptions;
import com.bountive.setting.GameOptions;
import com.bountive.start.Info;
import com.bountive.util.logger.LoggerUtil;

public class Window {

	private static final String TITLE = Info.NAME + " | " + Info.VERSION + " | " + Info.AUTHOR;
	private static Window window;
	private long windowID;
	private CallbackManager callbackManager;
	private static Vector2f primaryMonitorSize;
	
	private Window() {
		window = this;
	}
	
	public static void init() throws IllegalStateException, RuntimeException {
		if (window == null) {
			window = new Window();
		}
		
		if (window.windowID != MemoryUtil.NULL) {
			throw new IllegalStateException("A GLFW window is already created. Cannot create a new window for the current game thread.");
		}
		
		try {
			initGLFW();
			ByteBuffer primaryMonitor = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			primaryMonitorSize = new Vector2f(GLFWvidmode.width(primaryMonitor), GLFWvidmode.height(primaryMonitor));
			
			GameOptions.init();
			GameOptions.gameOptions.loadOptionsFromFile();
			
			ControlOptions.init();
			ControlOptions.controlOptions.loadOptionsFromFile();
			
		} catch (RuntimeException e) {
			LoggerUtil.logError(Window.class, Thread.currentThread(), e);
		}
	}
	
	private static void initGLFW() throws IllegalStateException {
		if (GLFW.glfwInit() == GL11.GL_FALSE) {
			throw new IllegalStateException("Unable to initialie GLFW.");
		}
	}
	
	public static void createWindow() throws RuntimeException {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		buildScreen();
	}
	
	public static void buildScreen() throws RuntimeException {
		
		long displayHandle;
		
		if (GameOptions.gameOptions.isFullscreenEnabled()) {
			displayHandle = GLFW.glfwCreateWindow(getPrimaryMonitorWidth(), getPrimaryMonitorHeight(), TITLE, GLFW.glfwGetPrimaryMonitor(), window.windowID);
		}
		else {
			Vector2f windowSize = GameOptions.gameOptions.getSavedWindowSize();
			displayHandle = GLFW.glfwCreateWindow((int)windowSize.x, (int)windowSize.y, TITLE, MemoryUtil.NULL, (window.windowID == MemoryUtil.NULL) ? MemoryUtil.NULL : window.windowID);
		}
		
		if (displayHandle == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create GLFW window.");
		}
		
		if (window.windowID != MemoryUtil.NULL) {
			window.callbackManager.release();
			GLFW.glfwDestroyWindow(window.windowID);
		}
		
		window.windowID = displayHandle;
		
		if (!GameOptions.gameOptions.isFullscreenEnabled()) {
			GLFW.glfwSetWindowPos(window.windowID, (int)GameOptions.gameOptions.getWindowPosition().x, (int)GameOptions.gameOptions.getWindowPosition().y);
		}
		
		GLFW.glfwMakeContextCurrent(window.windowID);
		GLFW.glfwSwapInterval(GameOptions.gameOptions.isVSyncEnabled() ? 1 : 0);
		GLFW.glfwShowWindow(window.windowID);
		window.callbackManager = new CallbackManager(window.windowID);
	}
	
	public static void saveOptions() {
		System.out.println("Saving!");
		GameOptions.gameOptions.storeOptionsInFile();
		ControlOptions.controlOptions.storeOptionsInFile();
	}
	
	public static long getID() {
		return window.windowID;
	}
	
	public static int getPrimaryMonitorWidth() {
		return (int)primaryMonitorSize.x;
	}
	
	public static int getPrimaryMonitorHeight() {
		return (int)primaryMonitorSize.y;
	}
	
	public static Vector2f getPrimaryMonitorSize() {
		return primaryMonitorSize;
	}
	
	public static Vector2f getWindowSize() {
		return GameOptions.gameOptions.isFullscreenEnabled() ? Window.getPrimaryMonitorSize() : GameOptions.gameOptions.getSavedWindowSize();
	}
	
	public static void release() {
		window.callbackManager.release();
		GLFW.glfwDestroyWindow(window.windowID);
		GLFW.glfwTerminate();
	}
}

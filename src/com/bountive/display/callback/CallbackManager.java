package com.bountive.display.callback;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class CallbackManager {

	private GLFWErrorCallback errorCallback;
	
	private GLFWWindowFocusCallback windowFocusCallback;
	
	private GLFWWindowSizeCallback windowSizeCallback;
	private GLFWFramebufferSizeCallback frameBufferSizeCallback;
	private GLFWWindowPosCallback windowPositionCallback;
	
	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback mousePosCallback;
	
	public CallbackManager(long windowID) {
		GLFW.glfwSetErrorCallback(errorCallback = Callbacks.errorCallbackPrint(System.err));
		
		GLFW.glfwSetWindowFocusCallback(windowID, windowFocusCallback = new WindowFocusCallback());
		
		GLFW.glfwSetWindowSizeCallback(windowID, windowSizeCallback = new WindowSizeCallback());
		GLFW.glfwSetFramebufferSizeCallback(windowID, frameBufferSizeCallback = new FramebufferSizeCallback());
		GLFW.glfwSetWindowPosCallback(windowID, windowPositionCallback = new WindowPositionCallback());
		
		GLFW.glfwSetKeyCallback(windowID, keyCallback = new KeyboardCallback());
		GLFW.glfwSetCursorPosCallback(windowID, mousePosCallback = new MousePositionCallback());
	}
	
	public void release() {
		errorCallback.release();
		windowFocusCallback.release();
		windowSizeCallback.release();
		frameBufferSizeCallback.release();
		windowPositionCallback.release();
		keyCallback.release();
		mousePosCallback.release();
	}
}

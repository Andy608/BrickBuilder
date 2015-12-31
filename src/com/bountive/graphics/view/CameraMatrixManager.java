package com.bountive.graphics.view;

import math.Matrix4f;
import math.Vector2f;

import com.bountive.display.Window;
import com.bountive.setting.GameOptions;
import com.bountive.util.logger.LoggerUtil;

/**
 * This class keeps track of
 */
public class CameraMatrixManager {

	public static CameraMatrixManager manager;
	
	private Matrix4f projectionMatrix;
	
	private static final float NEAR_PLANE = 0.1f;
	//TODO: May be moved as a setting in the GameOptions file. Not sure if this should be constant or changeable yet. I may change it depending on weather.
	private static final float FAR_PLANE  = 200f;
	
	private CameraMatrixManager() {
		manager = this;
	}
	
	public static void init() {
		if (manager == null) {
			new CameraMatrixManager();
			manager.initMatrices();
		}
		else {
			LoggerUtil.logWarn(Thread.currentThread(), manager.getClass().getSimpleName() + " is already initialized.");
		}
	}
	
	private void initMatrices() {
		projectionMatrix = new Matrix4f();
		buildProjectionMatrix();
	}
	
	public void buildProjectionMatrix() {
		if (GameOptions.gameOptions.isPerspective()) buildPerspectiveMatrix();
		else buildOrthographicMatrix();
	}
	
	private void buildPerspectiveMatrix() {
		Vector2f windowSize = Window.getWindowSize();
		float aspectRatio = (float)windowSize.x / (float)windowSize.y;
		float FOV = GameOptions.gameOptions.getFOV();
		
		projectionMatrix.setIdentity();
		projectionMatrix.m00 = 1f / (float)(Math.tan(Math.toRadians(FOV) / 2f));
		projectionMatrix.m11 = aspectRatio / (float)(Math.tan(Math.toRadians(FOV) / 2f));
		projectionMatrix.m22 = (NEAR_PLANE + FAR_PLANE) / (NEAR_PLANE - FAR_PLANE);
		projectionMatrix.m23 = -1f;
		projectionMatrix.m32 = (2 * NEAR_PLANE * FAR_PLANE) / (NEAR_PLANE - FAR_PLANE);
		projectionMatrix.m33 = 0f;
	}
	
	//TODO:FIX THIS MESS/DELETE IF NOT WORTH IT.
	private void buildOrthographicMatrix() {
		Vector2f windowSize = Window.getWindowSize();
		float aspectRatio = (float)windowSize.x / (float)windowSize.y;
		projectionMatrix.setIdentity();
		
		//dimension = size of box - try 50?
		//left = (-dimension * aspectRatio)
		//right = (dimension * aspectRatio)
		//bottom = -dimension
		//top = dimension
		//near = -dimension
		//far = dimension
		
		float dimension = 3;
		float left = (-dimension * aspectRatio);
		float right = (dimension * aspectRatio);
		float bottom = -dimension;
		float top = dimension;
		
		projectionMatrix.m00 = 2f / (right - left);
		projectionMatrix.m11 = 2f / (top - bottom);
		projectionMatrix.m22 = -2f / (FAR_PLANE - NEAR_PLANE);
		projectionMatrix.m30 = -((right + left) / (right - left));
		projectionMatrix.m31 = -((top + bottom) / (top - bottom));
		projectionMatrix.m32 = -((FAR_PLANE + NEAR_PLANE) / (FAR_PLANE - NEAR_PLANE));
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}

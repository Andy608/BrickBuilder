package com.bountive.graphics.view;

import math.Matrix4f;
import math.Vector3f;

public abstract class AbstractCamera {

	protected static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
	protected static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	protected static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);
	
	protected Matrix4f viewMatrix;
	protected Vector3f cameraPosition;
	protected Vector3f cameraRotation;
	
	public AbstractCamera() {
		this(new Vector3f(), new Vector3f());
	}
	
	public AbstractCamera(Vector3f pos, Vector3f rot) {
		cameraPosition = pos;
		cameraRotation = rot;
		viewMatrix = new Matrix4f();
	}
	
	protected abstract void createViewMatrix();
	
	public abstract void update(double deltaTime);
	
	public Vector3f getCameraPosition() {
		return cameraPosition;
	}
	
	public Vector3f getCameraRotation() {
		return cameraRotation;
	}
	
	public float getPitch() {
		return cameraRotation.x;
	}
	
	public float getYaw() {
		return cameraRotation.y;
	}
	
	public float getRoll() {
		return cameraRotation.z;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
}

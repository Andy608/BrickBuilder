package com.bountive.graphics.view;

import math.Matrix4f;
import math.Vector3f;

public abstract class AbstractCamera {

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
	
	public void update(double deltaTime) {
		createViewMatrix();
	}
	
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

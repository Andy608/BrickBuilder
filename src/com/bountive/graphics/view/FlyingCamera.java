package com.bountive.graphics.view;

import math.Matrix4f;
import math.Vector3f;

public class FlyingCamera extends AbstractCamera {

	private float moveSpeed; //1x1 Bricks per second.
	
	public FlyingCamera() {
		this(10f);
	}
	
	public FlyingCamera(float defaultSpeed) {
		this(defaultSpeed, new Vector3f());
	}
	
	public FlyingCamera(float defaultSpeed, Vector3f pos) {
		this(defaultSpeed, pos, new Vector3f());
	}
	
	public FlyingCamera(float defaultSpeed, Vector3f pos, Vector3f rot) {
		super(pos, rot);
		moveSpeed = defaultSpeed;
	}
	
	public void update(double deltaTime) {
		
		//
		
	}
	
	@Override
	protected void createViewMatrix() {
		viewMatrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(getPitch()), X_AXIS, viewMatrix, viewMatrix);
		Matrix4f.rotate((float)Math.toRadians(getYaw()), Y_AXIS, viewMatrix, viewMatrix);
		Matrix4f.rotate((float)Math.toRadians(getRoll()), Z_AXIS, viewMatrix, viewMatrix);
		Vector3f oppositePosition = new Vector3f(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
		Matrix4f.translate(oppositePosition, viewMatrix, viewMatrix);
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
}

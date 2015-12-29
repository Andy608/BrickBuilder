package com.bountive.graphics.view;

import math.Vector3f;

import com.bountive.display.callback.MousePositionCallback;
import com.bountive.setting.ControlOptions;
import com.bountive.util.math.MathHelper;
import com.bountive.util.math.MatrixMathHelper;

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
	
	@Override
	public void update(double deltaTime) {
		
		//TODO: MOST OF THIS WILL BE USED TO MOVE THE PLAYER'S POSITION. FOR NOW, IM USING THIS CAMERA AS THE PLAYER.
		
		Vector3f direction = new Vector3f();
		
		if (ControlOptions.moveForwardKey.isPressed()) {
			direction.x += (float)(MathHelper.sin(getYaw()) * MathHelper.cos(getPitch()));//glitch when pressing w and space at 90 degree angle --> camera moves forward from rounding error.
			direction.y -= (float)(MathHelper.sin(getPitch()));//Pitch becomes negative as the player looks up --> making sin(pitch) negative.
			direction.z -= (float)(MathHelper.cos(getYaw()) * MathHelper.cos(getPitch()));//glitch when pressing w and space at 90 degree angle --> camera moves forward from rounding error.
		}
		
		if (ControlOptions.moveBackwardKey.isPressed()) {
			direction.x -= (float)(MathHelper.sin(getYaw()) * MathHelper.cos(getPitch()));//glitch when pressing s and space at 90 degree angle --> camera moves backward from rounding error.
			direction.y += (float)(MathHelper.sin(getPitch()));
			direction.z += (float)(MathHelper.cos(getYaw()) * MathHelper.cos(getPitch()));//glitch when pressing s and space at 90 degree angle --> camera moves backward from rounding error.
		}
		
		if (ControlOptions.moveLeftKey.isPressed()) {
			direction.x += (float)(MathHelper.sin(getYaw() - 90));
			direction.z -= (float)(MathHelper.cos(getYaw() - 90));
		}
		
		if (ControlOptions.moveRightKey.isPressed()) {
			direction.x -= (float)(MathHelper.sin(getYaw() - 90));
			direction.z += (float)(MathHelper.cos(getYaw() - 90));
		}
		
		if (ControlOptions.jumpKey.isPressed()) {
			direction.y += 1;
		}
		
		if (ControlOptions.duckKey.isPressed()) {
			direction.y -= 1;
		}
		
		if (direction.lengthSquared() > 0) {
			direction.normalise();
			direction.scale((float)(deltaTime * moveSpeed));
//			System.out.println(direction);
			Vector3f.add(cameraPosition, direction, cameraPosition);
		}
		
		cameraRotation.y += /*sensitivity*/ 10 * (float)Math.toRadians(MousePositionCallback.getMouseOffset().x);
		cameraRotation.x = MathHelper.clampFloat(cameraRotation.x + /*sensitivity*/ 10 * (float)Math.toRadians(MousePositionCallback.getMouseOffset().y), -90, 90);
		
		createViewMatrix();
	}
	
	@Override
	protected void createViewMatrix() {
		viewMatrix.setIdentity();
		viewMatrix.rotate((float)Math.toRadians(getPitch()), MatrixMathHelper.X_AXIS);
		viewMatrix.rotate((float)Math.toRadians(getYaw()), MatrixMathHelper.Y_AXIS);
		viewMatrix.rotate((float)Math.toRadians(getRoll()), MatrixMathHelper.Z_AXIS);
		viewMatrix.translate(new Vector3f(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z));
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
}

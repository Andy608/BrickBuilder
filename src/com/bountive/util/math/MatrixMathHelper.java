package com.bountive.util.math;

import math.Matrix4f;
import math.Vector3f;

public class MatrixMathHelper {

	public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
	public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);
	private static final Vector3f DEFAULT_SCALE = new Vector3f(1, 1, 1);
	
	public static Matrix4f buildTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
		Matrix4f m = new Matrix4f();
		
		m.translate(translation);
		
		if (rotation != null) {
			m.rotate((float)(Math.toRadians(rotation.x)), X_AXIS);
			m.rotate((float)(Math.toRadians(rotation.y)), Y_AXIS);
			m.rotate((float)(Math.toRadians(rotation.z)), Z_AXIS);
		}
		
		if (scale == null) m.scale(DEFAULT_SCALE);
		else m.scale(scale);
		
		return m;
	}
	
	public static Matrix4f buildTransformationMatrix(Vector3f translation, Vector3f rotation) {
		return buildTransformationMatrix(translation, rotation, null);
	}
	
	public static Matrix4f buildTransformationMatrix(Vector3f translation) {
		return buildTransformationMatrix(translation, null);
	}
	
	public static void resetVector(Vector3f v) {
		v.set(0, 0, 0);
	}
}

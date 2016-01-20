package com.bountive.graphics.model.util;

import org.lwjgl.opengl.GL30;

public class VBOWrapper {

	private int vboID;
	private int vboLength;
	private float[] vboData;
	
	public VBOWrapper(int id, float[] data, int length) {
		vboID = id;
		vboLength = length;
		vboData = data;
	}
	
	public int getID() {
		return vboID;
	}
	
	public void setID(int id) {
		GL30.glDeleteVertexArrays(vboID);
		vboID = id;
	}
	
	/**
	 * @return the length of an individual data value in vboData.
	 */
	public int getLength() {
		return vboLength;
	}
	
	public int getDataLength() {
		return vboData.length / vboLength;
	}
	
	public float[] getData() {
		return vboData;
	}
}

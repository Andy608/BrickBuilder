package com.bountive.graphics.model;

public class ModelMesh {
	
	protected int vaoID;
	protected int vertexCount;
	
	public ModelMesh(int vertexArrayID, int vertCount) {
		vaoID = vertexArrayID;
		vertexCount = vertCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
}

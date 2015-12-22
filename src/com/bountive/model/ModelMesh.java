package com.bountive.model;

public class ModelMesh {
	
	protected int vaoID;
	protected int vertexCount;
	
	public ModelMesh(int vertexArrayID, int vertCount) {
		vaoID = vertexArrayID;
		vertexCount = vertCount;
	}
}

package com.bountive.graphics.model;

public class ModelMesh {
	
	protected int vaoID;
	protected int vertexCount;
	
	protected int[] vboIDs;
	
	public ModelMesh(int vertexArrayID, int[] vertexBufferObjectIDs, int vertCount) {
		vaoID = vertexArrayID;
		vertexCount = vertCount;
		vboIDs = vertexBufferObjectIDs;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public int[] getVboIDs() {
		return vboIDs;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
}

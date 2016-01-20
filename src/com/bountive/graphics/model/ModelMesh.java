package com.bountive.graphics.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.bountive.graphics.model.util.VBOWrapper;

public class ModelMesh {
	
	protected int vaoID;
	protected int vertexCount;
	
	protected VBOWrapper[] vboHandles;
	
	public ModelMesh(int vertexArrayID, int vertCount, VBOWrapper[] vboIDs) {
		vaoID = vertexArrayID;
		vertexCount = vertCount;
		vboHandles = vboIDs;
	}
	
	//TODO:TEST - USE glBufferSubData instead of bufferData
//	protected void updateVBOs() {
//		for (int i = 0; i < vboIDs.length; i++) {
//			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboIDs[i]);
//			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.toReadableFloatBuffer(data[i]), GL15.GL_DYNAMIC_DRAW);
//			GL20.glVertexAttribPointer(vboIDs[i], vboLengths[i], GL11.GL_FLOAT, false, 0, 0);
//			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//		}
//	}
	
	//TODO:TEST
	public void rebuildVAO() {
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		for (int i = 0; i < vboHandles.length; i++) {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandles[i].getID());
			GL20.glVertexAttribPointer(i, vboHandles[i].getLength(), GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			GL20.glEnableVertexAttribArray(i);
		}
		GL30.glBindVertexArray(0);
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public VBOWrapper[] getVBOs() {
		return vboHandles;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
}

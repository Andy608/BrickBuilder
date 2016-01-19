package com.bountive.world.zone;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.util.BufferUtil;

public class ModelZone extends ModelMesh {

	public ModelZone(int vertexArrayID, int vertCount, int[] vbos) {
		super(vertexArrayID, vbos, vertCount);
	}
	
	public void update(float[] pos, float[] cols, float[] norms, float[] tex) {
		GL30.glBindVertexArray(vaoID);
		updateVBO(vaoID, vboIDs[0], pos, 3);
		updateVBO(vaoID, vboIDs[1], cols, 4);
		updateVBO(vaoID, vboIDs[2], norms, 3);
		updateVBO(vaoID, vboIDs[3], tex, 2);
		GL30.glBindVertexArray(0);
	}
	
	private void updateVBO(int arrayIndex, int vbo, float[] bufferData, int dataLengthPerVertex) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.toReadableFloatBuffer(bufferData), GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(arrayIndex, dataLengthPerVertex, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
}
